package queryBuilder.model;

public class Column {
	private String name;
	private String dataType;
	private String columnType;
	private String length = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "Column [name=" + name + ", dataType=" + dataType + ", columnType=" + columnType + ", length=" + length
				+ "]";
	}

}
