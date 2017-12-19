package queryBuilder.model;

import java.util.ArrayList;
import java.util.List;

public class TableNames {
	String name;
	List<String> columnNames = new ArrayList<String>();

	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
