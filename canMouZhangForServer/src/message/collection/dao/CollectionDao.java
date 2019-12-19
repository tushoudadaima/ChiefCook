package message.collection.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import pachong.search.entity.Show;
import util.DBUtil;

public class CollectionDao {
	private DBUtil dbUtil = new DBUtil();
	public int addCollection(String uname,String vname) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int i = 0;
		try {
			String sql = "insert into collection (uname,vname)values('"+uname+"','"+vname+"')";
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
	
	public int deleteCollection(String uname,String vname) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int i = 0;
		try {
			String sql = "delete from collection where uname=? and vname=?";
			conn = dbUtil.getCon();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, uname);
			pstm.setString(2,vname);
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
	
	public List<Show> findCollection(String uname) throws IOException{
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Show> list = new ArrayList<>();
		try {
			String sql = "select vname from collection where uname=?";
			conn = dbUtil.getCon();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, uname);
			rs = pstm.executeQuery();
			while(rs.next()) {
				String url = "https://www.douguo.com/search/recipe/"+rs.getString(1);
				Document document = Jsoup.connect(url).get();
				Elements elements = document.getElementsByClass("cook-list");
		        Elements li = elements.select("li");
		        Show show = new Show();
	        	String url2 = li.get(0).select("a").attr("href");
	        	String imgUrl = "https://www.douguo.com"+url2;
	        	Document document2 = Jsoup.connect(imgUrl).get();
	        	Elements elements2 = document2.getElementsByClass("wb100");
	        	Elements div = document.getElementsByClass("cook-info");
	        	String name = div.get(0).getElementsByClass("cookname text-lips ").text();
	        	String description = div.get(0).select("p").text();
	        	String imgUrl2 = elements2.get(0).attr("src");
	        	show.setName(name);
	        	show.setDescription(description);
	        	show.setImgUrl(imgUrl2);
	        	list.add(show);
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
	
	public String check(String uname,String vname) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getCon();
			String sql = "select * from collection where uname=? and vname=?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, uname);
			pstm.setString(2, vname);
			rs = pstm.executeQuery();
			if(rs.next()) {
				return "сп";
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			dbUtil.close(conn);
		}
		
	}
}
