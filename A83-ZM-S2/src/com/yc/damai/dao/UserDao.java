package com.yc.damai.dao;

import java.util.List;
import java.util.Map;

import com.yc.damai.util.DBHelper;



public class UserDao {
		
	/**
	 * 代码重构: 重新定义, 如果正常登录, 返回用户的信息(Map集合), 否则返回null
	 * @param uname
	 * @param upass
	 * @return
	 */
	public Map<String,Object> selectByLogin(String cname, String pwd) {
		String sql = "select * from dm_user where cname=? and password=?";
		DBHelper dbh = new DBHelper();
		//return dbh.count(sql, uname, upass) > 0;
		List<Map<String,Object>> list = dbh.query(sql, cname, pwd);
		if(list.size()==0) {
			return null;
		} else {
			Map<String,Object> user = list.get(0);
			return user;
		}
	}
	
	public int insert(String ename, String cname, String password, String email,String phone,String sex) {
		String sql = "insert into dm_user VALUES(null,?,?,?,?,?,?,1,NOW())";
		DBHelper dbh = new DBHelper(); // ctrl + 1 错误解决方案提示
		return dbh.update(sql, ename, cname, password, email,phone,sex);
	}
	/*
	 * 修改密码
	 */
	public int reset (String cname, String password) {
		String sql = "update dm_user set password= ? where cname = ?";
		DBHelper dbh = new DBHelper(); // ctrl + 1 错误解决方案提示
		return dbh.update(sql, password, cname);
	}
	/**
	 * 根据用户名获取邮箱
	 */
	public Map<String, Object> query (String cname) {
		String sql = "select email from dm_user where cname=?";
		DBHelper dbh = new DBHelper(); 
		
		List<Map<String,Object>> list = dbh.query(sql, cname);
		if(list.size()==0) {
			return null;
		} else {
			Map<String,Object> user = list.get(0);
			return user;
		}

	}
	
}
