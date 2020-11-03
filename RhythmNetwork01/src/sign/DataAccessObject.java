package sign;

import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;

public class DataAccessObject {
	
	public static boolean create(DataTransferObject dto) throws Exception {
		
		Connection con = null;
		Statement st = null;
		
		boolean createFlag = false;
		
		String name = dto.getName();
		String password = dto.getPassword();
		String address = dto.getAddress();
		String sql = "INSERT INTO member(name, password, address) VALUES";
		
		try {
			sql += "('" + new String(name.getBytes(), "ISO-8859-1") + "','"
					+ new String(password.getBytes(), "ISO-8859-1") + "','"
					+ new String(address.getBytes(), "ISO-8859-1") + "')";
			
			Class.forName("com.mysql.cj.jdbc.Driver"); // mysql driver manager.
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/account?serverTimezone=Asia/Seoul",
					"root", "wnsgh1524");
			st = (Statement)con.createStatement();
			st.executeUpdate(sql); // execute sql.
			
			// create success...
			createFlag = true;
			System.out.println("DB Connection success... and Create Success...");
			
		} catch(Exception e) {
			createFlag = false;
			System.out.println("Connection Error... " + e.getMessage());	
		} finally {
			// close statement and connection
			try {
				if(st != null) {
					st.close();
				}
				
				if(con != null) {
					con.close();
				}
			} catch(Exception e) {
				System.out.println("Close error... " + e.getMessage());
			}
		}
		
		return createFlag;
	}
}
