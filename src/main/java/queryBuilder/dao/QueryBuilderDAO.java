package queryBuilder.dao;

import java.util.List;
import java.util.Map;

import queryBuilder.model.DBSchemas;

public interface QueryBuilderDAO {

	List<DBSchemas> getDatabaseTables();
	
	List<Map<String, Object>> getProcedureDetails(String proc);

}
