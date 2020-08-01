package com.yc.damai.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.yc.damai.dao.UserDao3;
import com.yc.damai.util.VerifyCodeUtils;
import com.yc.damai.web.BaseServlet;






@WebServlet("/DUsers.do")
public class UserServlet11 extends BaseServlet {
	private static final long serialVersionUID = 1L;
    
      private UserDao3 userdao=new UserDao3(); 
      
      //用户登录
      
	protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
	   
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String captcha = request.getParameter("captcha");
		  
		System.out.println(username+password);
		
		String scode=(String) request.getSession().getAttribute("vcode");
		Map<String, Object> user = userdao.selectByLogin(username, password);
	//	System.out.println("id====="+user.get("id"));  
//		String uid=String.valueOf(user.get("id"));
//		request.getSession().setAttribute("loginedUserId", uid);

		if (user != null) {
			if(captcha.equals(scode)) {
				request.getSession().setAttribute("loginedUser", user);
				System.out.println("session"+request.getSession().getAttribute("loginedUser"));
				response.getWriter().print("登录成功"); 
			}else {
				if(captcha==null||captcha.isEmpty()==true) {
					response.getWriter().print("请填写验证码 ");

				}else {
					response.getWriter().print("验证码错误");
				}
				
			
			}
		} else {
			response.getWriter().print("用户名或密码错误");
		}
	}
	 
   //用户注册
	protected void reg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
	   
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String repassword =request.getParameter("repassword");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String captcha = request.getParameter("captcha");
		System.out.println("password:"+password );
		System.out.println("repassword:"+repassword);
	   String scode=(String) request.getSession().getAttribute("vcode");
	  //判断用户名是否存在
	   List<?> list=userdao.getUsername(username);
		 if(list.size()==0) {
				 
		
	  if (username!= null&&username.isEmpty()==false&&password!= null&&password.isEmpty()==false&&
			  email!= null&&email.isEmpty()==false&&phone!= null&&phone.isEmpty()==false &&
			  name!= null&&name.isEmpty()==false&&sex!= null&&sex.isEmpty()==false 
			  ) {
		     if(password.equals(repassword)) {
		    	  
		      
			if(captcha.equals(scode)) {
				userdao.insert(username, name, password, email, phone, sex);
				 response.getWriter().print("注册成功"); 
			}else {
				if(captcha==null||captcha.isEmpty()==true) {
					response.getWriter().print("请填写验证码 ");

				}else {
					response.getWriter().print("验证码错误");
				}
				
			
			}
		     }else {
		    	 response.getWriter().print("密码不一致"); 
		     }
		} else {
			response.getWriter().print("信息不能为空");
		}
		 }else {
			 print(response,"用户名已存在");
		 }
	}
	 
	
    //检测用户名是否已存在
	protected void checkUserName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("username");
		List<?> list=userdao.getUsername(username);
		 if(list.size()!=0) {
			 
				print(response,"用户名已存在");
		 }
//		List<?> name=userdao.getUsername();
//	 
//		for(Object n:name) {
//			Map<String,Object> uname=(Map<String, Object>) n;
//			String u=(String) uname.get("ename");
//			if(u.equals(username)) {
//				print(response,"用户名已存在");
//			}
			 
	//	}
		
	}
	//获取当前登录用户信息
		protected void  query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session=request.getSession(); 
		   Map<String,Object> user=(Map<String, Object>) session.getAttribute("loginedUser");
		  String id=String.valueOf(user.get("id")) ;
			List<?> list=userdao.userinfo(id);
            print(response,list);
            System.out.println(list);
  
		}
		//修改用户信息
		protected void  modify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session=request.getSession(); 
		   Map<String,Object> user=(Map<String, Object>) session.getAttribute("loginedUser");
		  String id=String.valueOf(user.get("id")) ;
		 
		  String ename=request.getParameter("ename");
		  String cname=request.getParameter("cname");
		  String password=request.getParameter("password");
		  String email=request.getParameter("email");
		  String phone=request.getParameter("phone");
		  String sex=request.getParameter("sex");
		  int i=userdao.change(ename, cname, password, email, phone, sex, id);
		 System.out.println("i"+i);
		  if(i!=0) {
			   print(response,"修改成功");
		   }
		}
		 //找回密码中检测用户名是否已存在,并发送邮件
		
		protected void reset(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			String repassword=request.getParameter("repassword");
			if(password.equals(repassword)) {
				int i=userdao.changepassword(username, password);
				if(i>0) {
					print(response,"密码重置成功");
				}
			}else {
				print(response,"两次密码不一致");
			}

			
			
		}
		 

	//验证码生成
	protected void Img(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  //获取session必须在输出图片之前
				HttpSession session=request.getSession();
				//向页面输入一张验证码的图片
				String vcode=VerifyCodeUtils.outputImage(response);
				
				//将验证码设置到会话中
				session.setAttribute("vcode", vcode);
			
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		doGet(request, response);
	}

}
