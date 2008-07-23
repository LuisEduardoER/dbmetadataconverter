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

import java.util.ArrayList;
import java.util.List;

public class Relation {

	private Table pkTable;
	private Table fkTable;
	private List<Field> fieldsInPkTable = new ArrayList<Field>(3);
	private List<Field> fieldsInFkTable = new ArrayList<Field>(3);
	private Integer ordinalSequence;
		
	/**
	 * Returns the pk table of the relation (the one that exports the fields).
	 * @return
	 */
	public Table getPkTable() {
		return pkTable;
	}
	
	public void setPkTable(Table pkTable) {
		this.pkTable = pkTable;
	}
	
	/**
	 * Returns the fk table of the relation (the one that imports the fields).
	 * @return
	 */
	public Table getFkTable() {
		return fkTable;
	}
	
	public void setFkTable(Table fkTable) {
		this.fkTable = fkTable;
	}
	
	/**
	 * Returns a list with the fields that were exported by the pk table to the fk table.
	 * @return
	 */
	public List<Field> getFieldsInPkTable() {
		return fieldsInPkTable;
	}
	
	public void setFieldsInPkTable(List<Field> fieldsInPkTable) {
		this.fieldsInPkTable = fieldsInPkTable;
	}
	
	/**
	 * Returns a list with the fields that were imported by the fk table from the pk table.
	 * @return
	 */
	public List<Field> getFieldsInFkTable() {
		return fieldsInFkTable;
	}
	
	public void setFieldsInFkTable(List<Field> fieldsInFkTable) {
		this.fieldsInFkTable = fieldsInFkTable;
	}
	
	/**
	 * This method is very useful to work with multi relation between two tables.
	 * If one table imports from another table more than one time 
	 * (they have two or more relations) this method will return the relation number.
	 * For example, it will return 1 if it is the first relation, 2 if it is the second, etc.
	 * @return
	 */
	public Integer getOrdinalSequence() {
		return ordinalSequence;
	}
	
	public void setOrdinalSequence(Integer ordinalSequence) {
		this.ordinalSequence = ordinalSequence;
	}
	
	
}
