package com.yc.damai.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.yc.damai.dao.CommentDao;
import com.yc.damai.dao.ProductDao;
import com.yc.damai.po.DmProduct;
import com.yc.damai.po.Result;
import com.yc.damai.util.DBHelper;

import redis.clients.jedis.Jedis;

@WebServlet("/product.do")
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private ProductDao pdao = new ProductDao();
	private CommentDao cdao=new CommentDao();
	// 分页查询
	protected void FYquery(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid=request.getParameter("cid");
		String page = request.getParameter("page");
		int iPage = 1;// 第几页
		if (page != null && page.trim().isEmpty() == false) {
			iPage = Integer.valueOf(page);
		}
		List<Map<String, Object>> list = pdao.queryPage(cid,iPage, 12);//查询页号和每页行数

		int pages = pdao.countPages(cid,12);//总页数
		// 使用？对象保存list和pages ==>
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("list", list);
		data.put("pages", pages);

		Gson gson = new Gson();
		System.out.println(data);
		String json = gson.toJson(data);

		System.out.println(json);
		response.getWriter().append(json);

	}

	protected void query1(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, IllegalAccessException, InvocationTargetException {

		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		/**
		 * dp 要装载的 实体 对象 properties 存放属性值的 map 集合
		 */
		DmProduct dp = new DmProduct();
		// 装载方法
		BeanUtils.populate(dp, request.getParameterMap());

		List<?> list = pdao.query1(dp, page, rows);
		int total = pdao.count1(dp);

		HashMap<String, Object> data = new HashMap<>();
		data.put("rows", list);
		data.put("total", total);
		print(response, data);
	}

	protected void query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sql = "select * from dm_product where is_hot=1 limit 0,10";
		List<?> list = new DBHelper().query(sql);
		HashMap<String, Object> page = new HashMap<>();
		page.put("list", list);
		print(response, page);
	}

	// 查询某件商品
	protected void queryById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");

		List<Map<String, Object>> list = new ProductDao().query(id);
		int num =cdao.num(id);
		
//		Jedis jd = new Jedis();
//		//incr 返回增长的值  原值+1  incr 实现自增
//		long cnt = jd.incr("id:"+id);
//		
//		//将topic保存到zset中生存排行榜
//		jd.zadd("topic-zset",cnt,id);//键  浏览量  值
//		jd.close();
		
//		//将浏览量添加到原帖对象
//		try {
//			list.get(0).put("cnt", cnt);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		HashMap<String, Object> data = new HashMap<>();
		data.put("list", list.get(0));
		data.put("num", num);
		print(response,data);
	}

	protected void queryCate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sql = "SELECT * from dm_category where pid is null";
		List<?> list = new DBHelper().query(sql);
		HashMap<String, Object> page = new HashMap<>();
		page.put("list", list);
		print(response, page);
	}

	protected void query2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sql = "select * from dm_product order by id desc limit 0,10";
		List<?> list = new DBHelper().query(sql);

		HashMap<String, Object> page = new HashMap<>();
		page.put("list", list);
		print(response, page);
	}

	protected void fenlei2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cid = request.getParameter("cid");
		List<Map<String, Object>> list = new ProductDao().queryById(cid);
		HashMap<String, Object> page = new HashMap<>();
		page.put("list", list);
		print(response, page);
	}

	protected void fenlei(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cid = request.getParameter("cid");
		List<Map<String, Object>> list = new ProductDao().queryByCid(cid);
		HashMap<String, Object> page = new HashMap<>();
		page.put("list", list);
		print(response, page);
	}

	protected void category(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sql = "SELECT * from(SELECT\n" + "	a.id csid,\n" + "	a.cname NAME,\n" + "	a.pid cid,\n"
				+ "	b.cname cname\n" + "FROM\n" + "	dm_category a\n" + "JOIN dm_category b ON a.pid = b.id\n"
				+ "WHERE\n" + "a.pid IS NOT NULL\n" + ") a  ORDER BY a.cid";
		List<?> list = new DBHelper().query(sql);
		HashMap<String, Object> page = new HashMap<>();
		page.put("list", list);
		print(response, page);
	}

	protected void del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		DmProduct dp = new DmProduct();
		// 装载方法
		BeanUtils.populate(dp, request.getParameterMap());

		if (dp.getId() == null || dp.getId() == 0) {
			print(response, new Result(0, "选择商品失败!"));
		} else {
			pdao.delete(dp);
			print(response, new Result(1, "商品删除成功!"));
		}

	}

	// 保存商品
	protected void save(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		DmProduct dp = new DmProduct();
		// 装载方法
		BeanUtils.populate(dp, request.getParameterMap());
		// 商品名称验证 空值验证, 长度判断
		if (dp.getPname() == null || dp.getPname().trim().isEmpty()) {
			print(response, new Result(0, "商品名称不能为空!"));
			return;
		}
		if (dp.getShopPrice() == null || dp.getShopPrice() <= 0) {
			print(response, new Result(0, "商品商城价格必须大于0!"));
			return;
		}
		if (dp.getCid() == null || dp.getCid() <= 0) {
			print(response, new Result(0, "请选择商品的分类!"));
			return;
		}
		if (dp.getId() == null || dp.getId() == 0) {
			pdao.insert(dp);
		} else {
			pdao.update(dp);
		}
		print(response, new Result(1, "商品添加成功!"));
	}

}