package com.yc.damai.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.damai.util.DBHelper;
import com.yc.damai.util.DbHelper1;

public class OrdersDao {
	// 修改订单状态为1
	public int update1(String id) {
		String sql = "update dm_orders set state =1  where id=?  ";
		return new DBHelper().update(sql, id);
	}

	// 修改订单状态为2
	public int update2(String id) {
		String sql = "update dm_orders set state =2  where id=? and state=1";
		return new DBHelper().update(sql, id);
	}

	// 修改订单状态为3
	public int update3(String id) {
		String sql = "update dm_orders set state =3  where id=? and state=2";
		return new DBHelper().update(sql, id);
	}
	
	// 查询订单的状态
	public List<?> queryStates() {
		String sql = "SELECT  id,\n" + "	CASE\n" + "WHEN state = 0 THEN\n" + "	'未支付'\n" + "WHEN state = 1 THEN\n"
				+ "	'已支付'\n" + "WHEN state = 2 THEN\n" + "	'已发货'\n" + "WHEN state = 3 THEN\n" + "	'已收货'\n"
				+ "WHEN state = 4 THEN\n" + "	'已评价'\n" + "ELSE\n" + "	'已退货'\n" + "END state\n" + "FROM\n"
				+ "	dm_orders";
		return new DBHelper().query(sql);
	}

	// 查询所有订单
	public List<?> queryAllOrders() {
		String sql = "SELECT\n" + "	a.id,\n" + "	a.total,\n" + "	a.createtime,\n" + "	a.uid,\n" + "	b.addr,\n"
				+ "	b.phone,\n" + "	b.name,\n" + "	CASE\n" + "WHEN a.state = 0 THEN\n" + "	'未支付'\n"
				+ "WHEN a.state = 1 THEN\n" + "	'待发货'\n" + "WHEN a.state = 2 THEN\n" + "	'已发货'\n"
				+ "WHEN a.state = 3 THEN\n" + "	'已收货'\n" + "WHEN a.state = 4 THEN\n" + "	'已评价'\n" + "ELSE\n"
				+ "	'已退货'\n" + "END state\n" + "FROM\n" + "	dm_orders a\n" + "JOIN dm_address b ON a.aid = b.id";
		return new DBHelper().query(sql);
	}

	/**
	 * 事务绑定 提交订单
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public int payOrders(String uid) throws Exception {
		String sql = "INSERT into dm_orders SELECT\n" + "	NULL,\n" + "	c.total,\n" + "	NOW(),\n" + "	0,\n"
				+ "	a.id,\n" + "	b.id\n" + "FROM\n" + "	dm_user a\n" + "JOIN dm_address b ON a.id = b.uid\n"
				+ "AND dft = 1\n" + "JOIN (\n" + "	SELECT\n" + "		SUM(b.shop_price * a.count) total,\n"
				+ "		a.uid\n" + "	FROM\n" + "		dm_cart a\n" + "	JOIN dm_product b ON a.pid = b.id\n"
				+ "	WHERE\n" + "		a.uid =?\n" + "	GROUP BY\n" + "		a.uid\n" + ") c ON a.id = c.uid\n"
				+ "WHERE\n" + "	a.id =? ";
		String sql02 = "INSERT INTO dm_orderitem SELECT\n" + "	-- LAST_INSERT_ID() 获取最近产生的主键列 （自增列）\n" + "	NULL,\n"
				+ "	a.count,\n" + "	a.count * b.shop_price,\n" + "	a.pid,\n"
				+ "	(SELECT max(id) FROM dm_orders) oid\n" + "FROM\n" + "	dm_cart a\n"
				+ "JOIN dm_product b ON a.pid = b.id\n" + "WHERE\n" + "	uid = ?";
		String sql03 = "delete from dm_cart where uid =?";
		List<String> sqls = new ArrayList<String>();
		List<List<Object>> params = new ArrayList<List<Object>>();

		List<Object> param01 = new ArrayList<Object>();
		param01.add(uid);
		param01.add(uid);
		sqls.add(sql);
		params.add(param01);

		List<Object> param02 = new ArrayList<Object>();
		param02.add(uid);
		sqls.add(sql02);
		params.add(param02);

		List<Object> param03 = new ArrayList<Object>();
		param03.add(uid);
		sqls.add(sql03);
		params.add(param03);
		return new DbHelper1().update(sqls, params);
	}

	// 添加订单主表记录 orders
	public int insert(String uid) {
		String sql = "INSERT into dm_orders SELECT\n" + "	NULL,\n" + "	c.total,\n" + "	NOW(),\n" + "	0,\n"
				+ "	a.id,\n" + "	b.id\n" + "FROM\n" + "	dm_user a\n" + "JOIN dm_address b ON a.id = b.uid\n"
				+ "AND dft = 1\n" + "JOIN (\n" + "	SELECT\n" + "		SUM(b.shop_price * a.count) total,\n"
				+ "		a.uid\n" + "	FROM\n" + "		dm_cart a\n" + "	JOIN dm_product b ON a.pid = b.id\n"
				+ "	WHERE\n" + "		a.uid =?\n" + "	GROUP BY\n" + "		a.uid\n" + ") c ON a.id = c.uid\n"
				+ "WHERE\n" + "	a.id =? ";
		return new DBHelper().update(sql, uid, uid);
	}

//查询订单列表
	public List<Map<String, Object>> queryOrders(String uid) {
		String sql = "SELECT\n" + "	*\n" + "FROM\n" + "	dm_orders a\n" + "JOIN dm_orderitem b ON a.id = b.oid\n"
				+ "JOIN dm_product c ON c.id = b.pid\n" + "WHERE\n" + "	a.uid = ?";
		List<Map<String, Object>> list = new DBHelper().query(sql, uid);
		if (list.isEmpty()) {
			return null;
		} else {
			return list;
		}
	}

	//查询订单列表
		public List<Map<String, Object>> queryOrdersState(String uid,String state) {
		
			
			String sql = "SELECT\n" + "	*\n" + "FROM\n" + "	dm_orders a\n" + "JOIN dm_orderitem b ON a.id = b.oid\n"
					+ "JOIN dm_product c ON c.id = b.pid\n" + "WHERE\n" + "	a.uid = ? and a.state = ? ";
			List<Map<String, Object>> list = new DBHelper().query(sql,uid,state);
			if (list.isEmpty()) {
				return null;
			} else {
				return list;
			}
		}
	
	
	
	
	
	public Map<String, Object> queryNewOrders(String uid) {
		String sql = "select a.*,b.addr, b.phone, b.name from dm_orders a join dm_address b on a.aid=b.id"
				+ " where a.uid=? and state=0 order by id desc limit 0,1";
		List<Map<String, Object>> list = new DBHelper().query(sql, uid);
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	public Map<String, Object> queryByoid(String uid, String oid) {
		String sql = "SELECT\n" + "	a.*, b.addr,\n" + "	b.phone,\n" + "	b. NAME\n" + "FROM\n" + "	dm_orders a\n"
				+ "JOIN dm_address b ON a.aid = b.id\n" + "WHERE\n" + "	a.uid = ?\n" + "AND state = 0\n"
				+ "AND a.id = ?";
		List<Map<String, Object>> list = new DBHelper().query(sql, uid, oid);
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	public static void main(String[] args) {
		// 这种写法有数据库事务的问题
		int i = new OrdersDao().insert("1");
		// 出现异常, 会导致 订单被创建, 而订单明细没有创建, 购物车没有被清空
		int j = new OrderitemDao().insert("1");
		int k = new CartDao().deleteAllByUid("1");
		System.out.println(i + "" + j + "" + k);
	}
}
