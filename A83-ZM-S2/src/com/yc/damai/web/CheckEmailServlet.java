package com.yc.damai.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.damai.dao.UserDao;



@WebServlet("/checkEmail.do")
public class CheckEmailServlet extends HttpServlet {
	UserDao dao=new UserDao();
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String scode = request.getParameter("scode");
		String  cname= request.getParameter("cname");
		String  pwd= request.getParameter("pwd");
		int scode1 = Integer.parseInt(scode);

		Integer vcode = (Integer) request.getSession().getAttribute("vcode");
		int vcode1 = vcode;

		if (scode != null && scode.trim().isEmpty() == false) {
			if (vcode1 == scode1) {
				response.getWriter().append("验证码正确");
				
				int i= dao.reset(cname,pwd);
				if(i>0 &&pwd != null && pwd.trim().isEmpty() == false) {
					response.getWriter().append("重置密码成功");
				}else {
					response.getWriter().append("重置失败");
				}
				
				
				
			} else {
				response.getWriter().append("验证码不正确");
			}
		} else {
			response.getWriter().append("请输入验证码");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
