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
	
	public boolean isFk() {
		return isFk;
	}

	public void setFk(boolean isFk) {
		this.isFk = isFk;
	}
	
	public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}
	public boolean isKey() {
		return isKey;
	}
	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Class<?> getType() {
		return type;
	}
	public void setType(Class<?> type) {
		this.type = type;
	}
	public boolean isNotNull() {
		return notNull;
	}
	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}
	
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Field)) return false;
		return ((Field)o).name.equals(name) && table.getName().equals(((Field)o).table.getName());
	}

	@Override
	public int compareTo(Field o) {
		int comp = name.compareTo(o.name);
		return comp != 0 ? comp : table.getName().compareTo(o.table.getName());
	}

	public String getNameForAttribute() {
		return nameForAttribute;
	}

	public void setNameForAttribute(String nameForAttribute) {
		this.nameForAttribute = nameForAttribute;
	}

	public String getNameForClass() {
		return nameForClass;
	}

	public void setNameForClass(String nameForClass) {
		this.nameForClass = nameForClass;
	}
	public Table getFkTable() {
		return fkTable;
	}

	public void setFkTable(Table fkTable) {
		this.fkTable = fkTable;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getSqlType() {
		return sqlType;
	}

	public void setSqlType(int sqlType) {
		this.sqlType = sqlType;
	}
	
	public String getShortTypeName() {
		String name = getType().getName();
		if (name.lastIndexOf('.') > 0) {
	        name = name.substring(name.lastIndexOf('.')+1);  
	    }
		return name;
	}
	
	public Integer getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(Integer ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}
	
}
