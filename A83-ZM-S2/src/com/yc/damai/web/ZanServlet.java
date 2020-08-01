package com.yc.damai.web;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import redis.clients.jedis.Jedis;

@WebServlet("/zan.do")
public class ZanServlet extends  BaseServlet {
	private static final long serialVersionUID = 1L;
  //获取帖子的点赞数量
	protected void cnt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Jedis jd = new Jedis();
		String  id = request.getParameter("id");
		long cnt =jd.scard("comment-zan:"+id);//获取set元素个数
		jd.close();
		response.getWriter().append("{ \"cnt\":  " + cnt  + "  }");

	}
	protected void top(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/**
		 * 排行榜["63", "9", "22", "26", "41", "7", "1", "", "21", "6"] 根据id查商品
		 */
		Jedis jd = new Jedis();
		Set<String> top = jd.zrange("topic-zset", -10, -1);
		jd.close();
		response.getWriter().append(new Gson().toJson(top));
	}

	//点赞功能:用户(登录之后)在浏览器帖子之后，对
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user = 
				(Map<String, Object>) request.getSession().getAttribute("loginedUser");
		
		// 判断用户对象是否存在, 表示是否登录
		if(user == null) {
			response.getWriter().append("{\"msg\":\"请先登录系统!\"}");
			return;
		}
		
		Object uid=user.get("id");
		Jedis jd =new Jedis();
		String id = request.getParameter("id");
		//sadd 0失败 1成功
		jd.sadd("comment-zan:"+id,uid.toString());
		long cnt =jd.scard("comment-zan:"+id);
		jd.close();
		response.getWriter().append("{ \"cnt\":  " + cnt  + "  }");
	}
	
	//取消功能:用户(登录之后)在浏览器帖子之后，对
	protected void no(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user = 
				(Map<String, Object>) request.getSession().getAttribute("loginedUser");
		
		// 判断用户对象是否存在, 表示是否登录
		if(user == null) {
			response.getWriter().append("{\"msg\":\"请先登录系统!\"}");
			return;
		}
		
		Object uid=user.get("id");
		Jedis jd =new Jedis();
		String id = request.getParameter("id");
		//sadd 0失败 1成功
		jd.srem("comment-zan:"+id,uid.toString());
		long cnt =jd.scard("comment-zan:"+id);
		jd.close();
		response.getWriter().append("{ \"cnt\":  " + cnt  + "  }");
	}

}
