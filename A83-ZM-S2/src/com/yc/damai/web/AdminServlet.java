package com.yc.damai.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.damai.dao.AdminDao;

@WebServlet("/admin.do")
public class AdminServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		Map<String, Object> user = new AdminDao().selectByLogin(name, pwd);
		if (user != null) {
			// 登录成功之后, 要将用户名保存到会话对象
			request.getSession().setAttribute("loginedUser", user);
			print(response, "登录成功");
		} else {
			print(response, "登录失败");
		}
		// 获取多选框(checkbox)的值
		String rem = request.getParameter("rem");

		// 判断是否勾选功能
//		if (rem == 1) {// 已勾选功能
//			for (String str : checkbox) {
				// 判断是否勾选记住密码功能
				if (rem.equals("1")) {
					// 组合登录信息
					String loginInfo = name + "+" + pwd;
					// 将登陆信息编码
					loginInfo = URLEncoder.encode(loginInfo, "UTF-8");
					// 创建Cookie
					Cookie userCookie = new Cookie("loginInfo", loginInfo);
					// 设置Cookie存活期限
					userCookie.setMaxAge(30 * 24 * 60 * 60);// 设置存活期一个月
					// 设置所有路径下共享Cookie
					userCookie.setPath("/");
					// 添加Cookie到服务器响应中
					response.addCookie(userCookie);
				}
				// 判断是否勾选自动登录功能
//		        if (str.equals("2")) {
//		        	request.getSession().setAttribute("agent", agent);
//		        }
		//	}
		 else {// 未勾选功能，或取消勾选功能，删除Cookie信息
			String loginInfo = name + "+" + pwd;
			loginInfo = URLEncoder.encode(loginInfo, "UTF-8");
			Cookie userCookie = new Cookie("loginInfo", loginInfo);
			userCookie.setMaxAge(0);// 删除cookie,只需要将失效时间设置为0即可，其他步骤一样
			userCookie.setPath("/");
			response.addCookie(userCookie);
		}
		// 在测试中可能会出现一些问题，清除掉Cookie重新添加，在测试。
	}
}
