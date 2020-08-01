package com.yc.damai.util;


import java.util.Properties;

public class MyProperties extends Properties{
	//单列模式
	private static MyProperties instance =new MyProperties();
	
	//构造函数私有化
	private MyProperties(){
		try {//用于读取指定资源的输入流
			this.load(MyProperties.class.getClassLoader().getResourceAsStream("db.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static MyProperties getInstance() {
		return instance;
	}
}