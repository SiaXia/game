package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class DBConnection {

	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	public DBConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/foxmon?serverTimezone=Asia/Seoul", "root", "wnsgh1524");
			st = con.createStatement();
			
			System.out.println("DB connection success...");
		} catch(Exception e) {
			System.out.println("DB connection error..." + e.getMessage());
		}
	}
	
	public boolean isAdmin(String adminID, String adminPassword) {
		try {
			// mysql에서 문자열은 ' ' 으로 구분하기 때문에, 이거에 주의해서 작성.
			String sql = "SELECT * FROM ADMIN WHERE adminID = '" + adminID + "' and adminPassword = '" + adminPassword + "'";
			rs = st.executeQuery(sql);
			
			if(rs.next()) {
				return true;
			}
		} catch(Exception e) {
			System.out.println("DB search error..." + e.getMessage());
		}
		
		return false;
	}
}
