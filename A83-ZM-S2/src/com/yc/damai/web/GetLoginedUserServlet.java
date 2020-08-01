package com.yc.damai.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/GetLoginedUserServlet.do")
public class GetLoginedUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 从会话中获取登录的用户名
		@SuppressWarnings("unchecked")
		Map<String,Object> user = 
				(Map<String, Object>) request.getSession().getAttribute("loginedUser");
		// 使用 Gson 将数据 以 json 格式方式返回给页面
		Gson gson = new Gson();
		// 将集合转成 json 格式的字符串
		String json = gson.toJson(user);
		response.getWriter().print(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}