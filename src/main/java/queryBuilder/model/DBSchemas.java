package queryBuilder.model;

import java.util.ArrayList;
import java.util.List;

public class DBSchemas {
	String name;
	List<Table> tableNames = new ArrayList<Table>();
	List<Procedures> storedProcedures = new ArrayList<Procedures>();
	List<View> views = new ArrayList<View>();
	List<String> tableNamesList = new ArrayList<String>();
	List<String> storedProcedureNamesList = new ArrayList<String>();

	public List<Table> getTableNames() {
		return tableNames;
	}

	public void setTableNames(List<Table> schemaNames) {
		this.tableNames = schemaNames;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Procedures> getStoredProcedures() {
		return storedProcedures;
	}

	public void setStoredProcedures(List<Procedures> storedProcedures) {
		this.storedProcedures = storedProcedures;
	}

	public List<String> getTableNamesList() {
		return tableNamesList;
	}

	public void setTableNamesList(List<String> tableNamesList) {
		this.tableNamesList = tableNamesList;
	}

	public List<String> getStoredProcedureNamesList() {
		return storedProcedureNamesList;
	}

	public void setStoredProcedureNamesList(List<String> storedProcedureNamesList) {
		this.storedProcedureNamesList = storedProcedureNamesList;
	}

	public List<View> getViews() {
		return views;
	}

	public void setViews(List<View> views) {
		this.views = views;
	}

}
