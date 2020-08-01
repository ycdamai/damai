package com.yc.damai.util;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class DBTest {
	@Test
	public void test() throws Exception, IOException {
		DbHelper1 dbh = new DbHelper1();
		List<Map<String,Object>> list = dbh.findMutipl("select * from dm_cart",null);
		for(Map<String,Object> row:list) {
			System.out.println(row);
		}
		Assert.assertTrue(list.size()>0);
	}
}
