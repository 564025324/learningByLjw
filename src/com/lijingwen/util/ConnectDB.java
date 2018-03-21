package com.lijingwen.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectDB {
	private Connection conn;

	/**
	 * 测试是否连接成功
	 * 
	 * @return connection
	 */
	public Connection getConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			conn = DriverManager.getConnection("jdbc:sqlserver://localhost", "root", "root");
			// Class.forName("com.mysql.jdbc.Driver");
			// conn = DriverManager.getConnection("jdbc:mysql://localhost", "root",
			// "root");
			if (conn == null) {
				System.out.println("数据库连接失败");
			} else {
				System.out.println("数据库连接成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void main(String[] args) {
		ConnectDB testdb = new ConnectDB();
		Connection connection = testdb.getConnection();
		testdb.select();
	}

	/**
	 * 插入
	 */
	public void insert(String sql) {
		System.out.println("-----------INSERT------------");
		conn = getConnection();
		if (conn != null) {
			try {
				Statement stm = conn.createStatement();
				int result = stm.executeUpdate(sql);
				System.out.println(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 插入
	 */
	public void select() {
		System.out.println("-----------select------------");
		conn = getConnection();
		if (conn != null) {
			String sql = "use lijingwen ;select * from lmsp_qhib_sxf_log ";
			try {
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery(sql);
				while (rs.next()) {
					String FUNDNAME = rs.getString(5);
					System.out.println(FUNDNAME);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
