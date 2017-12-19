package queryBuilder.model;

import java.util.ArrayList;
import java.util.List;

public class Table {
	private String name;
	private List<Column> columns = new ArrayList<Column>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	@Override
	public String toString() {
		return "Table [name=" + name + ", columns=" + columns + "]";
	}

}
