package com.wondertek.mobilevideo.core.util;


/*
 *	 数据类型(dataType)
 *	1表示移动点播类型
 *	2表示移动直播类型
 *	3表示自运营点播类型
 */
public class CntvUtil {
	
	
	/*
	 * 获取详情页参数串
	 * contId:点播节目id	必选
	 * dataType: 数据类型     选填
	 */
	public static String getDetailPage(String contId, String dataType) {
		if(StringUtil.isNullStr(contId)){
			return "";
		}
		String url = "contId=" + contId;
		if(!StringUtil.isNullStr(dataType)){
			url += "&dataType=" + dataType;
		}
		return url;
	}
	
	/*
	 * 获取直播参数串
	 * contId:直播频道(节目)id	必选
	 * name:直播频道名称		 选填
	 */
	public static String getliveDetailPage(String contId, String name) {
		if(StringUtil.isNullStr(contId)){
			return "";
		}
		String url = "contId=" + contId;
		if(!StringUtil.isNullStr(name)){
			url += "&name=" + name;
		}
		return url;
	}
	
	/*
	 * 获取点播参数串
	 * contId:点播节目id	必选
	 * dataType: 数据类型     选填;默认为1
	 */
	public static String getDramaDetailPage(String contId, String dataType) {
		if(StringUtil.isNullStr(contId)){
			return "";
		}
		String url = "contId=" + contId;
		if(!StringUtil.isNullStr(dataType)){
			url += "&dataType=" + dataType;
		}else{
			url += "&dataType=1";
		}
		return url;
	}
}
