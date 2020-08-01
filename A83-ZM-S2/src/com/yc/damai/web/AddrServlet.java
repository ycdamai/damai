package com.yc.damai.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yc.damai.dao.AddrDao;


 
@WebServlet("/addr.do")
public class AddrServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	AddrDao adao=new AddrDao();
	//通过用户查找
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(); 
	 	  Map<String,Object> user=(Map<String, Object>) session.getAttribute("loginedUser");
	 	  String uid=String.valueOf(user.get("id")) ;
        List<?> list =adao.addrinfo(uid);
		print(response, list);


	}
	//查找指定id的地址
	protected void query1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   String id=request.getParameter("id");
        List<?> list =adao.addrinfo1(id);
		print(response, list);


	}
	//修改地址
	protected void update1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(); 
	    Map<String,Object> user=(Map<String, Object>) session.getAttribute("loginedUser");
	    String uid=String.valueOf(user.get("id")) ;
		String id=request.getParameter("id");
		String addr=request.getParameter("addr");
		String phone=request.getParameter("phone");
		String name=request.getParameter("name");
		String dft1= request.getParameter("dft") ;
		String dft;
		if(dft1.equals("是" )) {
			  dft="1";
		}else {
			dft="0";
		}
		 if(dft=="1") {
 			 adao.modifydft(uid);
 		 }
      int i=adao.modifyinfo1(addr, phone, name, dft, id);
       if(i!=0) {
    	 System.out.println(i);
    	 print(response, "修改成功");
     }
	}
   //添加新地址
 	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		HttpSession session=request.getSession(); 
	    Map<String,Object> user=(Map<String, Object>) session.getAttribute("loginedUser");
	    String uid=String.valueOf(user.get("id")) ;
 		String id=request.getParameter("id");
 		String addr=request.getParameter("addr");
 		String phone=request.getParameter("phone");
 		String name=request.getParameter("name");
 		String dft1= request.getParameter("dft") ;
		String dft;
		if(dft1.equals("是" )) {
			  dft="1";
		}else {
			dft="0";
		}
		 if(dft=="1") {
 			 adao.modifydft(uid);
 		 }
      int i=adao.add(uid, addr, phone, name, dft);
      if(i!=0) {
     	 System.out.println(i);
     	 print(response, "添加成功");
      }
 	}
 	//删除地址
 	protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   String id=request.getParameter("id");
          int i =adao.del(id);
       if(i>0) {
    	   print(response,"删除成功");
       }
		


	}
 	
		  
}
