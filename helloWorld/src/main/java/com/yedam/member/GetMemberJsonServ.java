package com.yedam.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@WebServlet("/GetMemberJsonServ")
public class GetMemberJsonServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetMemberJsonServ() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		// {"name":"Hongkildong", "age":20, "phone":"010-1234-5678"}
		// "[{\"id\":\"?\", \"name\":?, birth:?, gender:?}, {}, {}]";
		MemDAO dao = new MemDAO();
		List<MemberVO> list = dao.getMemberList();
//		int cnt = list.size();
//		out.println("[");
//		for (int i = 0; i < cnt; i++) {
//			out.println("{\"id\": \"" + list.get(i).getUserId() //
//					+ "\", \"name\": \"" + list.get(i).getUserName() //
//					+ "\", \"birth\": \"" + list.get(i).getBirthDate().substring(0, 10) //
//					+ "\", \"gender\": \"" + list.get(i).getGender() //
//					+ "\"}");
//			if (i != cnt - 1) {
//				out.println(",");
//			}
//		}
//		out.println("]");
		Gson gson = new GsonBuilder().create();
		out.println(gson.toJson(list));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		// 입력하는 기능
		MemDAO dao = new MemDAO();
		String userId = request.getParameter("u_i");
		String userName = request.getParameter("u_n");
		String address = request.getParameter("u_a");
		String phone = request.getParameter("u_p");
		String gender = request.getParameter("u_g");
		String birth = request.getParameter("u_b");

		MemberVO vo = new MemberVO();
		vo.setUserId(userId);
		vo.setUserName(userName);
		vo.setAddress(address);
		vo.setPhone(phone);
		vo.setGender(gender);
		vo.setBirthDate(birth);

		Gson gson = new GsonBuilder().create(); // json 반환
		JsonObject jsonObj = new JsonObject(); // json 데이터 만들기

		// {"retCode":"OK", "retVal": {vo}}
		// {"retCode":"NG", "retVal": "오류발생!! \n 담당자 문의!"}
		if (dao.insertMember(vo)) {
			jsonObj.addProperty("retCode", "OK");
			jsonObj.addProperty("userId", vo.getUserId());
			jsonObj.addProperty("userName", vo.getUserName());
			jsonObj.addProperty("birthDate", vo.getBirthDate());
			jsonObj.addProperty("address", vo.getAddress());
			jsonObj.addProperty("phone", vo.getPhone());
			jsonObj.addProperty("gender", vo.getGender());

		} else {
			jsonObj.addProperty("retCode", "NG");
			jsonObj.addProperty("retMsg", "�삤瑜섎컻�깮!! \n �떞�떦�옄(000-111) 臾몄쓽!");

		}
		response.getWriter().println(gson.toJson(jsonObj));

	}

}
