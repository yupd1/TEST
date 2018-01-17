package com.wondertek.mobilevideo.core.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;


/**
 * 发送http请求的工具类, 配置连接池能够重用已有连接进行循环发送
 * 
 * @author Jimmy Deng
 */
public class HttpSenderUtil {
	
	public static Logger LOG = Logger.getLogger(HttpSenderUtil.class);
	private static MultiThreadedHttpConnectionManager connectionManager;
	private static HttpClient client;
	/**
	 * maximum number of connections allowed per host
	 */
	private static int maxHostConnections = 100;//TODO:数值待定
	
	/**
	 * maximum number of connections allowed overall
	 */
	private static int maxTotalConnections = 100;//TODO:数值待定
	
	/**
	 * the timeout until a connection is etablished
	 */
	private static int connectionTimeOut = 5000;//TODO:数值待定
	
	private static int socketTimeout = 5000;//TODO:数值待定
	
	static {
		connectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		params.setDefaultMaxConnectionsPerHost(maxHostConnections);
		params.setMaxTotalConnections(maxTotalConnections);
		params.setConnectionTimeout(connectionTimeOut);
		params.setSoTimeout(socketTimeout);
		connectionManager.setParams(params);
		client = new HttpClient(connectionManager);
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
	}
	
	
	
	/**
	 * 使用get方式调用
	 * @param url调用的URL
	 * @return 调用得到的字符串
	 */
	public static String httpGet(String url) {
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		return getResponseStr(getMethod);
	}
	
	/**
	 * 发送3次http请求
	 * 使用get方式调用
	 * @param url调用的URL
	 * @return 调用得到的字符串
	 */
	public static String httpThreeGet(String url) {
		String resultStr = "";
		for(int k = 1 ; k <= 3 ; k++){//最多进行3次http请求测试
			resultStr = httpGet(url);
			if(!StringUtil.isNullStr(resultStr)){
				return resultStr;
			}
		}
		return resultStr;
	}
	

	/**
	 * 使用post方式调用
	 * @param url 调用的URL
	 * @param values 传递的参数值
	 * @return 调用得到的字符串
	 */
	public static String httpPost(String url, NameValuePair[] values) {
		List<NameValuePair[]> list = new ArrayList<NameValuePair[]>();
		list.add(values);
		return httpPost(url, list);
	}
	
	
	/**
	 * 使用post方式调用
	 * @param url 调用的URL
	 * @param params 参数Map
	 * @return 调用得到的字符串
	 */
	public static String httpPost(String url, Map<String,String> params) {
		PostMethod postMethod = new PostMethod(url);
		for(Entry<String,String> e:params.entrySet()){
			String value = "";
			if(e.getValue() != null){
				value = e.getValue();
			}
			postMethod.addParameter(new NameValuePair(e.getKey(),value));
		}
		return getResponseStr(postMethod);
	}
	
	/**
	 * 发送3次http请求
	 * @param url 调用的URL
	 * @param params 参数Map
	 * @return 调用得到的字符串
	 */
	public static String httpThreePost(String url, Map<String,String> params) {
		String resultStr = "";
		for(int k = 1 ; k <= 3 ; k++){//最多进行3次http请求测试
			resultStr = httpPost(url, params);
			if(!StringUtil.isNullStr(resultStr)){
				return resultStr;
			}
		}
		return resultStr;
	}
	
	/**
	 * 使用post方式调用
	 * @param url 调用的URL
	 * @param values 传递的参数值List
	 * @return 调用得到的字符串
	 */
	public static String httpPost(String url, List<NameValuePair[]> values) {
		PostMethod postMethod = new PostMethod(url);
		//将表单的值放入postMethod中
		for (NameValuePair[] value : values) {
			postMethod.addParameters(value);
		}
		return getResponseStr(postMethod);
	}
	
