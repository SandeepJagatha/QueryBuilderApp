package queryBuilder.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import queryBuilder.model.View;

public class ViewsMapper implements RowMapper<View> {

	@Override
	public View mapRow(ResultSet rs, int rowNum) throws SQLException {
		View view = new View();
		view.setName(rs.getString("view"));
		return view;
	}

}
