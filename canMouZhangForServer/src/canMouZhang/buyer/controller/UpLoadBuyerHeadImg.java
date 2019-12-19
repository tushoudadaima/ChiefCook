package canMouZhang.buyer.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import canMouZhang.buyer.dao.BuyerDao;

/**
 * Servlet implementation class UpLoadBuyerHeadImg
 */
@WebServlet("/UpLoadBuyerHeadImg")
public class UpLoadBuyerHeadImg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpLoadBuyerHeadImg() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		BuyerDao buyerDao = new BuyerDao();
		String buyerId= request.getParameter("buyerId");
		InputStream in = request.getInputStream();

		OutputStream out = new FileOutputStream("D:/upload/"+buyerId+".jpg");
		String pathString = "D:/upload/"+buyerId+".jpg";
		byte[] bytes = new byte[1024];
		int n = -1;
		while((n = in.read(bytes)) != -1) {
			out.write(bytes, 0, n);
			out.flush();
		}
		System.out.println("上传");
		in.close();
		out.close();
		
		if(buyerId!=null) {
			int a = buyerDao.upLoadHeadImg(buyerId,pathString);
			if(a!=0) {				
				response.getWriter().write("头像上传成功");
			}else {
				response.getWriter().write("上传失败");
			}
		}	
		
	
	}

}
