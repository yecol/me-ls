package act.mashup.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.json.JSONException;
import org.json.JSONML;

import act.mashup.global.EngineManager;
import act.mashup.global.Mashlet;
import act.mashup.global.MashletMaker;
import act.mashup.global.StreamEngineManager;

/**
 * Servlet implementation class MashupRequest
 */

@WebServlet(name = "MashupRequest", urlPatterns = "/engine", asyncSupported = true)
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

		System.out.println("receive a request.");
		// 在session中查找或者设置一个新的桩
		StreamEngineManager emgr = new StreamEngineManager();

		if (emgr.BuildEngine(new String(request.getParameter("xml").getBytes("ISO8859-1"), "utf-8"))) {
			String outputType = request.getParameter("opt");

			if (outputType != null && outputType.equals("json")) {

				response.setContentType("text/plain");
				// 指定响应类型
				response.setCharacterEncoding("gb2312");
				PrintWriter out = response.getWriter();
				// 获得书写器

				Format format = Format.getCompactFormat();
				format.setEncoding("gb2312");
				XMLOutputter outputter = new XMLOutputter(format);

				try {
					out.println(JSONML.toJSONArray(outputter.outputString(emgr.GetRlt())));
				} catch (JSONException e) {
					e.printStackTrace();
				}

				out.flush();
				out.close();

			} else {

				response.setContentType("text/xml");
				// 指定响应类型
				response.setCharacterEncoding("gb2312");
				PrintWriter out = response.getWriter();
				// 获得书写器

				Format format = Format.getCompactFormat();
				format.setEncoding("gb2312");
				XMLOutputter outputter = new XMLOutputter(format);

				outputter.output(emgr.GetRlt(), out);

				out.flush();
				out.close();

			}
		} else {
			
			//进入mashlet分支
			System.out.println("mashlet() run");
			AsyncContext asyncContext = request.startAsync();
			MashletMaker mashletMaker = new MashletMaker(asyncContext);
			Thread thread = new Thread(mashletMaker);
			thread.start();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("receive a request.");
		this.doGet(request, response);
	}

}
