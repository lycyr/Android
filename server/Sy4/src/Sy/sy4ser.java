package Sy;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.*;

import sun.net.www.http.HttpClient;
@WebServlet("/sy4ser")
public class sy4ser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sy4ser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		username = new String(username.getBytes(), "UTF-8");
		String password = request.getParameter("password");
		System.out.println(username + "--" + password);

		// 新建服务对象
		Sql sql = new Sql();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		// 验证处理
		boolean loged = sql.denglu(username, password);
		if (loged) {
			//JSONObject jsonObject = new JSONObject("{'success':'loginsuccess'}");
			// response.getWriter().write(jsonObject.toString());
			//System.out.println(jsonObject.get("success").toString());
			out.print("linksuccess");
		} else {
			//JSONObject jsonObject = new JSONObject("{'failed':'"+sql.errorhandle()+"'}");
			//System.out.println(jsonObject.get("failed").toString());
			//request.setAttribute(sql.errorhandle(),sql.errorhandle());
			System.out.println("Failed "+sql.errorhandle());
			out.print(sql.errorhandle());
		} 
		 out.flush();
		 out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
