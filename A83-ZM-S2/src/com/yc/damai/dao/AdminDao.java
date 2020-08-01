package com.yc.damai.dao;

import java.util.List;
import java.util.Map;

import com.yc.damai.util.DBHelper;

public class AdminDao {
	/**
	 * 代码重构: 重新定义, 如果正常登录, 返回用户的信息(Map集合), 否则返回null
	 * @param uname
	 * @param upass
	 * @return
	 */
	public Map<String,Object> selectByLogin(String username, String password) {
		String sql = "SELECT * FROM dm_adminuser WHERE username=? and `password`=?";
		DBHelper dbh = new DBHelper();
		//return dbh.count(sql, uname, upass) > 0;
		List<Map<String,Object>> list = dbh.query(sql, username, password);
		if(list.size()==0) {
			return null;
		} else 	{
			Map<String,Object> user = list.get(0);
			return user;
		}
	}
}
