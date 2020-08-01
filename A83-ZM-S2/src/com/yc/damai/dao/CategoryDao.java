package com.yc.damai.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.damai.po.DmCategory;
import com.yc.damai.po.DmProduct;
import com.yc.damai.util.DBHelper;

public class CategoryDao {
	
	public List<Map<String, Object>> query1(String pid, String page, String rows) {

		String where = "";
		List<Object> params = new ArrayList<>();
		if (pid != null &&pid.trim().isEmpty() == false) {
			where += " and pid= ?";
			params.add( pid);
		}

		int ipage = Integer.parseInt(page);
		int irows = Integer.parseInt(rows);
		ipage = (ipage - 1) * 10;
		String sql = "select * from(\n" +
				"		SELECT a.id id,a.cname cname,	CASE\n" +
				"WHEN a.pid is null THEN\n" +
				"	'一级目录'\n" +
				"ELSE\n" +
				"	'二级目录'\n" +
				"END cc,b.cname bname,a.pid pid \n" +
				"		FROM\n" +
				"			dm_category a\n" +
				"		LEFT JOIN dm_category b ON a.pid = b.id)a " + " where 1=1"
				+ where + " limit ?, ?";
		params.add(ipage);
		params.add(irows);
		return new DBHelper().query(sql, params.toArray());
	}

	public int count1(String pid) {
		String where = "";
		List<Object> params = new ArrayList<>();
		if (pid != null &&pid.trim().isEmpty() == false) {
			where += " and pid= ?";
			params.add( pid );
		}
		String sql ="select null from(\n" +
				"		SELECT a.id id,a.cname cname,a.pid pid,b.cname bname\n" +
				"		FROM\n" +
				"			dm_category a\n" +
				"		LEFT JOIN dm_category b ON a.pid = b.id)a  " +"where 1=1" + where;
		return new DBHelper().count(sql, params.toArray());   //得到行数
	}

	
	// 查询一级菜单
	public List<Map<String,Object>> query() {
		return new DBHelper().query("select * from dm_category where pid is null");
	}
	
	//查询一级二级菜单
	public List<Map<String,Object>> query2(int pid) {
		
		String sql="SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	(\n" +
				"		SELECT\n" +
				"			a.id secid,\n" +
				"			a.cname seccname,\n" +
				"			b.id fircid,\n" +
				"			b.cname fircname\n" +
				"		FROM\n" +
				"			dm_category a\n" +
				"		INNER JOIN dm_category b ON a.pid = b.id\n" +
				"		WHERE\n" +
				"			a.pid =?\n" +
				"	) a";
		return new DBHelper().query(sql,pid);
	}
	// 查询
		public List<?> query1() {
			return new DBHelper().query("SELECT\n" +
					"	a.id id,\n" +
					"	a.cname cname,\n" +
					"	b.num num\n" +
					"FROM\n" +
					"	(\n" +
					"		SELECT\n" +
					"			*\n" +
					"		FROM\n" +
					"			dm_category\n" +
					"		WHERE\n" +
					"			pid IS NULL\n" +
					"	) a\n" +
					"LEFT JOIN (\n" +
					"	SELECT\n" +
					"		count(*) num,\n" +
					"		pid\n" +
					"	FROM\n" +
					"		dm_category\n" +
					"	GROUP BY\n" +
					"		pid\n" +
					"	HAVING\n" +
					"		pid IS NOT NULL\n" +
					") b ON a.id = b.pid");
		}
	// 修改
	public int update(String cname, String id) {
		String sql = "update dm_category set cname =?  where id=?  ";
		return new DBHelper().update(sql,cname,id);
	}

	// 删除
	public int delete(String id) {
		String sql = "delete from dm_category where id=?  ";
		return new DBHelper().update(sql, id);
	}

	// 增加
	public int add(String cname) {
		String sql = "insert into dm_category VALUES(null,?,null)  ";
		return new DBHelper().update(sql, cname);
	}
}
