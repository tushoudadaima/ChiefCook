package message.discuss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import message.entity.Discuss;
import util.DBUtil;


public class DiscussDao {
	private DBUtil dbUtil = new DBUtil();
	public int addDiscuss(String uname,String vname,String time,String content,String buyerId) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int i = 0;
		try {
			String sql = "insert into discuss (uname,vname,time,content,buyerId)values('"+uname+"','"+vname+"','"+time+"','"+content+"','"+buyerId+"')";
			conn = dbUtil.getCon();
			pstm = conn.prepareStatement(sql);
			i = pstm.executeUpdate();
			return i;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}finally {
			dbUtil.close(conn);
		}
	}
	
	public List<Discuss> findDiscuss(String vname){
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Discuss> list = new ArrayList<Discuss>();
		try {
			String sql = "select uname,time,content,buyerId from discuss where vname=?";
			conn = dbUtil.getCon();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, vname);
			rs = pstm.executeQuery();
			while(rs.next()) {
				Discuss discuss = new Discuss();
				discuss.setUname(rs.getString(1));
				discuss.setTime(rs.getString(2));
				discuss.setContent(rs.getString(3));
				discuss.setBuyerId(rs.getString(4));
				list.add(discuss);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			dbUtil.close(conn);
		}
		
	}
}
