package com.wondertek.mobilevideo.core.util;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

public class FreemarkerUtilTest  {

	@Test
	public void testSimpleCompile(){
	
		String dir = this.getClass().getResource("/tpl").getFile();
		String templateFileName = "test.tpl";
		String templateFileName2 = "test2.tpl";
		
		Map map = new HashMap();
		map.put("aaa", "param1");
		String result = FreemarkerUtil.simpleCompile(map, dir, templateFileName);
		Assert.assertEquals("----param1---", result);
		
		
		result = FreemarkerUtil.simpleCompile(map, dir, templateFileName2);
		result = FreemarkerUtil.simpleCompile(map, dir, templateFileName);
		
	}
	
}
