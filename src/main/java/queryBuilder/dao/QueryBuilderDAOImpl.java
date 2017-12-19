package queryBuilder.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import queryBuilder.model.DBSchemas;
import queryBuilder.model.Procedures;
import queryBuilder.model.TableNames;
import queryBuilder.model.View;

@Service
public class QueryBuilderDAOImpl implements QueryBuilderDAO {

	private static final String SCHEMA_TEST_RT = "Financial_Advisor";
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/Financial_Advisor";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "admin";

	private static final String TABLE_NAME = "TABLE_NAME";
	private static final String TABLE_SCHEMA = "TABLE_SCHEM";
	private static final String[] VIEW_TYPES = { "VIEW" };

	private static final String TABLE_CAT = "TABLE_CAT";

	private static final String[] TABLE_TYPES = { "TABLE" };

	//
	//

	public static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}

	@Override
	public List<DBSchemas> getDatabaseTables() {
		String schemaName = null;
		Connection dbConnection = null;
		dbConnection = getDBConnection();

		List<DBSchemas> schemasList = new ArrayList<DBSchemas>();

		try {

			ResultSet resultSet = dbConnection.getMetaData().getCatalogs();
			ResultSet tableResultSet = dbConnection.getMetaData().getCatalogs();
			ResultSet columnsResultSet = dbConnection.getMetaData().getCatalogs();
			ResultSet proceduresResultSet = dbConnection.getMetaData().getCatalogs();
			ResultSet viewsResultSet = dbConnection.getMetaData().getCatalogs();

			while (resultSet.next()) {
				DBSchemas schema = new DBSchemas();

				// --- DATABASE SCHEMA NAMES ---
				schemaName = resultSet.getString(TABLE_CAT);
				schema.setName(schemaName);
				 System.out.println("Schema Name = " + schemaName);

//				if (schemaName.equals(SCHEMA_TEST_RT)) {
					// --- DATABASE VIEW NAMES ---
					viewsResultSet = getViews(schemaName, dbConnection, schema);

					// --- DATABASE PROCEDURES ---
					proceduresResultSet = getProcedures(schemaName, dbConnection, schema);

					if (resultSet.getString(TABLE_CAT) != null) {
						tableResultSet = dbConnection.getMetaData().getTables(schemaName, null, "%", TABLE_TYPES);
						// --- DATABASE TABLE NAMES ---
						columnsResultSet = getTables(schemaName, dbConnection, tableResultSet, columnsResultSet,
								schema);
					}
					schemasList.add(schema);
//					break;
//				}
			}

			resultSet.close();
			tableResultSet.close();
			columnsResultSet.close();
			proceduresResultSet.close();
			viewsResultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				dbConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return schemasList;
	}

	private ResultSet getTables(String schemaName, Connection dbConnection, ResultSet tableResultSet,
			ResultSet columnsResultSet, DBSchemas schema) throws SQLException {
		String tableName = "";
		List<TableNames> tablesList = new ArrayList<TableNames>();
		List<String> allTabNamesList = new ArrayList<String>();

		while (tableResultSet.next()) {
			TableNames tableNames = new TableNames();

			tableName = tableResultSet.getString(3);
			allTabNamesList.add(tableName);
			tableNames.setName(tableName);
			// System.out.println("Table Name = " + tableName);

			if (tableName != null) {
				// --- DATABASE COLUMN NAMES ---
				columnsResultSet = getColumns(schemaName, dbConnection, tableName, tableNames);
			}
			tablesList.add(tableNames);
		}
		//schema.setTableNames(tablesList);
		schema.setTableNamesList(allTabNamesList);
		return columnsResultSet;
	}

	private ResultSet getColumns(String schemaName, Connection dbConnection, String tableName, TableNames tableNames)
			throws SQLException {
		ResultSet resultSet3;
		DatabaseMetaData meta = dbConnection.getMetaData();
		resultSet3 = meta.getColumns(schemaName, null, tableName, "%");
		List<String> colNames = new ArrayList<String>();

		while (resultSet3.next()) {
			// System.out.println("Column Name of table " +
			// tableName + " = " + resultSet3.getString(4));
			colNames.add(resultSet3.getString(4));
		}
		tableNames.setColumnNames(colNames);
		return resultSet3;
	}

	private ResultSet getProcedures(String schemaName, Connection dbConnection, DBSchemas schema) throws SQLException {
		ResultSet resultSet4;
		// System.out.println("List of procedures: ");
		List<Procedures> storedProceduresList = new ArrayList<Procedures>();
		resultSet4 = dbConnection.getMetaData().getProcedures(schemaName, null, "%");
		List<String> allProceduresNamesList = new ArrayList<String>();
		while (resultSet4.next()) {
			String procName = resultSet4.getString(3);
			// System.out.println(procName);
			allProceduresNamesList.add(procName);

			Procedures proc = new Procedures();
			proc.setName(procName);

			storedProceduresList.add(proc);

			// System.out.println("
			// "+resultSet4.getString("PROCEDURE_CAT") + ",
			// "+resultSet4.getString("PROCEDURE_SCHEM") + ",
			// "+resultSet4.getString("PROCEDURE_NAME"));
		}
		System.out.println(storedProceduresList.size());
		schema.setStoredProcedures(storedProceduresList);
		schema.setStoredProcedureNamesList(allProceduresNamesList);
		return resultSet4;
	}

	private ResultSet getViews(String schemaName, Connection dbConnection, DBSchemas schema) throws SQLException {
		String viewName;
		ResultSet viewsResultSet;
		List<View> views = new ArrayList<View>();
		viewsResultSet = dbConnection.getMetaData().getTables(schemaName, null, null, VIEW_TYPES);
		while (viewsResultSet.next()) {
			View view = new View();
			viewName = viewsResultSet.getString(TABLE_NAME);
			view.setName(viewName);
			views.add(view);
		}
		schema.setViews(views);
		return viewsResultSet;
	}

	@Override
	public List<Map<String, Object>> getProcedureDetails(String proc) {
		return null;
	}

}
