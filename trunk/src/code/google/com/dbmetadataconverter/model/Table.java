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

public class Table implements Comparable<Table>{
	
	private String name;
	private List<Field> fields = new ArrayList<Field>();
	private List<Field> keys = new ArrayList<Field>(3);
	private List<Relation> pkRelations = new ArrayList<Relation>(3);
	private List<Relation> fkRelations = new ArrayList<Relation>(3);
	private boolean keyAutoInc;
	private String nameForClass;
	private String nameForAttribute;
	private String sequenceName;
	
	public Table() {
		
	}
	
	public Table(String name) {
		this.name = name;
	}
	
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	public List<Field> getKeys() {
		return keys;
	}
	public void setKeys(List<Field> chaves) {
		this.keys = chaves;
	}
	
	public boolean isKeyAutoInc() {
		return keyAutoInc;
	}
	public void setKeyAutoInc(boolean keyAutoInc) {
		this.keyAutoInc = keyAutoInc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Table)) return false;
		return ((Table)o).name.equals(name);
	}
	
	@Override
	public int compareTo(Table o) {		
		return name.compareTo(o.name);
	}

	public List<Relation> getPkRelations() {
		return pkRelations;
	}

	public void setPkRelations(List<Relation> pkRelations) {
		this.pkRelations = pkRelations;
	}

	public List<Relation> getFkRelations() {
		return fkRelations;
	}

	public void setFkRelations(List<Relation> fkRelations) {
		this.fkRelations = fkRelations;
	}

	public String getNameForClass() {
		return nameForClass;
	}

	public void setNameForClass(String nameForClass) {
		this.nameForClass = nameForClass;
	}

	public String getNameForAttribute() {
		return nameForAttribute;
	}

	public void setNameForAttribute(String nameForAttribute) {
		this.nameForAttribute = nameForAttribute;
	}

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
}
