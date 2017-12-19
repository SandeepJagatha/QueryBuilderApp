package queryBuilder.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import queryBuilder.model.DBSchemas;
import queryBuilder.responses.DataResponse;
import queryBuilder.responses.ResponseTypes;
import queryBuilder.service.QueryBuilderService;

@RequestMapping(value = "queryBuilder")
@RestController
public class QueryBuilderController {

	@Autowired
	private QueryBuilderService queryBuilderService;

	@CrossOrigin(origins = "*")
	@GetMapping("/getDatabaseTables") // new annotation since 4.3
	public List<DBSchemas> getDatabaseTables() {
		return this.queryBuilderService.getDatabaseTables();
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/isQueryValid") // new annotation since 4.3
	public DataResponse isQueryValid(@RequestParam String query) {
		System.out.println("isQueryValid" + query);
		DataResponse datares = new DataResponse();
		String queryValidator = this.queryBuilderService.queryValidator(query);
		if (queryValidator.equals("Valid")) {
			datares.setSuccess(ResponseTypes.SUCCESS);
			datares.setMessage(queryValidator);
			return datares;
		} else {
			datares.setSuccess(ResponseTypes.FAILED);
			datares.setMessage(queryValidator);
			return datares;
		}
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping("/getProcedureDetails") // new annotation since 4.3
	public List<Map<String, Object>> getProcedureDetails(@RequestParam String proc) {
		return this.queryBuilderService.getProcedureDetails(proc);
	}

}
