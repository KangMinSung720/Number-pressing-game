package Project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DataBase {
	
	public void record(String time, String name) {
	Connection conn = null;
	PreparedStatement pstmt = null;
	String sql = "insert into record(name, time) values(?, ?)";
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/game", "root", "apmsetup");
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, name);
		pstmt.setString(2, time);
		pstmt.executeUpdate();
	} catch(Exception e) { 
		
	} finally {
		try {
			pstmt.close();
			conn.close();
		} catch(Exception ex) { }
	}
	
	} 

}
