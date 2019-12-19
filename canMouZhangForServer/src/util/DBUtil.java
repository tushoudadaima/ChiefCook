package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class DBUtil {
	private static Properties dbProps = new Properties();
	/**
	 * 读取配置文件，加载数据库驱动
	 */
	static {
		try {
			InputStream is = DBUtil.class.getResourceAsStream("../DBConfig.properties");
			dbProps.load(is);
			Class.forName(dbProps.getProperty("DRIVER"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	public static Connection getCon() { 
		try {
			return DriverManager.getConnection(dbProps.getProperty("CONN_STR"), dbProps.getProperty("USER"),
					dbProps.getProperty("PWD"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 关闭数据库连接等对象
	 * 
	 * @param rs
	 * @param pstm
	 * @param con
	 */
	public static void close(Connection con) {
		try {
			if (con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
