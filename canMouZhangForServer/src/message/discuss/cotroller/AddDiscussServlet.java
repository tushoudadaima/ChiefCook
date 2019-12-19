package message.discuss.cotroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import message.discuss.service.DiscussService;




/**
 * Servlet implementation class AddDiscussServlet
 */
@WebServlet("/AddDiscussServlet")
public class AddDiscussServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddDiscussServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String uname = request.getParameter("uname");
		String vname = request.getParameter("vname");
		String time = request.getParameter("time");
		String content = request.getParameter("content");
		String buyerId = request.getParameter("buyerId");
		DiscussService discussService = new DiscussService();
		int a = discussService.add(uname, vname, time, content,buyerId);
		PrintWriter out = response.getWriter();
		out.write(a);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
