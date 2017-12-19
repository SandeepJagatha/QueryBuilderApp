package queryBuilder.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import queryBuilder.model.Procedures;
import queryBuilder.model.Table;

public class ProceduresMapper implements RowMapper<Procedures> {
	@Override
	public Procedures mapRow(ResultSet rs, int rowNum) throws SQLException {
		Procedures procedure = new Procedures();
		procedure.setName(rs.getString("NAME"));
		return procedure;
	}

}
