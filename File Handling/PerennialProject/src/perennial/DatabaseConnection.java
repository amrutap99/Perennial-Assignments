package perennial;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private ResultSet rs;
	
	
	

	public static void getConnection() {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/filerecord", "root", "root");
			stmt = conn.createStatement();

			if (conn != null) {
				System.out.println("Connected !");
				// conn.close();
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insertValidFilerecord(String commaSeparated, File file) throws SQLException {
		//System.out.println("File name =" + file.getName() + " \n " + "valid record = " + read);
		String query ="SELECT COUNT(validrecordid) FROM validrecord vr WHERE vr.filename = '" + file.getName() + "' AND vr.record = '" + commaSeparated + "'";
		rs = stmt.executeQuery(query);
		
		int duplicateCount=0;
		while(rs.next()) {
			duplicateCount = rs.getInt(1);
			//System.out.println(rs.getInt(1));
		}
		
		if(duplicateCount == 0 ) {
			
			String sql = "INSERT into validrecord(filename,record) VALUES('" + file.getName() + "','" + commaSeparated + "')";
			stmt.executeUpdate(sql);
		}


	}

	public void insertInValidFilerecord(String commaSeparated, File file) throws SQLException {
		//System.out.println("File name =" + file.getName() + " \n " + "valid record = " + read);
		String query ="SELECT COUNT(invalidrecordid) FROM invalidrecord vr WHERE vr.filename = '" + file.getName() + "' AND vr.record = '" + commaSeparated + "'";
		rs = stmt.executeQuery(query);
		
		int duplicateCount=0;
		while(rs.next()) {
			duplicateCount = rs.getInt(1);
			//System.out.println(rs.getInt(1));
		}
		
		if(duplicateCount == 0 ) {
			
			String sql = "INSERT into invalidrecord(filename,record) VALUES('" + file.getName() + "','" + commaSeparated + "')";
			stmt.executeUpdate(sql);
		}

		

	}


}
