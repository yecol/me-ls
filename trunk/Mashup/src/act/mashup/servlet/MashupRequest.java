package act.mashup.servlet;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import act.mashup.global.EngineManager;
import act.mashup.global.Mashlet;
import act.mashup.global.MashletMaker;

/**
 * Servlet implementation class MashupRequest
 */

@WebServlet(name = "MashupRequest", urlPatterns = "/MashupRequest", asyncSupported = true)
public class MashupRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EngineManager emgr;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MashupRequest() {
		super();
		// TODO Auto-generated constructor stub
		emgr = new EngineManager();

		Mashlet.mashletQueue = emgr.mashletsQueue;
		Mashlet.queueMap = emgr.queueMap;
		Mashlet.startTimes = emgr.startTimes;
		
		MashletMaker.mashletQueue = emgr.mashletsQueue;
		MashletMaker.queueMap = emgr.queueMap;
		MashletMaker.startTimes = emgr.startTimes;
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
//		   response.setCharacterEncoding("utf-8");
//           response.setContentType("text/html;charset=utf-8");
//           PrintWriter out = response.getWriter();
//           out.println("Servlet begin <br>");
                
           //进入异步模式,调用业务处理线程进行业务处理
           //Servlet不会被阻塞,而是直接往下执行
           //业务处理完成后的回应由AsyncContext管理
           AsyncContext asyncContext = request.startAsync();
           MashletMaker mashletMaker = new MashletMaker(asyncContext);
           Thread thread = new Thread(mashletMaker);
           thread.start();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
