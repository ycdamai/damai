package com.yc.damai.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.damai.po.DmProduct;
import com.yc.damai.util.DBHelper;

public class ProductDao {
	DBHelper db=new	DBHelper();
	/**
	 * 分页查询
	 * @param page 页号
	 * @param size  每页行数
	 * @return
	 * limit 第几行，查几条记录
	 */
	public List<Map<String,Object>> queryPage(String cid,int page,int size){
		int begin=(page-1)*size;
		return db.query("select * from dm_product  where cid =? limit ?,?",cid,begin,size);
	}
	/**
	 * 总页数
	 * @param size
	 * @return
	 */
	public int countPages(String cid,int size) {
		if(db.count("select  *  from  dm_product where cid =?",cid)%size==0) {
			return  db.count("select  *  from  dm_product  where cid =?",cid)/size;
		}
		return db.count("select  *  from  dm_product where cid =?",cid)/size+1;
	}
	
	
	
	
	//后台商品添加
	public void insert(DmProduct dp) {
		String sql = "insert into dm_product values(null,?,?,?,?,?,?,now(),?)";
		new DBHelper().update(sql
				,dp.getPname()
				,dp.getMarketPrice()
				,dp.getShopPrice()
				,dp.getImage()
				,dp.getPdesc()
				,dp.getIsHot()
				,dp.getCid());
	}

	public void update(DmProduct dp) {
		String sql = "update dm_product set "
				+ "pname=?,"
				+ "market_price=?,"
				+ "shop_price=?,"
				+ "image=?,"
				+ "pdesc=?,"
				+ "is_hot = ?,"
				+ "cid = ?"
				+ " where id=?";
		new DBHelper().update(sql
				,dp.getPname()
				,dp.getMarketPrice()
				,dp.getShopPrice()
				,dp.getImage()
				,dp.getPdesc()
				,dp.getIsHot()
				,dp.getCid()
				,dp.getId());
	}
	
	//删除商品
	public void delete(DmProduct dp) {
		String sql="DELETE  from dm_product where id=?";
		new DBHelper().update(sql, dp.getId());
	}
	
	public List<Map<String, Object>> query1(DmProduct dp, String page, String rows) {

		String where = "";
		List<Object> params = new ArrayList<>();
		if (dp.getPname() != null && dp.getPname().trim().isEmpty() == false) {
			where += " and pname like ?";
			params.add("%" + dp.getPname() + "%");
		}

		if (dp.getCid() != null && dp.getCid() != 0) {
			where += " and cid = ?";
			params.add(dp.getCid());
		}

		if (dp.getIsHot() != null &&dp.getIsHot() != 0) {
			where += " and is_hot = ?";
			params.add(dp.getIsHot());
		}

		int ipage = Integer.parseInt(page);
		int irows = Integer.parseInt(rows);
		ipage = (ipage - 1) * 10;
		String sql = "select a.*, b.cname from dm_product a" + " join dm_category b on a.cid = b.id" + " where 1=1"
				+ where + " limit ?, ?";
		params.add(ipage);
		params.add(irows);
		return new DBHelper().query(sql, params.toArray());
	}

	public int count1(DmProduct dp) {
		String where = "";
		List<Object> params = new ArrayList<>();
		if (dp.getPname() != null && dp.getPname().trim().isEmpty() == false) {
			where += " and pname like ?";
			params.add("%" + dp.getPname() + "%");
		}

		if (dp.getCid() != null && dp.getCid() != 0) {
			where += " and cid = ?";
			params.add(dp.getCid());
		}

		if (dp.getIsHot() != null &&dp.getIsHot() !=0) {
			where += " and is_hot = ?";
			params.add(dp.getIsHot());
		}
		String sql = "select null from dm_product where 1=1" + where;
		return new DBHelper().count(sql, params.toArray());   //得到行数
	}

	public List<Map<String, Object>> queryByCid(String cid) {
		String sql = "SELECT * from dm_product where cid=? limit 0,12";
		return new DBHelper().query(sql, cid);
	}

	public List<Map<String, Object>> queryById(String cid) {
		String sql = "SELECT\n" + "	*\n" + "FROM\n" + "	dm_product a\n" + "INNER JOIN dm_category b ON a.cid = b.id\n"
				+ "WHERE\n" + "	cid = 1";
		return new DBHelper().query(sql, cid);
	}

	public List<Map<String, Object>> query(String id) {
		String sql = "select * from dm_product where id=?";
		List<Map<String, Object>> list = new DBHelper().query(sql, id);

		if (list.isEmpty()) {
			return null;
		} else {
			return list;
		}
	}
}
