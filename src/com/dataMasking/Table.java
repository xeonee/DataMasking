package com.dataMasking;
import java.util.ArrayList;
import java.util.List;

public class Table {
	private String name;
	private String pkid;
	private String schema;

	private List column = new ArrayList();

	public Table(String name, List columns, String schema ) {
		this.name = name;
		this.column = columns;
		this.schema = schema;
	}

	public Table() { }


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addColumn(List columns) {
		column.add(columns);
	}

	public List getColumn() {
		return column;
	}

	public String getPkid() {
		return pkid;
	}

	public void setPkid(String pkid) {
		this.pkid = pkid;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}
}
