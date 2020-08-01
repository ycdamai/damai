package com.yc.damai.web;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.google.gson.Gson;
import com.yc.damai.dao.UserDao;

@WebServlet("/emailServlet.do")
public class EmailServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Random random = new Random();
		int vcode = 1000 + random.nextInt(9999);
		// 将验证码保存在会话中
		HttpSession session = request.getSession();
		session.setAttribute("vcode", vcode);

		HtmlEmail email = new HtmlEmail();// 创建对象
		email.setHostName("smtp.qq.com");// 在QQ邮箱设置==>账户==>打开smtp服务
		email.setCharset("utf-8");// 设置字符集
		try {
			String cname = request.getParameter("cname");
			Map<String,Object> map=new UserDao().query(cname);
			
			Gson gson = new Gson();
			System.out.println(map);
			String json = gson.toJson(map);

			System.out.println(json);
			response.getWriter().append(json);	
		
		//   	"{\"msg\":\"购物车添加成功!\"}"
			
			email.addTo((String) map.get("email") );// 收件地址
			email.setFrom("1837278602@qq.com", "水晶发送的邮件");// 此处填邮箱地址和用户名,用户名可以任意填写
			email.setAuthentication("1837278602@qq.com", "jbxpqjujqzfcdfai");
			// 填写发件人邮箱地址和客户端授权码 打开smtp服务可得到客户端授权码

			email.setSubject("验证码获取");// 此处填写邮件名，邮件名可任意填写
			email.setMsg("尊敬的用户您好,您本次注册的验证码是:" + vcode + "请及时填写哦");// 此处填写邮件内容
			email.send();
			response.getWriter().append("验证码已发送");
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
