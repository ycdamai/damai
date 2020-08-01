package com.yc.damai.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.sql.Blob;
import java.sql.Clob;

public class DbHelper1 {
	// 注册驱动 加载驱动
	static {
		try {
			Class.forName(MyProperties.getInstance().getProperty("driverName"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	// 获取连接对象
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public Connection getConn() throws SQLException {
		//getConnection带两个参，MyProperities自动装载password和user 是connection中的方法
		conn = DriverManager.getConnection(MyProperties.getInstance().getProperty("url"), MyProperties.getInstance());
		return conn;
	}

	/**
	 * 更新操作 单条sql语句
	 * 
	 * @param sql
	 * @param params
	 * @return 参数的顺序必须和？顺序一致
	 * @throws SQLException
	 */

	public int update(String sql, List<Object> params) throws SQLException {
		int result = 0;
		try {
			conn = getConn();
			pstmt = conn.prepareStatement(sql);// 获取预编译的对象
			// 设置参数
			setParams(pstmt, params);
			result = pstmt.executeUpdate();// 执行更新操作
		} finally {
			// 关闭资源
			closeAll(conn, pstmt, null);
		}
		return result;
	}

	/**
	 * 多条sql语句更新 事务
	 * 
	 * @param sqls                           多条sql语句 sql语句必须和list<Object>存储的顺序必须一致
	 * @param params一条sql语句的参数放在LIST<object> 多个 一起存在params
	 * @return
	 * @throws Exception
	 */
	public int update(List<String> sqls, List<List<Object>> params) throws Exception {
		int result = 0;
		try {
			conn = getConn();
			// 默认事务自动提交，但是在此处必须手动提交，所有的sql语句执行成功后才可以提交
			// 事务改为手动提交
			conn.setAutoCommit(false);
			// 循环sql集合
			for (int i = 0; i < sqls.size(); i++) {
				String sql = sqls.get(i);
				System.out.println("SQL: " + sql);
				List<Object> param = params.get(i);// 一一对应
				// 获取预编译对象
				System.out.println("参数: " + param);
				pstmt = conn.prepareStatement(sql);
				// 设置参数
				setParams(pstmt, param);// 小list
				result = pstmt.executeUpdate();
				if (result <= 0) {// 如果其中某个sql语句执行时不成功
					conn.rollback();
					return result;
				}
			}
			// 事务提交
			conn.commit();
		} catch (Exception e) {
			// 发生异常后，事务必须回滚
			conn.rollback();
			result = 0;
			e.printStackTrace();
		} finally {
			// 还原事务的状态
			conn.setAutoCommit(true);
			// 关闭资源
			closeAll(conn, pstmt, null);
		}
		return result;
	}

	/**
	 * count sum min max avg select count 聚合函数 只是用一个聚合函数
	 * 不考虑group by 分组 只返回一列 可以用double存储
	 * @throws SQLException
	 */
	public double getPloymer(String sql, List<Object> params) throws SQLException {
		try {
			conn = getConn();
			pstmt = conn.prepareStatement(sql);
			setParams(pstmt, params);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getDouble(1);//返回的是结果集中的第一列
			}
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return 0;
	}

	/**
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public Map<String, Object> findSingle(String sql, List<Object> params) throws SQLException, IOException {
		Map<String, Object> map = null;
		try {
			conn = getConn();
			pstmt = conn.prepareStatement(sql);// 获取预编译的对象
			// 设置参数
			setParams(pstmt, params);
			rs = pstmt.executeQuery();// 执行查询操作并返回结果集
			List<String> columnNames = getColumnNames(rs);
			if (rs.next()) {
				map = new HashMap<String, Object>();
				// 循环所有的列
				for (String name : columnNames) {
					// 获取对应字段值
					Object obj = rs.getObject(name);
					if (null == obj) {
						continue;// 结束本次循环
					}
					String typeName = obj.getClass().getName();
					// System.out.println(typeName);//number
					if ("java.sql.Blob".equals(typeName)) {
						Blob Blob= (Blob) obj;
						// 图片 放在字节数组中
						InputStream in = Blob.getBinaryStream();
						byte[] bt = new byte[(int) Blob.length()];
						in.read(bt);
						in.close();
						map.put(name, bt);// 图片以字节数组形式存储
					}else if("java.sql.Clob".equals(typeName)) {
						Clob Clob = (Clob) obj;
						StringBuffer sb=new StringBuffer();
						Reader in=Clob.getCharacterStream();
						BufferedReader br=new BufferedReader(in);
						String str=null;
						while((str=br.readLine())!=null) {
							sb.append(str);						
						}
						map.put(name, sb.toString());//文本已字符串进行存储
					}else {
						map.put(name, rs.getObject(name));
					}
				}
				
			}
		} finally {
			// 关闭资源
			closeAll(conn, pstmt, null);
		}
		return map;

	}

	/**
	 * 根据结果集获取所有的列名
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private List<String> getColumnNames(ResultSet rs) throws SQLException {
		List<String> columnNames = new ArrayList<String>();
		// 如何获取sql语句中字段的名称
		ResultSetMetaData data = rs.getMetaData();
		int count = data.getColumnCount();
		// System.out.println("总列数："+count);
		for (int i = 1; i <= count; i++) {// 起始值1开始，最大值=count
			// System.out.println(data.getColumnName(i));
			// 添加到list集合中
			columnNames.add(data.getColumnName(i));
		}
		return columnNames;
	}

	/**
	 * 返回多条记录
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<Map<String, Object>> findMutipl(String sql, List<Object> params) throws SQLException, IOException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		try {
			conn = getConn();
			pstmt = conn.prepareStatement(sql);// 获取预编译的对象
			// 设置参数
			setParams(pstmt, params);
			rs = pstmt.executeQuery();// 执行查询操作并返回结果集
			List<String> columnNames = getColumnNames(rs);
			while (rs.next()) {
				map = new HashMap<String, Object>();
				// 循环所有的列
				for (String name : columnNames) {
					// 获取对应字段值
					Object obj = rs.getObject(name);
					if (null == obj) {
						continue;// 结束本次循环
					}
					String typeName = obj.getClass().getName();
					// System.out.println(typeName);//number
					if ("java.sql.Blob".equals(typeName)) {
						Blob Blob= (Blob) obj;
						// 图片 放在字节数组中
						InputStream in = Blob.getBinaryStream();
						byte[] bt = new byte[(int) Blob.length()];
						in.read(bt);
						in.close();
						map.put(name, bt);// 图片以字节数组形式存储
					}else if("java.sql.Clob".equals(typeName)) {
						Clob Clob = (Clob) obj;
						Reader rd=Clob.getCharacterStream();
						char [] cs =new char[(int) Clob.length()];
						rd.read(cs);
						String string =new String(cs);
						rd.close();
						map.put(name, string);
					}else {
						map.put(name, rs.getObject(name));
					}
				}
				// map添加到List集合
				list.add(map);
			}
		} finally {
			// 关闭资源
			closeAll(conn, pstmt, null);
		}
		return list;

	}

	/**
	 * 
	 * 预编译对象设置参数
	 * 
	 * @param pstmt
	 * @param params
	 * @throws SQLException
	 */
	private void setParams(PreparedStatement pstmt, List<Object> params) throws SQLException {
		if (null == params || params.size() <= 0) {
			return;
		}
		for (int i = 0; i < params.size(); i++) {
			pstmt.setObject(i + 1, params.get(i));// 暂位符从1开始
		}
	}

	// 关闭资源
	public void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
			}
			if (null != pstmt) {

				pstmt.close();
			}
			if (null != conn) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
