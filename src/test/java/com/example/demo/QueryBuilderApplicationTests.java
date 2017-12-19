package com.example.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import queryBuilder.dao.QueryBuilderDAOImpl2;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryBuilderApplicationTests {

	@Autowired
	private QueryBuilderDAOImpl2 qbDAO;

	@Test
	public void contextLoads() {
		// qbDAO.getProcedureDetails("diagnostics");
		System.out.println(qbDAO.getProcedureDetails("diagnostics"));
		assertEquals(3, qbDAO.getProcedureDetails("diagnostics").size());
		// qbDAO.getDatabaseTables();
	}
}
