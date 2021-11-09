package com.yedam.member;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



@WebServlet("/GetScheduleServlet")
public class GetScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public GetScheduleServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		MemDAO dao = new MemDAO();
		List<Map<String, String>> list = dao.getSchedule();
		
		Gson gson = new GsonBuilder().create();
		response.getWriter().println(gson.toJson(list));
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		
		String title = request.getParameter("title");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		
		MemDAO dao = new MemDAO();
		boolean insertOK = dao.addSchedule(title, start, end);
		if(insertOK) {
			// {"retCode":"OK"}
			response.getWriter().print("{\"retCode\":\"OK\"}");
		} else {
			// {"retCode":"NG"}
			response.getWriter().print("{\"retCode\":\"NG\"}");
		}
		
	}

}
