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
package code.google.com.dbmetadataconverter.model;

public class Field implements Comparable<Field>{

	
	private String name;
	private Class<?> type;
	private int sqlType;
	private boolean notNull;
	private String nameForClass;
	private String nameForAttribute;
	private boolean isKey;
	private Table table;
	private boolean isFk;
	private Table fkTable;
	private String comments;
	private Integer ordinalPosition;
	
	public Field() {
		
	}
	
	public Field(String name) {
		this.name = name;
	}
	
	public Field(String name, Table table) {
		this.name = name;
		this.table = table;
	}
	
	/**
	 * Returns true if this field is imported to this table or false otherwise.
	 * @return boolean
	 */
	public boolean isFk() {
		return isFk;
	}

	public void setFk(boolean isFk) {
		this.isFk = isFk;
	}
	
	/**
	 * Gets the Table that contains this field.
	 * @return
	 */
	public Table getTable() {
		return table;
	}
	
	public void setTable(Table table) {
		this.table = table;
	}
	
	/**
	 * Returns true if this field belongs to the primary key of the table or false otherwise.
	 * @return boolean
	 */
	public boolean isKey() {
		return isKey;
	}
	
	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}

	/**
	 * Returns the name of the field. This name is the same one that is on the database.
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the Class of the field. It returns the sql type converted into a java type. 
	 * Ex: if the field is a VARCHAR type on the database, it becomes a String in java. 
	 * @return
	 */
	public Class<?> getType() {
		return type;
	}
	
	public void setType(Class<?> type) {
		this.type = type;
	}
	
	/**
	 * Returns true if this field does not allow null entries in the database or false if
	 * the field is nullable.
	 * @return
	 */
	public boolean isNotNull() {
		return notNull;
	}
	
	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}
		
	/**
	 * Compares the field by the name of the field and the name of the table.
	 * @return
	 */
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Field)) return false;
		return ((Field)o).name.equals(name) && table.getName().equals(((Field)o).table.getName());
	}
	
	/**
	 * Compares one field to another. Returns 0 if they are equals. 
	 * The result is positive if this field is grater then the parameter field or negative
	 * if the parameter field is grater. 
	 */
	@Override
	public int compareTo(Field o) {
		int comp = name.compareTo(o.name);
		return comp != 0 ? comp : table.getName().compareTo(o.table.getName());
	}

	/**
	 * Returns the name of the field converted to the java code conventions definition
	 * for attributes variables. Ex: test_table becomes testTable.
	 * @return
	 */
	public String getNameForAttribute() {
		return nameForAttribute;
	}

	public void setNameForAttribute(String nameForAttribute) {
		this.nameForAttribute = nameForAttribute;
	}

	/**
	 * Returns the name of the field converted to the java code conventions definition
	 * for classes names. Ex: test_table becomes TestTable.
	 * @return
	 */
	public String getNameForClass() {
		return nameForClass;
	}

	public void setNameForClass(String nameForClass) {
		this.nameForClass = nameForClass;
	}

	/**
	 * If this field is an Fk field, this method can return the table that 
	 * exports this field.
	 * @return
	 */
	public Table getFkTable() {
		return fkTable;
	}

	public void setFkTable(Table fkTable) {
		this.fkTable = fkTable;
	}

	/**
	 * Returns the comments of this fields.
	 * @return
	 */
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * Return the field`s sql type. The result value is one of the java.sql.Types constants.
	 * @return
	 */
	public int getSqlType() {
		return sqlType;
	}

	public void setSqlType(int sqlType) {
		this.sqlType = sqlType;
	}
	
	/**
	 * Returns the short name of the Class. 
	 * Ex: If the Class is of java.lang.Integer type, the return value is: Integer.
	 * This method is very useful since you don`t have to pollute the code 
	 * breaking the Class name to get the class short name. 
	 * @return String
	 */
	public String getShortTypeName() {
		String name = getType().getName();
		if (name.lastIndexOf('.') > 0) {
	        name = name.substring(name.lastIndexOf('.')+1);  
	    }
		return name;
	}
	
	/**
	 * Gets the order of the field in the database table 
	 * (The order that appears on a Database GUI Tool for example).
	 * @return
	 */
	public Integer getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(Integer ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}
	
}
