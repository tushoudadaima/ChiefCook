package canMouZhang.buyer.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import canMouZhang.buyer.dao.BuyerDao;

/**
 * Servlet implementation class UpdataBuyerServlet
 */
@WebServlet("/UpdataBuyerServlet")
public class UpdataBuyerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdataBuyerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BuyerDao buyerDao = new BuyerDao();
		String buyerId= request.getParameter("buyerId");
		String buyerPassword= request.getParameter("buyerPassword");
		PrintWriter writer = response.getWriter();
		int a = buyerDao.updataBuyerPassword(buyerId, buyerPassword);
//		writer.write(a);
		System.out.println(a);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
