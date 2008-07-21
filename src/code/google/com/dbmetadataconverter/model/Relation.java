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
		
	public Table getPkTable() {
		return pkTable;
	}
	public void setPkTable(Table pkTable) {
		this.pkTable = pkTable;
	}
	public Table getFkTable() {
		return fkTable;
	}
	public void setFkTable(Table fkTable) {
		this.fkTable = fkTable;
	}
	public List<Field> getFieldsInPkTable() {
		return fieldsInPkTable;
	}
	public void setFieldsInPkTable(List<Field> fieldsInPkTable) {
		this.fieldsInPkTable = fieldsInPkTable;
	}
	public List<Field> getFieldsInFkTable() {
		return fieldsInFkTable;
	}
	public void setFieldsInFkTable(List<Field> fieldsInFkTable) {
		this.fieldsInFkTable = fieldsInFkTable;
	}
	public Integer getOrdinalSequence() {
		return ordinalSequence;
	}
	public void setOrdinalSequence(Integer ordinalSequence) {
		this.ordinalSequence = ordinalSequence;
	}
	
	
}
