package com.halifaxcarpool;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class HalifaxCarpoolApplicationTests {

	@Value("${spring.datasource.password}")
	String password;

	@Value("${spring.datasource.username}")
	String userName;

	@Value("${spring.datasource.url}")
	String url;

	@Test
	void contextLoads() {
	}

	@Test
	void initialTest() {

	}
//	@Test
//	void testDbConnection() {
//		try {
//			Connection connection = DatabaseConnector.getConnection(url, userName, password);
//			PreparedStatement ps = connection.prepareStatement("select * from movie");
//			ResultSet rs = ps.executeQuery();
//			while (rs.next()) {
//				System.out.println(rs.getString(1));
//			}
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}

}
