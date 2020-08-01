package com.yc.damai.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yc.damai.dao.Per_OrderDao;




 
@WebServlet("/Per_OrderServlet.do")
public class Per_OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	 private Per_OrderDao podao=new Per_OrderDao();
	// 查询订单
	 	protected void query(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	 		HttpSession session=request.getSession(); 
	 	 	  Map<String,Object> user=(Map<String, Object>) session.getAttribute("loginedUser");
	 	 	  String uid=String.valueOf(user.get("id")) ;
	 	 	 HashMap<String,Object> ret=new HashMap();
	 		// List<?> orders = podao.query(uid);
	 		List<?> orderitem = podao.queryitem(uid); 
	 		 
      	// ret.put("orders", orders);
     ret.put("orderitem", orderitem);
	 		 
	 		print(response, ret);
	 	}
}
