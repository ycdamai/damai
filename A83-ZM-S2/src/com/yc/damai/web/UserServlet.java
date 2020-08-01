package com.yc.damai.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.yc.damai.dao.ProductDao;
import com.yc.damai.dao.UserDao;
import com.yc.damai.util.DBHelper;

@WebServlet("/user.do")
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cname = request.getParameter("cname");
		String pwd = request.getParameter("pwd");
		String scode = request.getParameter("scode");// 页面上的验证码
		// 取服务器里的验证码
		String code = (String) request.getSession().getAttribute("code");
		System.out.println(code);

		Map<String, Object> user = new UserDao().selectByLogin(cname, pwd);
		if (user != null && scode != null && scode.trim().isEmpty() == false) {
			// 登录成功之后, 要将用户名保存到会话对象

			if (scode.equalsIgnoreCase(code)) {
				request.getSession().setAttribute("loginedUser", user);
				print(response, "登录成功");
			} else {
				response.getWriter().append("验证码错误！");
			}

		} else {
			print(response, "用户名或密码错误");
		}

	}
	
	protected void reset(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cname = request.getParameter("cname");
		String password = request.getParameter("password");
		String scode = request.getParameter("scode");// 页面上的验证码
		// 取服务器里的验证码
		String code = (String) request.getSession().getAttribute("code");
		System.out.println(code);
		
		int i=new UserDao().reset(cname,password);

		if (scode != null && scode.trim().isEmpty() == false) {
			if (scode !=code) {
				response.getWriter().append("验证码不正确");
			} else if(i==1) {
				response.getWriter().append("重置密码成功");
			}
		} else {
			response.getWriter().append("请输入验证码");
		}
		
	}
	
	
	
	protected void roback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cname = request.getParameter("cname");
		Map<String,Object> map=new UserDao().query(cname);
//		print(response,map);
//		System.out.println(map);
		
		Gson gson = new Gson();
		System.out.println(map);
		String json = gson.toJson(map);

		System.out.println(json);
		response.getWriter().append(json);
	
		
	}
	
	
	

	protected void reg(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ename = request.getParameter("ename");
		String cname = request.getParameter("cname");
		String password = request.getParameter("password");
		String repwd = request.getParameter("reupass");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String sex = request.getParameter("sex");

		String scode = request.getParameter("scode");// 页面上的验证码
		// 取服务器里的验证码
		String code = (String) request.getSession().getAttribute("code");
		System.out.println(code);
	
		int i=0;
		

		if (cname == null || cname.trim().isEmpty()) {
			print(response,"请填写用户名!");
		} else if (password == null || password.trim().isEmpty()) {
			print(response,"请填写密码!");
		} else if (password.equals(repwd) == false) {
			print(response,"两次输入的密码不一致!");
		} else if (email == null || email.trim().isEmpty()) {
			print(response,"请填写E-mail!");
		} else if (phone == null || phone.trim().isEmpty()) {
			print(response,"请填写phone!");
		} else if (scode== null || scode.trim().isEmpty() ) {
			print(response,"请填写验证码!");
		} else if (scode.equalsIgnoreCase(code)== false) {
			print(response,"验证码不正确!");
		}else {
			i=new UserDao().insert(ename, cname, password, email, phone, sex);
		}
		
		if(i==0) {
			print(response,"用户注册失败");
		}else {
			print(response,"用户注册成功");
		}


	
	}
}
