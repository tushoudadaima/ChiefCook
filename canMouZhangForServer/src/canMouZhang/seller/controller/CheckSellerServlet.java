package canMouZhang.seller.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import canMouZhang.buyer.dao.BuyerDao;
import canMouZhang.seller.dao.SellerDao;

/**
 * Servlet implementation class CheckSellerServ
 */
@WebServlet("/CheckSellerServlet")
public class CheckSellerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckSellerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SellerDao sellerDao = new SellerDao();	
		String sellerId= request.getParameter("sellerId");
		PrintWriter writer = response.getWriter();
		String string = sellerDao.check(sellerId);
		writer.write(string);
		System.out.println(string);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
