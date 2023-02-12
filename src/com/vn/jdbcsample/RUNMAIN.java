/**
 * 
 */
package com.vn.jdbcsample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.management.loading.PrivateClassLoader;

/**
 * @author Admin
 *
 */
public class RUNMAIN {

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
// Aplication (c/java) ------JDBC------    DB (oracal,Mysql)
		// java dât base connecting (orm)
		// Hibeete
		// Jpa

		// bộ thư viện hỗ trợ giao tiếp ứng dụng Java và DB
		// jbdc MYSQL
		// KẾT nối giữa 2 ứng dụng
		// Step 1 :creat connect
		// Step 2 :Thao tác với nhau
		// Step 3: đÓNG KẾT nối
		// Creat connecting App Java vs DB
		// C,R,U,D
		// Close.connec
		String HOST = "localhost";
		String PORT = "3306";
//	String DB_URL ="java24";
		String DB_NAME = "java24";
		// URL(IP,PORT,DB_NAME)
		String DB_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
		String USER_NAME = "root";
		String PASSWORD = "root@123";
		Connection conn = null;
//			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
		try {
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD); //

			if (conn != null) {
				System.out.println("Kết nối thành công");
				// Creat statement
				Statement stmt = null;// qua qua đối tượng này
				stmt = conn.createStatement();// slecrt ra sjwr dụng
				String SELECT = "select*from java24.Person";

				ResultSet resultset = stmt.executeQuery(SELECT);

				while (resultset.next()) {

					System.out.println("Lastname" + resultset.getString(2));
					System.out.println("FirtName" + resultset.getString(3));
					System.out.println("PerrsionID" + resultset.getString(1));

					System.out.println("Lasstname" + resultset.getString("Lastname"));
					System.out.println("PerrsionID" + resultset.getString("PersonID"));
				}

				String inssert = "insert into java24.Persions values (123,'Nam','Nguyen','Hoan KIem')";
				boolean check = stmt.execute(inssert);
				if (check) {
					System.out.println("Oke");

				}

				String UPDATE = "update java24.Persions set City = 'Da Nang '";
				int contRecord = stmt.executeUpdate(UPDATE);
				System.out.println("RECORD UPDATE" + contRecord);

				String DELETE = "delete java24.Persions where ID = 128";
				contRecord = stmt.executeUpdate(DELETE);
				System.out.println("RECORD DELETE" + contRecord);

			} else {
				System.out.println("Kết nối thất bại");
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
