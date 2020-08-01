package com.yc.damai.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.yc.damai.dao.CategoryDao;
import com.yc.damai.po.DmProduct;
import com.yc.damai.po.Result;

@WebServlet("/category.do")
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private CategoryDao cdao = new CategoryDao();
	
	protected void queryCC(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, IllegalAccessException, InvocationTargetException {

		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		/**
		 * dp 要装载的 实体 对象 properties 存放属性值的 map 集合
		 */
	String pid=request.getParameter("pid");

		List<?> list = cdao.query1(pid, page, rows);
		int total = cdao.count1(pid);

		HashMap<String, Object> data = new HashMap<>();
		data.put("rows", list);
		data.put("total", total);
		print(response, data);
	}
	
	/**
	 * 查询二级分类
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryBySecondCat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Map<String,Object>> firstList = cdao.query();
		Map<String,Object> map;
		List<Map<String,Object>> res=new ArrayList<Map<String,Object>>();
		int fid=1;
		for(Map<String,Object> f1:firstList) {
			fid=(int)f1.get("id");
			List<Map<String,Object>> secondList =  cdao.query2(fid);
			map=new HashMap<String,Object>();
			map.put("firstCat",f1);
			map.put("secondCat",secondList);
			res.add(map);
		}
		print(response, res);
	}
	
	
	
//查询
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<?> list = cdao.query();
		print(response, list);
	}
	
	//查询
		protected void query1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			List<?> list = cdao.query1();
			print(response, list);
		}
//增加
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cname=request.getParameter("cname");	
	
		if(cdao.add(cname)>0) {
			print(response, new Result(1, "增加成功!"));
		}else {
			print(response, new Result(0, "增加失败!"));
		}
	}
	//删除
		protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String id=request.getParameter("id");	
			if(cdao.delete(id)>0) {
				print(response, new Result(1, "删除成功!"));
			}else {
				print(response, new Result(0, "删除失败!"));
			}
		}
	
		//保存
		protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String id=request.getParameter("id");	
			String cname=request.getParameter("cname");
		
			if (id == null || id.trim().isEmpty() ) {
				cdao.add(cname);
			} else {
				cdao.update(cname,id);
			}
			print(response, new Result(1, "商品添加成功!"));

		}
		
		//修改
		protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String id=request.getParameter("id");	
			String cname=request.getParameter("cname");	
			if(cdao.update(cname,id)>0) {
				print(response, new Result(1, "修改成功!"));
			}else {
				print(response, new Result(0, "修改失败!"));
			}
		}
}