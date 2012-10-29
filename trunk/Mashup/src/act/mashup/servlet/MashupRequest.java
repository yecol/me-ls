package act.mashup.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import act.mashup.global.MashletMaker;

/**
 * Servlet implementation class MashupRequest
 */

@WebServlet(name = "MashupRequest", urlPatterns = "/MashupRequest", asyncSupported = true)
public class MashupRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MashupRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		   response.setCharacterEncoding("utf-8");
           response.setContentType("text/html;charset=utf-8");
           PrintWriter out = response.getWriter();
           out.println("Servlet begin <br>");
                
           //进入异步模式,调用业务处理线程进行业务处理
           //Servlet不会被阻塞,而是直接往下执行
           //业务处理完成后的回应由AsyncContext管理
           AsyncContext asyncContext = request.startAsync();
           MashletMaker mashletMaker = new MashletMaker(asyncContext);
           Thread thread = new Thread(mashletMaker);
           thread.start();
                
           out.println("Servlet end <br>");
           out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
