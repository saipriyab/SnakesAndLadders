package com.example.game.util;

import java.sql.Connection;
import java.sql.DriverManager;

import com.example.game.bean.SnakeBean;

public class DBUtil {

	public static Connection getDatabaseConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
		} catch (Exception e) {
			e.printStackTrace();
			conn = null;
		}
		return conn;
	}

}
