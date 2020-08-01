package com.yc.damai.dao;

import java.util.List;

import com.yc.damai.util.DBHelper;



public class AddrDao {
	 //用户地址信息
	 public List<?> addrinfo(String uid) {
		 String sql="SELECT\n" +
				 "  id,\n" +
				 "  uid,\n" +
				 "	addr,\n" +
				 "	phone,\n" +
				 "	NAME,\n" +
				 "createtime,\n" +
				 "	CASE  \n" +
				 "		 	WHEN dft = 1 THEN\n" +
				 "	  '是'\n" +
				 "ELSE\n" +
				 "	 \n" +
				 "		   '否'  \n" +
				 "		 	 \n" +
				 "END dft\n" +
				 "FROM\n" +
				 "	dm_address\n" +
				 "WHERE\n" +
				 "	uid =?";
		 DBHelper dbh=new DBHelper(); 
		return dbh.query(sql,uid); 
	 }
	 //通过id查用户地址信息
	 public List<?> addrinfo1(String uid) {
		 String sql="SELECT\n" +
				 "  id,\n" +
				 "  uid,\n" +
				 "	addr,\n" +
				 "	phone,\n" +
				 "	NAME,\n" +
				 "createtime,\n" +
				 "	CASE  \n" +
				 "		 	WHEN dft = 1 THEN\n" +
				 "	  '是'\n" +
				 "ELSE\n" +
				 "	 \n" +
				 "		   '否'  \n" +
				 "		 	 \n" +
				 "END dft\n" +
				 "FROM\n" +
				 "	dm_address\n" +
				 "WHERE\n" +
				 "	id =?";
		 DBHelper dbh=new DBHelper(); 
		return dbh.query(sql,uid); 
	 }
	 //通过id查用户地址信息
	 public int modifyinfo1(String addr,String phone,String name,String dft,String id) {
		 String sql="update dm_address set addr=?,phone=?,name=?,dft=? where id=?";
		 DBHelper dbh=new DBHelper(); 
		return dbh.update(sql,addr,phone,name,dft,id); 
	 }
	 //添加地址
	 public int add(String uid,String addr,String phone,String name,String dft) {
		 String sql="insert into dm_address(uid,addr,phone,name,dft) "
		 		+ "value(?,?,?,?,?);";
		 DBHelper dbh=new DBHelper(); 
		return dbh.update(sql,uid,addr,phone,name,dft); 
	 }
	//删除地址
		 public int del(String id) {
			 String sql="delete from dm_address where id=?";
			 DBHelper dbh=new DBHelper(); 
			return dbh.update(sql,id); 
		 }
	//改变默认地址
		 public int modifydft(String uid) {
			 String sql="update dm_address set dft=0 where uid=?";
			 DBHelper dbh=new DBHelper(); 
			return dbh.update(sql,uid); 
		 } 
	 
}
