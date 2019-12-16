package canMouZhang.seller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import canMouZhang.util.DbUtil;

public class SellerDao {
	Connection con=null;
	PreparedStatement pstm = null;
	int a = 0;
	ResultSet rs ;
	String str="";
	//�������
	public int addSeller(String sellerId,String sellerPassword) {
		con = DbUtil.getCon();
		String sql = "insert into seller(sellerId,sellerPassword)values('"+sellerId+"','"+sellerPassword+"')";
		try {
			pstm = con.prepareStatement(sql);
			a = pstm.executeUpdate();
			return a;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}finally {
			DbUtil.close(con);
		}	
	}
	//����Ƿ�������
	public String check(String sellerId)  {
		con = DbUtil.getCon();
		String sqlString = "select * from seller"+" where sellerId = '"+sellerId+"'";
		try {
			pstm = con.prepareStatement(sqlString);
			rs =  pstm.executeQuery();
			while(rs.next())
				str+="������";
			return str;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return sqlString;
		}finally {
			DbUtil.close(con);
		}	
		
		
	}
	//��֤�û���¼
	public String checkSeller(String sellerId,String sellerPassword)  {
		con = DbUtil.getCon();
		String sqlString="select * from seller"+ " where sellerId= '"+sellerId+"'"+" and sellerPassword= '"+sellerPassword+"'";
		try {
			pstm = con.prepareStatement(sqlString);
			rs = pstm.executeQuery();
			while(rs.next())
				str = "id:"+rs.getString(1)+" ����:"+rs.getString(2)+"\n";
			return str;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return sqlString;
		}finally {
			DbUtil.close(con);
		}	
			
	}
	//������������
	public int updataSellerPassword(String sellerId,String sellerPassword ) {
		con = DbUtil.getCon();
		String sqlString ="update seller set sellerPassword='"+sellerPassword+"'"+" where sellerId='"+sellerId+"'";
		try {
			pstm = con.prepareStatement(sqlString);
			a = pstm.executeUpdate();
			return a;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}finally {
			DbUtil.close(con);
		}	

		
	}

}
