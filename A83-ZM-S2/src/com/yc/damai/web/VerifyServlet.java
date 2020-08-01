package com.yc.damai.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/verify.do")
public class VerifyServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	

		String vcode = request.getParameter("vcode");// 页面上的验证码
		System.out.println(vcode);
		// 取服务器里的验证码	
		String scode = (String)request.getSession().getAttribute("code");
		System.out.println(scode);
		if (vcode != null && vcode.trim().isEmpty() == false) {
			if (vcode.equalsIgnoreCase(scode)) {
				response.getWriter().append("验证码正确！");
			} else {
				response.getWriter().append("验证码错误！");
			}
		} else {
			response.getWriter().append("请输入验证码！");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
