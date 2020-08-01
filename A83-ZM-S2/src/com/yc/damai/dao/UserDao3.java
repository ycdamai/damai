package com.yc.damai.dao;

import java.util.List;
import java.util.Map;

import com.yc.damai.util.DBHelper;







public class UserDao3 {
	//注册
	public void insert(String username,String name,String password ,String email,String phone,String sex) {
		 String sql="insert into dm_user values(null,?,?,?,?,?,?,1,now())";
		 DBHelper dbh=new DBHelper(); 
		 dbh.update(sql, username,name,password,email,phone,sex); 
	 }
	 
	 //用户登录
	 public Map<String,Object> selectByLogin(String username,String password) {
		 String sql="select * from dm_user where cname=? and password=?";
		 DBHelper dbh=new DBHelper();
		 //return dbh.count(sql, uname,upass)>0;
	     List<Map<String,Object>> list=dbh.query(sql, username,password);
	     if(list.size()==0) {
	    	 return null;
	     }else {
	    	 Map<String,Object> user=list.get(0);
	    	 return user;
	     }
	 }
	 
	 public void getId(String username) {
		 String sql="select id from dm_user where ename=?";
		 DBHelper dbh=new DBHelper(); 
		 dbh.query(sql, username); 
	 }
	 //用户名是否存在
	 public List<?> getUsername(String username) {
		 String sql="select * from dm_user where ename=?";
		 DBHelper dbh=new DBHelper(); 
		return dbh.query(sql,username); 
	 }
	 //用户信息
	 public List<?> userinfo(String id) {
		 String sql="select * from dm_user where id=?";
		 DBHelper dbh=new DBHelper(); 
		return dbh.query(sql,id); 
	 }
	//用户信息修改
		 public int change(String ename,String cname,String password,String email,String phone,String sex,String id) {
			 String sql="update dm_user set ename=?,cname=?,password=?,email=?,phone=?,sex=? where id=?";
			 DBHelper dbh=new DBHelper(); 
			return dbh.update(sql,ename,cname,password,email,phone,sex,id); 
		 }
		//密码找回修改
		 public int changepassword(String ename,String password) {
			 String sql="update dm_user set password=? where ename=?";
			 DBHelper dbh=new DBHelper(); 
			return dbh.update(sql,password,ename); 
		 }
}
