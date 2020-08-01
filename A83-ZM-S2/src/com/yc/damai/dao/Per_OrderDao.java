package com.yc.damai.dao;

import java.util.List;
import java.util.Map;

import com.yc.damai.util.DBHelper;




public class Per_OrderDao {
	//个人订单
//	 public List<?> query(String uid) {
//  		String sql ="SELECT\n" +
//  				"	createtime,\n" +
//  				"	NAME,\n" +
//  				"	addr,\n" +
//  				"	total\n" +
//  				"FROM\n" +
//  				"	backorder where uid=?";
//  		 return  new DBHelper().query(sql, uid);
//	 }
	//个人订单详情
		 public List<?> queryitem(String uid) {
	  		String sql ="SELECT\n" +
	  				" \n" +
	  				"c.pname,\n" +
	  				"	c.image,\n" +
	  				"	 a.total,\n" +  
	  				"	b.createtime,\n" +
	  				"  count \n" +
	  				"FROM\n" +
	  				"	dm_orderitem a,\n" +
	  				"	dm_orders b,\n" +
	  				"	dm_product c\n" +
	  				"WHERE\n" +
	  				"	b.id = a.oid\n" +
	  				"AND pid = c.id\n" +
	  				"AND uid = ?"
	  				+ " ORDER BY  b.createtime DESC LIMIT 8;";
	  		 return  new DBHelper().query(sql, uid);
		 }
}
