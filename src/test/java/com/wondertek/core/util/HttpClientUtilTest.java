package com.wondertek.core.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.wondertek.mobilevideo.core.util.HttpClientUtil;

public class HttpClientUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

//	@Test
//	public void testRequestGet() {
//		String resp = HttpClientUtil.requestGet("http://192.168.1.197:8081/cas/login");
//		assertNotNull(resp);
//		assertTrue(resp.length()>0);
//	}

	@Test
	public void testBatchRequestGet() {
		
		
		for(int i=0;i<10000;i++){
			new TestThread().run();
		}
		System.out.println(1);
		//assertNotNull(resp);
//		assertTrue(resp.length()>0);
	}
	
	class TestThread extends Thread{
		
		public void run(){
			HttpClientUtil.requestGet("http://localhost");
		}
	}
}
