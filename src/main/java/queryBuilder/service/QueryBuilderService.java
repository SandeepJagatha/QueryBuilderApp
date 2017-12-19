package queryBuilder.service;

import java.util.List;
import java.util.Map;

import queryBuilder.model.DBSchemas;

public interface QueryBuilderService {

	List<DBSchemas> getDatabaseTables();
	
	String queryValidator(String query);
	
	List<Map<String, Object>> getProcedureDetails(String proc);

}
