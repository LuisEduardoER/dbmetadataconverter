/*
 * Copyright 2008 DataBaseMetaDataConverter.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package code.google.com.dbmetadataconverter.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import code.google.com.dbmetadataconverter.model.Field;
import code.google.com.dbmetadataconverter.model.Relation;
import code.google.com.dbmetadataconverter.model.Table;

public class DBMetaDataConverterService {

	private static final int COLUMN_NAME = 4;
	private static final int TYPE_NAME = 6;
	public static final String MY_SQL = "MySQL";
	public static final String ORACLE = "Oracle";
	public static final String MS_SQL_SERVER= "MS SQLServer";
	
	private Connection conn;
	private DatabaseMetaData meta;	
	
	
	public DBMetaDataConverterService(String username, String password, String host, 
			String rdbms, String dbName) throws SQLException {
		
		String url = DBMetaDataConverterService.getUrl(rdbms, host, dbName);
		String driverClassName = DBMetaDataConverterService.getDriverName(rdbms);
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		url = DBMetaDataConverterService.getUrl(rdbms, host, dbName);
		conn = DriverManager.getConnection(url, username, password);		
		meta = conn.getMetaData();		
	}
	
	public DBMetaDataConverterService(DatabaseMetaData meta) {
		this.meta = meta;
	}
	
	public DatabaseMetaData getMeta() {
		return meta;
	}

	public void setMeta(DatabaseMetaData meta) {
		this.meta = meta;
	}
	
	public List<Table> getAllTables() throws SQLException {
		ResultSet rs = meta.getTables(null, null, null, null);
	    List<Table> tables = new ArrayList<Table>();
	    while (rs.next()) {
	    	if (rs.getString(4).equals("TABLE")) {
	    		tables.add(new Table(rs.getString(3)));
	    	}	    	
	    }
		return getTables(tables);
	}
	
	public List<Table> getTables(List<Table> tables) throws SQLException {		
		for (Table table : tables) {
			table.setNameForClass(convert(table.getName(), true));
			table.setNameForAttribute(convert(table.getName(), false));
			ResultSet rsCols = meta.getColumns(null, null, table.getName(), null);			
			
			//fields of the table
			ArrayList<Field> fields = new ArrayList<Field>();
			while (rsCols.next()) {
				Field field = new Field();
				field.setTable(table);
				field.setName(rsCols.getString(COLUMN_NAME));
				field.setNameForClass(convert(field.getName(), true));
				field.setNameForAttribute(convert(field.getName(), false));
				field.setType(this.getType(rsCols.getString(TYPE_NAME), 
						rsCols.getInt(7), rsCols.getInt(9)));
				field.setSqlType(rsCols.getInt(5));
				field.setNotNull(rsCols.getString(18).trim().equals("NO"));
				field.setComments(rsCols.getString(12));
				field.setOrdinalPosition(rsCols.getInt(17));
				fields.add(field);				
			}
			Collections.sort(fields);
			table.setFields(fields);
			
			//pks of the table
			rsCols.close();
			rsCols = meta.getPrimaryKeys(null, null, table.getName());
			ArrayList<Field> keyFields = new ArrayList<Field>();
			while (rsCols.next()) {
				Field field = new Field();
				field.setTable(table);
				field.setName(rsCols.getString(COLUMN_NAME));
				
				field = fields.get(Collections.binarySearch(fields, field));
				field.setKey(true);
				 
				keyFields.add(field);				
			}
			table.setKeys(keyFields);
			rsCols.close();
		}
		
		//relations of this table	
		Collections.sort(tables);
		List<Relation> relations = new ArrayList<Relation>();		
		for (Table pkTable : tables) {
			ResultSet rsEK = meta.getExportedKeys(null, null, pkTable.getName());
			Relation relation = null;
			String lastFkTableName = "";
			Integer ordinalSequence = 1;
			while (rsEK.next()) {
				//String pkTableName = rsEK.getString(3);
				String fkTableName = rsEK.getString(7);
				String pkColumnName = rsEK.getString(4);
				String fkColumnName = rsEK.getString(8);
				short keySeq = rsEK.getShort(9);
								
				Table fkTable = tables.get(Collections.binarySearch(
						tables, new Table(fkTableName)));
				Field pkField = new Field(pkColumnName, pkTable);
				Field fkField = new Field(fkColumnName, fkTable);
											
				if (keySeq == 1) {
					relation = new Relation();
					relation.setPkTable(pkTable);
					relation.setFkTable(fkTable);	
					relations.add(relation);
					pkTable.getPkRelations().add(relation);
					fkTable.getFkRelations().add(relation);
					if (lastFkTableName.equals(fkTableName)) {
						relation.setOrdinalSequence(++ordinalSequence);
					} else {
						relation.setOrdinalSequence(ordinalSequence = 1);
						lastFkTableName = fkTableName;
					}
				}
				pkField = pkTable.getFields().get(
						Collections.binarySearch(pkTable.getFields(), pkField));
				fkField = fkTable.getFields().get(
						Collections.binarySearch(fkTable.getFields(), fkField));
				fkField.setFk(true);
				fkField.setFkTable(fkTable);
				
				relation.getFieldsInPkTable().add(pkField);
				relation.getFieldsInFkTable().add(fkField);				
			}
			rsEK.close();
		}
		Comparator<Field> comparator = new Comparator<Field>() {
			@Override
			public int compare(Field o1, Field o2) {
				return o1.getOrdinalPosition().compareTo(o2.getOrdinalPosition());					
			}			
		};
		for (Table table : tables) {
			Collections.sort(table.getFields(),comparator);
			Collections.sort(table.getKeys(),comparator);
		}
		return tables;
	}
	
	public static String getDriverName(String rdbms) {
		String driverName = "";
		if (rdbms.equals(MY_SQL)) {
			driverName = "com.mysql.jdbc.Driver";
		} else if (rdbms.equals(ORACLE)) {
			driverName = "oracle.jdbc.driver.OracleDriver";
		} else if (rdbms.equals(MS_SQL_SERVER)) {
			driverName = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
		}
		return driverName;
	}
	
	public static String getUrl(String rdbms, String host, String dbName) {
		String url = "";
		if (rdbms.equalsIgnoreCase(MY_SQL)) {			
			url = "jdbc:mysql://" + host + ":3306/" + dbName;			
		} else if (rdbms.equals(ORACLE)) {
			url = "jdbc:oracle:thin:@" + host + ":1521:" + dbName;
		} else if (rdbms.equals(MS_SQL_SERVER)) {
			url = "jdbc:microsoft:sqlserver://"+host+":1433;DatabaseName="+dbName;
		}
		return url;
	}
	
	public static String convert(String str, boolean isFirstUpper) {
		String[] strV = str.split("_");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strV.length; i++) {
			char[] aux = convertIntoLowercase(strV[i].toCharArray());
			aux[0] = Character.toUpperCase(aux[0]);
			sb.append(String.valueOf(aux));			
		}	
		String retorno = sb.toString();
		char[] auxN = retorno.toCharArray();
		if (!isFirstUpper) {
			auxN[0] = Character.toLowerCase(auxN[0]);
			retorno = String.valueOf(auxN);
		}
		return retorno.toString();
	}
	
	private static char[] convertIntoLowercase(char[] nome) {
		char[] retorno = new char[nome.length];
		for (int i = 0; i < nome.length; i++) {
			retorno[i] = Character.toLowerCase(nome[i]);
		}
		return retorno;
	}
	
	private Class<?> getType(String sqlType, int columSize, int decimalDigits) {		
		if (sqlType.equalsIgnoreCase("CHAR") || sqlType.equalsIgnoreCase("VARCHAR") || 
				sqlType.equalsIgnoreCase("LONGVARCHAR") || sqlType.equalsIgnoreCase("VARCHAR2")
				|| sqlType.toUpperCase().contains("VARCHAR")) {
			return String.class;
		} else if (sqlType.toUpperCase().contains("NUMBER") 
				|| sqlType.toUpperCase().contains("INTEGER") || sqlType.toUpperCase().contains("TINYINT")
				|| sqlType.toUpperCase().contains("BIGINT")				 				 
				|| sqlType.toUpperCase().contains("SMALLINT") || sqlType.toLowerCase().startsWith("int")) {
			if (decimalDigits == 0) {
				return columSize <= 8 ? Integer.class : Long.class;
			} else if (decimalDigits > 0) {
				return Double.class; 
			}
		} else if (sqlType.equalsIgnoreCase("REAL")) { 
			return Float.class;
		} else if (sqlType.equalsIgnoreCase("DOUBLE") || sqlType.equalsIgnoreCase("FLOAT")) {
			return Double.class; 
		} else if (sqlType.equalsIgnoreCase("DATE") || sqlType.startsWith("date") 
				|| sqlType.equalsIgnoreCase("DATETIME") || sqlType.contains("date") || sqlType.contains("time")) {
			return Date.class;			
		} else if (sqlType.equalsIgnoreCase("BIT")) {
			return Short.class;
		} else if (sqlType.equalsIgnoreCase("NUMERIC") || sqlType.equalsIgnoreCase("DECIMAL")) {
			return Double.class;
		}			
		return Object.class;
	}
	
	public static String getSqlTypes(int i) {
		switch (i) {
		case Types.INTEGER: return "Types.INTEGER";
		case Types.VARCHAR: return "Types.VARCHAR";
		case Types.DATE: return "Types.DATE";
		case Types.DOUBLE: return "Types.DOUBLE";		
		case Types.BIGINT: return "Types.BIGINT";
		case Types.DECIMAL: return "Types.DECIMAL";
		case Types.TIMESTAMP: return "Types.TIMESTAMP";
		case Types.FLOAT: return "Types.FLOAT";
		}
		return String.valueOf(i);
	}
	
	public void finalize() {		
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
