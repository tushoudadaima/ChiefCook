package canMouZhang.buyer.controller;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import canMouZhang.buyer.dao.BuyerDao;

/**
 * Servlet implementation class AddBuyerServlet
 */
@WebServlet("/AddBuyerServlet")
public class AddBuyerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBuyerServlet() {
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
		String uname = request.getParameter("uname");
		if(buyerId!=null) {
			buyerDao.addBuyer(buyerId, buyerPassword,uname);
		}				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
