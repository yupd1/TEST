package com.wondertek.core.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import com.wondertek.mobilevideo.core.util.XstreamUtil;

public class XstreamUtilTest {

	@Test
	public void testObject2xml() {
		String str = "abcdef";
		String xmlStr = XstreamUtil.object2xml(str);
		assertNotNull(xmlStr);
		
		ArrayList<String> ls = new ArrayList<String>();
		ls.add("abc");
		ls.add("bcd");
		String xmlLs = XstreamUtil.object2xml(ls);
		assertNotNull(xmlLs);
		
		Object ls2 = XstreamUtil.xml2Object(xmlLs);
		Object str2 = XstreamUtil.xml2Object(xmlStr);
		
		assertTrue(ls2 instanceof ArrayList);
		assertTrue(str2 instanceof String);
		
		
		HashMap<String, String> map = new HashMap<String,String>();
		String xmlMap = XstreamUtil.object2xml(map);
		assertNotNull(xmlStr);
		
		Object map2 = XstreamUtil.xml2Object(xmlMap);
		assertTrue(map2 instanceof HashMap);
		
	}

}
