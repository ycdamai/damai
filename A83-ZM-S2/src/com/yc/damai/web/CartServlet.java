package com.yc.damai.web;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.damai.dao.CartDao;

@WebServlet("/cart.do")
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private CartDao cdao = new CartDao();
	// cart.do?op=add&pid=???
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 从会话中获取 用户map
		@SuppressWarnings("unchecked")
		Map<String,Object> user = 
			(Map<String, Object>) request.getSession().getAttribute("loginedUser");
		
		// 判断用户对象是否存在, 表示是否登录
		if(user == null) {
			response.getWriter().append("{\"msg\":\"请先登录系统!\"}");
		//	response.getWriter().print("请先登录系统");
			return;
		}
		
		String uid = ""+user.get("id"); // 获取用户ID
		String pid = request.getParameter("pid");
		if( cdao.update(uid, pid) == 0) {
			// 结果为0 ,说明该用户还没有添加过改商品
			cdao.insert(uid, pid);
		}
		response.getWriter().append("{\"msg\":\"购物车添加成功!\"}");
	}

	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 从会话中获取 用户map
		@SuppressWarnings("unchecked")
		Map<String,Object> user = 
			(Map<String, Object>) request.getSession().getAttribute("loginedUser");
		
		// 判断用户对象是否存在, 表示是否登录
		if(user == null) {
			response.getWriter().print("请先登录系统");
			return;
		}	
		String uid = ""+user.get("id"); // 获取用户ID
		
		List<?> list = cdao.queryByUid(uid);
		print(response, list);
	}
    
	protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		int i= cdao.deleteById(id);
		if(i==0) {
			response.getWriter().append("{\"msg\":\"删除商品失败!\"}");
		}
		response.getWriter().append("{\"msg\":\"删除成功!\"}");
	}

	protected void delAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 从会话中获取 用户map
		@SuppressWarnings("unchecked")
		Map<String,Object> user = 
			(Map<String, Object>) request.getSession().getAttribute("loginedUser");
		
		// 判断用户对象是否存在, 表示是否登录
		if(user == null) {
			response.getWriter().append("{\"msg\":\"请先登录系统!\"}");
		//	response.getWriter().print("请先登录系统");
			return;
		}
		
		String uid = ""+user.get("id"); // 获取用户ID
		
		int i= cdao.deleteAllByUid(uid);
		if(i==0) {
			response.getWriter().append("{\"msg\":\"删除商品失败!\"}");
		}
		response.getWriter().append("{\"msg\":\"删除成功!\"}");
	}
}
