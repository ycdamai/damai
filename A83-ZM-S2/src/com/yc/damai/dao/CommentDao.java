package com.yc.damai.dao;

import java.util.List;
import java.util.Map;

import com.yc.damai.util.DBHelper;

public class CommentDao {
	public int insert(String pid, String image1, String comments, String uid) {
		String sql = "INSERT into dm_comment VALUES(null,?,?,null,?,NOW(),?)";
		DBHelper dbh = new DBHelper(); // ctrl + 1 错误解决方案提示
		return dbh.update(sql, pid, image1, comments, uid);
	}
	
	//查询商品的评论数
	 public  int num(String pid) {
		 String sql="select * from dm_comment where  pid=?";
		 DBHelper dbh=new DBHelper(); 
		return dbh.count(sql,pid); 
	 }
	 
	 //查看评论
	 public List<Map<String,Object>> look(String pid){
		 String sql="SELECT\n" +
				 "	a.*, CONCAT(\n" +
				 "		SUBSTRING(b.cname, 1, 1),\n" +
				 "		'****'\n" +
				 "	) NAME\n" +
				 "FROM\n" +
				 "	dm_comment a\n" +
				 "LEFT JOIN dm_user b ON a.uid = b.id\n" +
				 "WHERE\n" +
				 "	pid = ?  order by a.createtime desc";
		 DBHelper dbh=new DBHelper(); 
			return dbh.query(sql,pid);  
	 }
}