	/**
	 * 发送post或get请求获取响应信息
	 * @param method	http请求类型,post或get请求
	 * @return			服务器返回的信息
	 */
	public static String getResponseStr(HttpMethodBase method) {
		StringBuilder sb = new StringBuilder();
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode != 200) {
				LOG.error("HttpClient Error : statusCode = " + statusCode + ", uri :" + method.getURI() );
				return "";
			}
			//以流的行式读入，避免中文乱码
			InputStream is = method.getResponseBodyAsStream();
			BufferedReader dis = new BufferedReader(new InputStreamReader(is,"utf-8"));   
			String str = "";                           
			while ((str = dis.readLine()) != null) {
				sb.append(str);
			}
		} catch (Exception e) {
			LOG.info("调用远程出错;发生网络异常!");
			e.printStackTrace();
		} finally {
			// 关闭连接
			method.releaseConnection();
		}
		return sb.toString();
	}
	
	/**
	 * 使用post方式调用
	 * @param url 调用的URL
	 * @param values 传递的参数值
	 * @param xml 传递的xml参数
	 * @return
	 */
	public static String httpPost(String url, NameValuePair[] values, String xml){
		StringBuilder sb = new StringBuilder();
		
		LOG.debug(" http url :" + url);
	     for (NameValuePair nvp : values) {
	    	 LOG.debug(" http param :" + nvp.getName() + " = " + nvp.getValue());
	      }

		
		PostMethod method = new PostMethod(url);
		method.setQueryString(values);
		method.addRequestHeader("Content-Type", "text/xml;charset=UTF-8");
		RequestEntity reis = null;
		InputStream input = null;
		InputStream is = null;
		BufferedReader dis = null;
		try {
			input = new ByteArrayInputStream(xml.getBytes("utf-8"));
			reis = new InputStreamRequestEntity(input);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());

			method.setRequestEntity(reis);
			client.executeMethod(method);

			// 以流的行式读入，避免中文乱码
			is = method.getResponseBodyAsStream();
			dis = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String str = "";
			while ((str = dis.readLine()) != null) {
				sb.append(str);
			}
		} catch (HttpException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
			try {
				if (dis != null)
					dis.close();
				if (is != null)
					is.close();
				if (input != null)
					input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	/**
	 * 将MAP<String, Object>转换成httpclient请求参数
	 * @param paramMap
	 * @return
	 */
	public static NameValuePair[] praseParameterMap(Map<String, Object> paramMap) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		Set<String> keys = paramMap.keySet();
		for(Iterator it = keys.iterator(); it.hasNext(); ){
			String key = (String)it.next();
			Object value = paramMap.get(key);
			if(value instanceof String){
				NameValuePair nvp = new NameValuePair();
				nvp.setName(key);
				nvp.setValue((String) value);
				list.add(nvp);
			}else if(value instanceof String[]){
				String[] valueArray = (String[]) value;
				for(int i=0; i<valueArray.length; i++){
					NameValuePair nvp = new NameValuePair();
					nvp.setName(key);
					nvp.setValue(valueArray[i]);
					list.add(nvp);
				}
			}else if(value==null){
				NameValuePair nvp = new NameValuePair();
				nvp.setName(key);
				nvp.setValue("");
				list.add(nvp);
			} else {
				NameValuePair nvp = new NameValuePair();
				nvp.setName(key);
				nvp.setValue(value.toString());
				list.add(nvp);
			}
		}
		NameValuePair[] nvps = new NameValuePair[list.size()];
		list.toArray(nvps);
		return nvps;
	}


	public static MultiThreadedHttpConnectionManager getConnectionManager() {
		return connectionManager;
	}


	public static void setConnectionManager(
			MultiThreadedHttpConnectionManager connectionManager) {
		HttpSenderUtil.connectionManager = connectionManager;
	}


	public static HttpClient getClient() {
		return client;
	}


	public static void setClient(HttpClient client) {
		HttpSenderUtil.client = client;
	}


	public static int getMaxHostConnections() {
		return maxHostConnections;
	}


	public static void setMaxHostConnections(int maxHostConnections) {
		HttpSenderUtil.maxHostConnections = maxHostConnections;
	}


	public static int getMaxTotalConnections() {
		return maxTotalConnections;
	}


	public static void setMaxTotalConnections(int maxTotalConnections) {
		HttpSenderUtil.maxTotalConnections = maxTotalConnections;
	}
	
	
}
