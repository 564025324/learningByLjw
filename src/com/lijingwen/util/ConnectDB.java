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
			conn = DriverManager.getConnection("jdbc:sqlserver://localhost",
					"sa", "ljw+545464");
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

	/**
	 * 插入
	 */
	public void insert() {
		System.out.println("-----------INSERT------------");
		conn = getConnection();
		if (conn != null) {
			String sql = "use lijingwen ;insert into users(userid,username,mobile,status) values ('1','1','1','1')";
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
			String sql = "use lijingwen ;select * from users";
			try {
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery(sql);
				while(rs.next()){
					String name = rs.getString(2);
					System.out.println(name);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
