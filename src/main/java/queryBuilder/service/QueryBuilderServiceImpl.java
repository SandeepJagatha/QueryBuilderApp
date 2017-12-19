package queryBuilder.service;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import queryBuilder.dao.QueryBuilderDAO;
import queryBuilder.model.DBSchemas;

@Service
public class QueryBuilderServiceImpl implements QueryBuilderService {

	@Autowired
	@Qualifier("queryBuilderDAOImpl2")
	private QueryBuilderDAO queryBuilderDAO;

	public List<DBSchemas> getDatabaseTables() {
		return this.queryBuilderDAO.getDatabaseTables();
	}

	@Override
	public String queryValidator(String query) {
		CCJSqlParserManager sqlParser = new CCJSqlParserManager();
		try {
			Statement statement = sqlParser.parse(new StringReader(query));
			if(statement instanceof Select){
				Select selectStatement = (Select) statement;
				SelectBody selectBody = selectStatement.getSelectBody();
				if(selectBody != null){
					System.out.println("selectBody : " + selectBody);
					PlainSelect plainSelect = (PlainSelect) selectBody;
					
				}
			}
			return "Valid";
		} catch (JSQLParserException e) {
			String message = getStackTrace(e);

			String msg = ExceptionUtils.getRootCauseMessage(e);
			System.out.println("NOT VALID SQLmsg : " + msg);

			/*System.out.println(" getMessage : " + ExceptionUtils.getMessage(e));
			System.out.println(" getRootCauseMessage : " + ExceptionUtils.getRootCauseMessage(e));
			System.out.println(" getThrowableCount : " + ExceptionUtils.getThrowableCount(e));
			System.out.println(" getThrowables : ");

			for (final Throwable element : ExceptionUtils.getThrowables(e)) {
				System.out.println(element.getMessage());
			}

			System.out.println(" indexOfThrowable(e,RuntimeException.class) : "
					+ ExceptionUtils.indexOfThrowable(e, RuntimeException.class));
			System.out.println(
					" indexOfThrowable(e,Throwable.class) : " + ExceptionUtils.indexOfThrowable(e, Throwable.class));
			System.out.println(" indexOfType(e,RuntimeException.class) : "
					+ ExceptionUtils.indexOfType(e, RuntimeException.class));
			System.out.println(" indexOfType(e,Throwable.class) : " + ExceptionUtils.indexOfType(e, Throwable.class));
			System.out.println(" getCause : " + ExceptionUtils.getCause(e));
			System.out.println(" getRootCause : " + ExceptionUtils.getRootCause(e));
			System.out.println(" getRootCauseStackTrace : ");

			for (final String element : ExceptionUtils.getRootCauseStackTrace(e)) {
				System.out.println(element);
			}*/
			return msg;
		}
	}

	public static String getStackTrace(final Throwable throwable) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
	}

	@Override
	public List<Map<String, Object>> getProcedureDetails(String proc) {
		return queryBuilderDAO.getProcedureDetails(proc);
	}
}
