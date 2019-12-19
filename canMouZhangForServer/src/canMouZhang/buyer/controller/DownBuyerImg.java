package canMouZhang.buyer.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class DownBuyerImg
 */
@WebServlet("/DownBuyerImg")
public class DownBuyerImg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownBuyerImg() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String buyerId= request.getParameter("buyerId");
		File file = new File("D:/upload/"+buyerId+".jpg");
		if(file.exists()) {
			InputStream in = new FileInputStream("D:/upload/"+buyerId+".jpg");
			OutputStream out = response.getOutputStream();
			byte[] bytes = new byte[1024];
			int n = -1;
			while((n = in.read(bytes)) != -1) {
				out.write(bytes, 0, n);
				out.flush();
			}
			System.out.println("下载"+buyerId);
			in.close();
			out.close();
		}else {
			InputStream in = new FileInputStream("D:/upload/touxiang.jpg");
			OutputStream out = response.getOutputStream();
			byte[] bytes = new byte[1024];
			int n = -1;
			while((n = in.read(bytes)) != -1) {
				out.write(bytes, 0, n);
				out.flush();
			}
	
			in.close();
			out.close();
			System.out.println("不存在该文件 使用默认头像");
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
