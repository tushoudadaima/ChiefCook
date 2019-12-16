package canMouZhang.buyer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import canMouZhang.util.DbUtil;

public class BuyerDao {
	Connection con=null;
	PreparedStatement pstm = null;
	ResultSet rs ;
	String str="";
	int a = 0;
	
	//������
	public int addBuyer(String buyerId,String buyerPassword) {
		con = DbUtil.getCon();
		String sql = "insert into buyer(buyerId,buyerPassword)values('"+buyerId+"','"+buyerPassword+"')";
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
	//�����������
	public String check(String buyerId){
		con = DbUtil.getCon();
		String sqlString = "select * from buyer"+" where buyerId = '"+buyerId+"'";
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
	public String checkBuyer(String buyerId,String buyerPassword) {
		con = DbUtil.getCon();
		String sqlString="select * from buyer"+ " where buyerId= '"+buyerId+"'"+" and buyerPassword= '"+buyerPassword+"'";
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
	//�����������
	public int updataBuyerPassword(String buyerId,String buyerPassword ) {
		con = DbUtil.getCon();
		String sqlString ="update buyer set buyerPassword='"+buyerPassword+"'"+" where buyerId='"+buyerId+"'";
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
	
	//buyer�ϴ�ͷ��
	public int upLoadHeadImg(String buyerId, String pathString) {
		// TODO Auto-generated method stub
		con = DbUtil.getCon();
		String sqlString ="update buyer set buyerHeadImg='"+pathString+"'"+" where buyerId='"+buyerId+"'";
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
