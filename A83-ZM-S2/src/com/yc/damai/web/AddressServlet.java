package com.yc.damai.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.yc.damai.dao.AddressDao;
import com.yc.damai.po.DmAddress;
import com.yc.damai.po.Result;


@WebServlet("/address.do")
public class AddressServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	AddressDao adao=new AddressDao();
	//通过用户查找
		protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			// 从会话中获取 用户map
			@SuppressWarnings("unchecked")
			Map<String,Object> user = 
				(Map<String, Object>) request.getSession().getAttribute("loginedUser");
			String uid = ""+user.get("id"); // 获取用户ID		
//			HttpSession session=request.getSession(); 
//		 	  Map<String,Object> user=(Map<String, Object>) session.getAttribute("loginedUser");
//		 	  String uid=String.valueOf(user.get("id")) ;
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
			int dft= Integer.parseInt(request.getParameter("dft"));
			 if(dft==1) {
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
		    
		    
//	 		String id=request.getParameter("id");
	 		String addr=request.getParameter("addr");
	 		String phone=request.getParameter("phone");
	 		String name=request.getParameter("name");
	 		int dft= Integer.parseInt(request.getParameter("dft"));
	 		 if(dft==1) {
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
	
	
	
		// 添加默认地址--此时无默认地址
		protected void save(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
			DmAddress dp = new DmAddress();
			
			
			// 从会话中获取 用户map
			@SuppressWarnings("unchecked")
			Map<String,Object> user = 
				(Map<String, Object>) request.getSession().getAttribute("loginedUser");
			String uid = ""+user.get("id"); // 获取用户ID
			
			// 装载方法
			BeanUtils.populate(dp, request.getParameterMap());
			// 商品名称验证 空值验证, 长度判断
			if (dp.getAddr() == null || dp.getAddr().trim().isEmpty()) {
				print(response, new Result(0, "收货地址不能为空!"));
				return;
			}
			if (dp.getPhone() == null || dp.getPhone().trim().isEmpty()) {
				print(response, new Result(0, "联系电话不能为空!"));
				return;
			}
			if (dp.getName() == null || dp.getName().trim().isEmpty()) {
				print(response, new Result(0, "收货人不能为空"));
				return;
			}
//			if (dp.getDft() == null || dp.getDft() == false) {
//				adao.insert(dp,uid);
//			} else {
			//	adao.update(dp,uid);
		//	}
			List<?> list=adao.querydft(uid);
			if(list.size()>0) {
				adao.modifydft(uid);
			}
			if(adao.insert(dp,uid)>0) {
				print(response, new Result(1, "默认地址设置成功!"));
			}
		}

}
