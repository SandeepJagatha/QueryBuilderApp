package queryBuilder.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import queryBuilder.mapper.ProceduresMapper;
import queryBuilder.mapper.ViewsMapper;
import queryBuilder.model.Column;
import queryBuilder.model.DBSchemas;
import queryBuilder.model.Parameter;
import queryBuilder.model.Procedures;
import queryBuilder.model.Schema;
import queryBuilder.model.Table;
import queryBuilder.model.View;

@Repository
public class QueryBuilderDAOImpl2 implements QueryBuilderDAO {

	private static final String VIEWS_QUERY = "SELECT TABLE_NAME as view\n"
			+ "FROM information_schema.tables WHERE TABLE_TYPE LIKE 'VIEW' AND TABLE_SCHEMA LIKE ?";
	private static final String SHOW_PROCEDURE_QUERY = "SHOW PROCEDURE STATUS WHERE db = ?";
	private static final String TABLES_QUERY = "SELECT UPPER(TABLE_NAME) as TABLE_NAME, UPPER(COLUMN_NAME) as COLUMN_NAME, concat(UPPER(TABLE_NAME), \".\", UPPER(COLUMN_NAME)) as TABLE_COLUMN_NAME, Data_Type, COLUMN_TYPE\n"
			+ "    FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA=? ORDER BY TABLE_NAME;";
	private static final String SCHEMAS_QUERY = "SELECT SCHEMA_NAME AS `schema_name` FROM INFORMATION_SCHEMA.SCHEMATA";
	private static final String SCHEMA_TEST_RT = "information_schema";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<DBSchemas> getDatabaseTables() {
		List<DBSchemas> schemasList = new ArrayList<DBSchemas>();
		List<Schema> schemas = getSchemas();

		for (Schema schema : schemas) {
			DBSchemas dBSchemas = new DBSchemas();
			String schemaName = schema.getSchemaName();

			dBSchemas.setName(schemaName);

//			if (schemaName != null && schemaName.equals(SCHEMA_TEST_RT)) {
				List<String> allTableNames = new ArrayList<String>();

				List<Table> tables = getTables(schemaName);
				for (Table table : tables) {
					allTableNames.add(table.getName());
				}

				dBSchemas.setViews(getViews(schemaName));
				dBSchemas.setStoredProcedures(getProcedures(schemaName));
				dBSchemas.setTableNamesList(allTableNames);
				dBSchemas.setTableNames(getTables(schemaName));
//			}

			schemasList.add(dBSchemas);
		}

		return schemasList;
	}

	private List<Schema> getSchemas() {
		List<Schema> schemas = new ArrayList<Schema>();

		for (Map<String, Object> map : jdbcTemplate.queryForList(SCHEMAS_QUERY)) {
			Schema schema = new Schema();
			schema.setSchemaName((String) map.get("schema_name"));
			schemas.add(schema);
		}

		return schemas;
	}

	private List<Table> getTables(String schemaName) {
		List<Table> tables = new ArrayList<Table>();
		Table table = null;
		String curTableName = null;

		for (Map<String, Object> map : jdbcTemplate.queryForList(TABLES_QUERY, new Object[] { schemaName })) {
			String tableName = (String) map.get("TABLE_NAME");
			String columnName = (String) map.get("COLUMN_NAME");
			String dataType = (String) map.get("Data_Type");
			String columnType = (String) map.get("COLUMN_TYPE");
			if (!tableName.equals(curTableName)) {
				table = new Table();
				table.setName(tableName);
				curTableName = tableName;
				tables.add(table);
			}
			Column column = new Column();
			column.setColumnType(columnType);
			column.setDataType(dataType);
			column.setName(columnName);

			Matcher matcher = Pattern.compile("\\(([^)]+)\\)").matcher(columnType);
			while (matcher.find()) {
				column.setLength(matcher.group(1));
			}

			table.getColumns().add(column);
		}
		return tables;
	}

	private List<Procedures> getProcedures(String schemaName) {
		List<Procedures> procedures = jdbcTemplate.query(SHOW_PROCEDURE_QUERY, new Object[] { schemaName },
				new ProceduresMapper());

		for (Procedures proc : procedures) {
			List<Parameter> inputs = new ArrayList<Parameter>();
			List<Parameter> outputs = new ArrayList<Parameter>();

			for (Map<String, Object> map : getProcedureDetails(proc.getName())) {
				Parameter parameter = new Parameter();
				String specific_name = (String) map.get("specific_name");
				String parameter_mode = (String) map.get("parameter_mode");
				String parameter_name = (String) map.get("parameter_name");
				String dtd_identifier = (String) map.get("dtd_identifier");

				Matcher matcher = Pattern.compile("\\(([^)]+)\\)").matcher(dtd_identifier);
				while (matcher.find()) {
					parameter.setCharacter_maximum_length(matcher.group(1));
				}

				parameter.setDtd_identifier(dtd_identifier);
				parameter.setParameter_mode(parameter_mode);
				parameter.setParameter_name(parameter_name);
				parameter.setSpecific_name(specific_name);

				if (parameter_mode.equals("IN")) {
					inputs.add(parameter);
				} else {
					outputs.add(parameter);
				}
			}
			proc.setInputs(inputs);
			proc.setOutputs(outputs);
		}

		return procedures;
	}

	private List<View> getViews(String schemaName) {
		List<View> views = jdbcTemplate.query(VIEWS_QUERY, new Object[] { schemaName }, new ViewsMapper());
		return views;
	}

	@Override
	public List<Map<String, Object>> getProcedureDetails(String proc) {
		String procQuery = "SELECT specific_name, parameter_mode, parameter_name, character_maximum_length, dtd_identifier\n"
				+ "FROM information_schema.parameters \n" + "WHERE SPECIFIC_NAME  = ?";
		return jdbcTemplate.queryForList(procQuery, new Object[] { proc });
	}
}
