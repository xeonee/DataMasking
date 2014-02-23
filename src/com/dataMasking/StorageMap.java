package com.dataMasking;
import java.util.*;

public class StorageMap {
	private String storageMapName;
	private List table = new ArrayList();

	public void addTable(Table tables) {
		table.add(tables);
	}

	public List getTable() {
		return table;
	}

	public String getName() {
		return storageMapName;
	}

	public void setName(String name) {
		this.storageMapName = name;
	}

}
