package act.mashup.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.json.JSONException;
import org.json.JSONML;

import com.google.gson.Gson;

import act.mashup.global.EngineManager;
import act.mashup.util.Log;

/**
 * Servlet implementation class EngineAssembler
 */

public class EngineAssembler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public EngineAssembler() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 在session中查找或者设置一个新的桩
		EngineManager emgr = new EngineManager();
		//emgr.BuildEngine(request.getParameter("xml"));
		
		emgr.BuildEngine(new String(request.getParameter("xml").getBytes("ISO8859-1"),"utf-8"));
		//Log.logger.info(request.getParameter("xml"));
		//System.out.println(request.getParameter("xml"));
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
			
			Log.logger.info(outputter.outputString(emgr.GetRlt()));
			
			
			

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
	}

}
