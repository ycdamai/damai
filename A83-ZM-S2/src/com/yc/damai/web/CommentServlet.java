package com.yc.damai.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.damai.dao.CommentDao;
import com.yc.damai.dao.UserDao;

@WebServlet("/comment.do")
public class CommentServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	protected void insert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uid=request.getParameter("uid");
		String pid=request.getParameter("pid");
		String image1=request.getParameter("image1");
		String comments=request.getParameter("comments");
		
		int i=new CommentDao().insert(pid, image1, comments,uid);
		
		if(i>0) {
			print(response,"您已成功评论");
		}else {
			print(response,"评论失败");
		}
		
		
		
	}
	
	protected void look(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pid=request.getParameter("pid");
		List<Map<String,Object>> list=new CommentDao().look(pid);
		print(response,list);
		
	}
	
	
	
}
