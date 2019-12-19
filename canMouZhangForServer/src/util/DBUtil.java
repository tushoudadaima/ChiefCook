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
	 * ��ȡ�����ļ����������ݿ�����
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
	 * ��ȡ���ݿ�����
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
	 * �ر����ݿ����ӵȶ���
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
