package canMouZhang.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static Connection getCon() {
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/ourproject","root","");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void close(Connection con) {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
