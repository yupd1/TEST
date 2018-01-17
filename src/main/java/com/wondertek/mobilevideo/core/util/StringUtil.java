package com.wondertek.mobilevideo.core.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.math.NumberUtils;
import org.codehaus.jettison.json.JSONObject;
import org.apache.struts2.json.JSONUtil;

public class StringUtil {

	/**
	 * 判断是否为合法的日期时间字符串
	 * 
	 * @param str_input
	 * @return boolean;符合为true,不符合为false
	 */
	public static boolean isDate(String str_input, String rDateFormat) {
		if (!isNull(str_input)) {
			SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
			formatter.setLenient(false);
			try {
				formatter.format(formatter.parse(str_input));
			} catch (Exception e) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	public static Date nullToDate(Object str_input, String rDateFormat) {
		if (!isNullStr(str_input)) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
				formatter.setLenient(false);
				return formatter.parse(null2Str(str_input));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static boolean isNull(String str) {
		if (str == null)
			return true;
		else
			return false;
	}
	public static boolean isNullStr(String str) {
		if (str == null||str.trim().equals(""))
			return true;
		else
			return false;
	}
	
	public static boolean isNullStr(Object str) {
		if (str == null || isNullStr(str.toString()))
			return true;
		else
			return false;
	}

	// 将NULL转换成空字符串
	public static String null2Str(Object value) {
		return value == null || "null".equals(value.toString()) ? "" : value.toString();
	}

	public static String null2Str(String value) {
		return value == null || "null".equals(value) ? "" : value.trim();
	}

	public static String nullToString(String value) {
		return value == null || "null".equals(value) ? "" : value.trim();
	}

	public static String nullToString(Object value) {
		return value == null ? "" : value.toString();
	}

	public static Long nullToLong(Object value){
		return value == null || "null".equals(value.toString()) ? 0L: stringToLong(value.toString().trim());
	}
	
	public static Long nullToCloneLong(Object value){
		return value == null || NumberUtils.isNumber(value.toString()) == false  ? null : stringToLong(value.toString());
	}
	
	public static Integer nullToInteger(Object value){
		return value == null || "null".equals(value.toString()) ? 0: stringToInteger(value.toString());
	}
	
	public static Double nullToDouble(Object value){
		Double d = new Double(0);
		try{
			if(value != null && !StringUtil.isNullStr(value.toString())){
				d = Double.parseDouble(String.valueOf(value));
			}
		}catch(Exception e){
			e.printStackTrace();
			d = new Double(0);
		}
		return d;
	}
	
	public static Boolean nullToBoolean(Object value){
		if(value == null 
				|| "null".equals(value.toString()))
			return false;
		if("1".equals(value.toString().trim()) 
				|| "true".equalsIgnoreCase(value.toString().trim()))
			return true;
		if("1".equals(value.toString().trim()) 
				|| "yes".equalsIgnoreCase(value.toString().trim()))
			return true;
		return false;
	}
	
	public static Float nullToFloat(Object value) {
		return value == null || "null".equals(value.toString())
				|| "".equals(value.toString()) ? 0.0F : stringToFloat(value
				.toString().trim());
	}

	public static Float stringToFloat(String value) {
		Float f;
		value = nullToString(value);
		if ("".equals(value)) {
			f = 0.0F;
		} else {
			try {
				f = Float.valueOf(value);

			} catch (Exception e) {
				f = 0.0F;
			}
		}
		return f;
	}
	
	public static Long stringToLong(String value) {
		Long l;
		value = nullToString(value);
		if ("".equals(value)) {
			l = 0L;
		} else {
			try {
				l = Long.valueOf(value);

			} catch (Exception e) {
				l = 0L;
			}
		}
		return l;
	}
	
	public static Integer stringToInteger(String value) {
		Integer l;
		value = nullToString(value);
		if ("".equals(value)) {
			l = 0;
		} else {
			try {
				l = Integer.valueOf(value);

			} catch (Exception e) {
				l = 0;
			}
		}
		return l;
	}
	public static Boolean stringToBoolean(String value) {
		if (("true").equals(value)) {
			return true;
		} else if (("false").equals(value)){
			return false;
		}
		return null;
	}
	
	public static List<Long> stringToLongArray(String value){
		List<Long> ls = new ArrayList<Long> ();
		if(StringUtil.isNullStr(value)){
			return ls;
		}
		
		String[] ids = value.split(",");
		for(int i = 0; i < ids.length; i ++ ){
			try{
				if(StringUtil.isNumber(ids[i])){
					ls.add(Long.parseLong(ids[i]));
				}
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
		return ls;
	} 
	
	public static List<Long> stringToLongArray(Object value) throws Exception{
		List<Long> ls = new ArrayList<Long> ();
		if(value == null || StringUtil.isNullStr(value.toString())){
			return ls;
		}
		
		try{
			String[] ids = value.toString().split(",");
			for(int i = 0; i < ids.length; i ++ ){
				if(StringUtil.isNumber(ids[i])){
					ls.add(Long.parseLong(ids[i]));
				}
			}
		}catch(Exception e){
			throw e;
		}
		return ls;
	} 
	
	public static List<String> strToStrArray(String value, String splitChar)throws Exception{
		List<String> ls = new ArrayList<String> ();
		if(value == null || "".equals(value)){
			return ls;
		}
		
		try{
			String[] ids = value.split(splitChar);
			for(int i = 0; i < ids.length; i ++ ){
				if(!StringUtil.isNullStr(ids[i])){
					ls.add(ids[i].trim());
				}
			}
		}catch(Exception e){
			throw e;
		}
		return ls;
	} 
	
	public static String strArrayToString(List<String> list, String splitChar)throws Exception{
		StringBuffer sb = new StringBuffer ("");
		if(list == null || list.size() == 0){
			return sb.toString();
		}
		
		try{
			for(Iterator<String> it = list.iterator(); it.hasNext(); ){
				String value = it.next();
				sb.append(value);
				if(it.hasNext()){
					sb.append(splitChar);
				}
			}
		}catch(Exception e){
			throw e;
		}
		return sb.toString();
	} 
	
	public static String strSetToString(Set<String> set, String splitChar)throws Exception{
		StringBuffer sb = new StringBuffer ("");
		if(set == null || set.size() == 0){
			return sb.toString();
		}
		
		try{
			for(Iterator<String> it = set.iterator(); it.hasNext(); ){
				String value = it.next();
				sb.append(value);
				if(it.hasNext()){
					sb.append(splitChar);
				}
			}
		}catch(Exception e){
			throw e;
		}
		return sb.toString();
	} 
	
	public static List<Long> setToList(Set<Long> set){
		List<Long> list = new ArrayList<Long> ();
		if(set != null && set.size() > 0){
			for(Iterator<Long> it = set.iterator(); it.hasNext(); ){
				list.add(it.next());
			}
		}
		return list;
	}
	
	public static String longArrayToString(List<Long> values){
		StringBuffer sb = new StringBuffer ();
		if(values == null || values.size() == 0){
			return sb.toString();
		}
		
		try{
			boolean isFirst = true;
			Set<Long> setLong = new HashSet<Long> ();
			for(Iterator<Long> it = values.iterator(); it.hasNext(); ){
				Long value = it.next();
				if(setLong == null || !setLong.contains(value)){
					if(isFirst == false)
						sb.append(",");
					sb.append(value);
					isFirst = false;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static String longArray2String(List<Long> values){
		boolean isChangeDate = false;
		StringBuffer sb = new StringBuffer ();
		if(values == null || values.size() == 0)
			return null;
		
		try{
			Set<Long> setLong = new HashSet<Long> ();
			for(Iterator<Long> it = values.iterator(); it.hasNext(); ){
				Long value = it.next();
				if(setLong == null || !setLong.contains(value)){
					sb.append("'" + value + "',");
					setLong.add(value);
					isChangeDate = true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return isChangeDate ? sb.toString().substring(0, sb.toString().length() - 1) : sb.toString();
	}
	
	public static List<String> strCollectionToList(Collection<String> collections){
		List<String> list = new ArrayList<String> ();
		if(collections != null && collections.size() > 0){
			for(Iterator<String> it = collections.iterator(); it.hasNext(); ){
				list.add(it.next());
			}
		}
		return list;
	}
	
	public static List<Long> longCollectionToList(Collection<Long> collections){
		List<Long> list = new ArrayList<Long> ();
		if(collections != null && collections.size() > 0){
			for(Iterator<Long> it = collections.iterator(); it.hasNext(); ){
				list.add(it.next());
			}
		}
		return list;
	}
	
	public static List<Long> strListToLongList(List<String> strList)throws Exception{
		List<Long> longList = new ArrayList<Long> ();
		try{
			for(String str : strList){
				longList.add(Long.valueOf(str));
			}
		}catch(Exception e){
			throw e;
		}
		return longList;
	}
	
	public static String strListToSQL(List<String> strList){
		StringBuffer sb = new StringBuffer ();
		boolean isFirst = false;
		for(String str : strList){
			if(isFirst)sb.append(",");
			sb.append(String.format("'%s'", StringUtil.null2Str(str)));
			isFirst = true;
		}
		return sb.toString();
	}
	
	public static List<String> stringToStrList(String value, String splitChar){
		List<String> list = new ArrayList<String> ();
		if(StringUtil.isNullStr(value))return list;
		
		String[] strArray = value.split(splitChar);
		for(int i = 0; i < strArray.length; i ++){
			if(!StringUtil.isNullStr(strArray[i]))
				list.add(strArray[i].trim());
		}
		return list;
	}
	
	public static Long stringToLong(Object value) {
		Long l;
		value = nullToString(value);
		if ("".equals(value)) {
			l = 0L;
		} else {
			try {
				l = Long.valueOf(value.toString());
			} catch (Exception e) {
				l = 0L;
			}
		}
		return l;
	}
	
	
	public static Map<String,String> stringToMap(String value)  throws Exception {
		
		Map<String,String> map= new HashMap<String,String>();
		try{
			JSONObject jsonObj = new JSONObject(value);
			Iterator<String> keyName = jsonObj.keys();
			String name;

		    while(keyName.hasNext())
		    {
		    	name =keyName.next();
		    	map.put(name, jsonObj.getString(name));
		    }
		}catch(Exception e){
			throw e;
		}
		
	
	    return map;
	}
	
	
	/**
	 * 判断字符串是否是整数
	 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value.trim());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static int parseInteger(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * 判断字符串是否是浮点数
	 */
	public static boolean isDouble(String value) {
		try {
			Double d=Double.parseDouble(value.trim());
			String tempD=d.toString();
			if (tempD.contains("."))
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 判断字符串是否是数字
	 */
	public static boolean isNumber(String value) {
		if(value == null || StringUtil.isNullStr(value)) return false;
		return isInteger(value) || isDouble(value);
	}

	/**
	 * 判断字符串是否布尔
	 * @param value
	 * @return
	 */
	public static boolean isBoolean(String value){
		try {
			Boolean b = Boolean.parseBoolean(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/** 判断是否为时间 * */
	public static boolean isDate(String value) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			sdf.parse(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 
	 * 中文转换--文章换行的转换
	 * 
	 * @param str
	 * 
	 * @return
	 */

	public static String getText(String str) {
		if (str == null)
			return ("");
		if (str.equals(""))
			return ("");
		// 建立一个StringBuffer来处理输入数据
		StringBuffer buf = new StringBuffer(str.length() + 6);
		char ch = '\n';
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch == '\r') {
				buf.append(" ");
			} else if (ch == '\n') {
				buf.append(" ");
			} else if (ch == '\t') {
				buf.append("    ");
			} else if (ch == ' ') {
				buf.append(" ");
			} else if (ch == '\'') {
				buf.append("\\'");
			} else if(ch == '<'){
				buf.append("\\<");
			} else if(ch == '>'){
				buf.append("\\>");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}
	//清除特殊字符
	public static String getescapeText(String str) {
		if (str == null)
			return ("");
		if (str.equals(""))
			return ("");
		// 建立一个StringBuffer来处理输入数据
		StringBuffer buf = new StringBuffer(str.length() + 6);
		char ch = '\n';
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch == '\r') {
				buf.append("");
			} else if (ch == '\n') {
				buf.append("");
			} else if (ch == '\t') {
				buf.append("");
			} else if (ch == ' ') {
				buf.append("");
			} else if (ch == '\'') {
				buf.append("");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}
	
	/**
	 * ASCII码表第48～57号为0～9十个阿拉伯数字；65～90号为26个大写英文字母，97～122号为26个小写英文字母
	 */
	public static boolean isCharAndNum(String source){
		StringCharacterIterator sci = new StringCharacterIterator(source);

		for (char c = sci.first(); c != StringCharacterIterator.DONE; c = sci.next()) {
			if ((c > 47 && c < 58) || (c > 64 && c < 91) || (c > 96 && c < 123)) 
				continue;
			else
				return false;
		}
		return true;
	}
	
	/**
	 * 清除所有特殊字符，只保留中英文字符和数字
	 * @param str
	 * @return
	 */
	public static String getEscapeText(String str){
		try{
			String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";     
		    Pattern p = Pattern.compile(regEx);        
		    Matcher m = p.matcher(str);  
		    return m.replaceAll("");
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 清除所有特殊字符，只保留中英文字符和数字，以及中文标点符号
	 * @param str
	 * @return
	 */
	public static String getZWEscapeText(String str){
		try{
			String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~@#￥%……&*（）——+|{}]";     
		    Pattern p = Pattern.compile(regEx);        
		    Matcher m = p.matcher(str);  
		    return m.replaceAll("");
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean haveEscapeText(String str,String regex){
		if(str == null || str.equals("") || regex == null)
			return false;
		boolean flag = true;
		try {
			if(str.replaceAll(regex,"").length() == 0)
				flag = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 清除所有特殊字符，只保留中英文字符和数字
	 * @param str
	 * @return
	 */
	public static boolean isEscapeText(String str){
		boolean flag=false;
		try{
			String regEx = "[`~!@#$%^&*()+=|{}':;',…\\[\\].<>/?~！@#￥%…&*（）——+|{}【】‘；：”“’。，、？]";     
			Pattern p = Pattern.compile(regEx);        
			Matcher m = p.matcher(str);  
			if(m.find())flag=true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 判断字符串中是否包含除中英文字符和数字外的特殊字符，包含返回true
	 * @param str
	 * @return
	 */
	public static boolean haveEscapeText(String str){
		if(str.replaceAll("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*-*_*\\s*", "").length()==0){
			return false;
	    }else{
	    	return true;
	    }
	}
	
	/**
	 * 是否包含中文和&符号
	 * @param str
	 * @return
	 */
	public static boolean isContainChinese(String str) {
		if(str == null || "".equals(str))
			return false;
		
		String regEx = "[\u4e00-\u9fa5]|&"; 
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		while (m.find()) {
			return true;
		} 
		return false;
	}
	
	public static boolean isAvalidPhoneNumbers(String phoneNumbers){
		if(phoneNumbers == null || "".equals(phoneNumbers))
			return true;
		
		String regEx = "^([0-9]|\\|)*$"; 
		if(phoneNumbers.replaceAll(regEx, "").length() == 0){
			return true;
	    }else{
	    	return false;
	    }
	}
	
	/**
	 * 
	 * 根据转义列表对字符串进行转义(escape)。
	 * 
	 * @param source
	 *            待转义的字符串
	 * 
	 * @param escapeCharMap
	 *            转义列表
	 * 
	 * @return 转义后的字符串
	 */

	public static String escapeCharacter(String source, HashMap escapeCharMap) {

		if (source == null || source.length() == 0) {

			return source;

		}

		if (escapeCharMap.size() == 0) {

			return source;

		}

		StringBuffer sb = new StringBuffer(source.length() + 100);

		StringCharacterIterator sci = new StringCharacterIterator(source);

		for (char c = sci.first();

		c != StringCharacterIterator.DONE;

		c = sci.next()) {

			String character = String.valueOf(c);

			if (escapeCharMap.containsKey(character)) {

				character = (String) escapeCharMap.get(character);

			}

			sb.append(character);

		}

		return sb.toString();

	}

	/**
	 * 
	 * 中文转换--文章换行的转换
	 * 
	 * @param str
	 * 
	 * @return
	 */

	public static String changeEnter(String str) {
		if (str == null)
			return ("");
		if (str.equals(""))
			return ("");
		// 建立一个StringBuffer来处理输入数据
		StringBuffer buf = new StringBuffer(str.length() + 6);
		char ch = '\n';
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch == '\r') {
				buf.append("|");
			} else if (ch == '\n') {
				buf.append("|");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	// 截掉url左边的一级目录名,如/wap/news/index.xml -> /news/index.xml
	public static String trimLeftNode(String str) {
		if (str == null)
			return "";

		if (str.startsWith("/")) {
			int ind = str.indexOf("/", 1);
			if (ind > 0)
				return str.substring(ind);
		}
		return str;
	}
	
	// 截掉url右边的一级目录名,如/wap/news/index.xml -> /wap/news/
	public static String trimRightNode(String str){
		if (str == null)
			return "";

		if(str.endsWith("/")){ 
			str = str.substring(0, str.length() - 1);
		}
		
		int index = 0;
		if ((index = str.lastIndexOf("/")) > 0) {
			return str.substring(0, index);
		}
		return str;
	}

	public static String generatedUrl(int pageType, List<String> sourceList, String nodestr, int maxint) {
		List<String> nodeList = new ArrayList<String>();
		Random rmd = new Random();
		String rstr = "";
		Set<String> cpSet = new HashSet<String>();
		Set<Integer> distNum = new HashSet<Integer>();
		Set<String> distCp = new HashSet<String>();
		for (int i = 0; i < sourceList.size(); i++) {
			String tmpstr = sourceList.get(i);
			if (getSpstr(tmpstr, 1).equals(nodestr)) {
				nodeList.add(tmpstr);
				cpSet.add(getSpstr(tmpstr, 3));
			}
		}
		if (nodeList.size() > maxint) {
			for (int i = 0; i < maxint;) {
				int tmpint = rmd.nextInt(nodeList.size());
				String tmpstr = nodeList.get(tmpint);
				if ((distCp.add(getSpstr(tmpstr, 3)) || distCp.size() >= cpSet
						.size())
						&& distNum.add(tmpint)) {
					rstr += "<a href='" + getSpstr(tmpstr, 4) + "'>"
					+ getSpstr(tmpstr, 2) + "</a><br/>";
					i++;
				}
			}
		} else {
			for (int i = 0; i < nodeList.size(); i++) {
				String tmpstr = nodeList.get(i);
				rstr += "<a href='" + getSpstr(tmpstr, 4) + "'>"
				+ getSpstr(tmpstr, 2) + "</a><br/>";
			}
		}
		return rstr;
	}

	public static String getSpstr(String spstr, int level) {
		String rstr = "";
		for (int i = 0; i < level; i++) {
			if (spstr.indexOf("|*") == -1) {
				rstr = spstr;
				return rstr;
			} else {
				rstr = spstr.substring(0, spstr.indexOf("|*"));
			}
			spstr = spstr.substring(spstr.indexOf("|*") + 2, spstr.length());
		}
		return rstr;
	}

	public static String toString(Object obj) {
		try {
			return obj.toString();
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String getEncrypt(String mobile,String pid){
		StringBuffer buf = new StringBuffer();
		buf.append(mobile);
		buf.append(pid);
		buf.append("MDN2000");
//		buf.append(CmsConstants.SYSTEMCONFIG.get("PORTAL_MDN"));//"MDN2000"
		String md5String = buf.toString();
        MD5 md5 = new MD5();
        byte[] byteone = md5String.getBytes();
        return md5.md5Str(byteone, 0, byteone.length);
	}
	
	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
        '9', 'a', 'b', 'c', 'd', 'e', 'f' }; 
	
	/**
     * 把byte[]数组转换成十六进制字符串表示形式
     * @param tmp    要转换的byte[]
     * @return 十六进制字符串表示形式
     */
    public static String byteToHexString(byte[] tmp) {
    	if(tmp == null){
    		throw new NullPointerException();
    	}
    	int len = tmp.length;
        char str[] = new char[len * 2];
        int i = 0;
        for(byte b:tmp){
        	str[i*2] = hexDigits[b >>> 4 & 0xf]; // 取字节中高 4 位的数字转换, 
            str[i*2+1] = hexDigits[b & 0xf]; // 取字节中低 4 位的数字转换
            i++;
        }
        return new String(str);
    }
    
    /**
     * 得到一个Long值的指定长度的字符串形式
     * 
     * @param l
     * @param len
     * @return
     */
    public static String getStringByAppointLen(Long l,int len){
    	return getStringByAppointLen(l.toString(), len, true);
    }
    
    /**
     * 得到一个Integer值的指定长度的字符串形式
     * NOTE:	不足的前面添'0'
     * @param i
     * @param len
     * @return
     */
    public static String getStringByAppointLen(Integer i,int len){
    	return getStringByAppointLen(i.toString(), len, true);
    }
    
    /**
     * 得到一个String值的指定长度的字符串形式
     * NOTE:	不足的前面添'0'
     * 			
     * @param s
     * @param len
     * @param cutHead
     * 		当s的长度大于len时，截取方式：true,截掉头部；否则从截掉尾部
     * 		例如getStringByAppointLen("12345",3,true) ---> "345"
     * @return
     */
    public static String getStringByAppointLen(String s,int len,boolean cutHead){
    	if(s == null || len <=0){
    		s = "";
    	}
    	if(len > s.length()){
    		int size = len - s.length();
    		StringBuffer sb = new StringBuffer();
    		while(size -- > 0){
    			sb.append("0");
    		}
    		sb.append(s);
    		return sb.toString();
    	}else if(len == s.length()){
    		return s;
    	}else{
    		if(cutHead){
    			return s.substring(s.length() - len, s.length());
    		}else{
    			return s.substring(0,len);
    		}
    	}
    }
	
	/**
     * 通过ID生成存储路径
     * @param id
     * @return
     */
    public static String getFileDirPathById(Long id){
    	String s = StringUtil.getStringByAppointLen(id, 12);
		StringBuffer sb = new StringBuffer();
		sb.append(s.substring(0,3)).append("/")
			.append(s.substring(3,6)).append("/")
			.append(s.substring(6,9)).append("/")
			.append(s.substring(9,12)).append("/");
		return sb.toString();
	}
	
    public static Boolean string2Boolean(String str){
    	try{
    		if("0".equals(str))
    			return Boolean.FALSE;
    		else if("1".equals(str))
    			return Boolean.TRUE;
    		else if("false".equalsIgnoreCase(str))
    			return Boolean.FALSE;
    		else if("true".equalsIgnoreCase(str))
    			return Boolean.TRUE;
    	}catch(Exception e){
    		return Boolean.FALSE;
    	}
		return Boolean.FALSE;
	}
    
    public static String trimSqlIds(String ids){
    	String tmpIds=ids;
    	if(tmpIds!=null&&!"".equals(tmpIds)){
		if(tmpIds.endsWith(","))
			tmpIds=tmpIds.substring(0,tmpIds.length()-1);
		if(tmpIds.startsWith(","))
			tmpIds=tmpIds.substring(1,tmpIds.length());
    	}else tmpIds="";
    	return tmpIds;
    }
    
    public static String formatData(String tpl,double data){
    	DecimalFormat df = new DecimalFormat(tpl);
		String format = df.format(data);
		
		return format;
    }
    /**
     * 返回中文长度
     * @param str
     * @param chineseLength //一个中文的长度（2或3）
     * @return
     */
    public static int chineseLength(String str,int chineseLength){
    	int length = 0;
    	if(str == null || str.equals(""))
    		return length;
    	String chinese = "[\u0391-\uFFE5]";
    	for(int i = 0;i<str.length();i++){
    		String temp = str.substring(i,i+1);
    		if(temp.matches(chinese)){
    			length += chineseLength;
    		}else{
    			length ++;
    		}
    	}
    	return length;
    }
    
    /**
	 * 有除英文和字母外的其他字符？
	 */
    public static boolean haveExcludeText(String str){
		if(str == null || str.equals("")){
			return false;
		}
		for(int i = 0 ;i < str.length();i ++){
			char temp = str.charAt(i);
			//数字0~9，小写字母a~z，大写字母A~Z
			if((temp >= 48 && temp <=57)||(temp >=65 && temp <=90)||(temp >=97 && temp <=122)){
    		}else{
    			return true;
    		}
		}
		return false;
	}
	/**
	 * 有西文特殊字符？
	 * @param str
	 * @param 西文特殊字符 ：空格!"#$%&'()*+'-./以及:;<=>?@以及[\]^_`以及{|}~
	 * @return
	 */
	public static  boolean hasSpecialText(String str){
		if(str == null || str.equals("")){
			return false;
		}
		for(int i = 0 ;i < str.length();i ++){
			char temp = str.charAt(i);
			//空格!"#$%&'()*+'-./以及:;<=>?@以及[\]^_`以及{|}~
			if((temp >= 32 && temp <= 47)||(temp >=58 && temp <=64)||(temp >=91 && temp <=96)||(temp >=123 && temp <=126)){
				return true;
    		}
		}
		return false;
	}
	
	public static boolean checkDate(String sourceDate){
		try {
			if(StringUtil.isNullStr(sourceDate))return false;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateFormat.setLenient(false);
			dateFormat.parse(sourceDate);
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	
	/**
	 * 有西文特殊字符？
	 * @param str 
	 * @param excludes 不验证的西文特殊字符
	 * @param 西文特殊字符（包含空格）	：空格!"#$%&'()*+'-./以及:;<=>?@以及[\]^_`以及{|}~
	 * @param 对应						： 32^47以及58^64以及91^96以及123^126
	 * @return
	 */
	public static  boolean hasSpecialText(String str,char... excludes){
		if(str == null || str.equals("")){
			return false;
		}
		for(int i = 0 ;i < str.length();i ++){
			char temp = str.charAt(i);
			if(excludes != null && excludes.length > 0){
				int contain = 0;
				for(char exclude : excludes){
					if(exclude == temp){
						contain = 1;
						break;
					}
				}
				if(contain == 1){
					continue;
				}
			}else if(temp == 43 || temp == 45 || temp == 61 || temp == 63 
						|| temp == 91 || temp == 93 || temp == 95 || temp == 123
						|| temp == 125 || temp == 126){//默认排除+-=?[]_{}~
				continue;
			}
			//空格!"#$%&'()*+'-./以及:;<=>?@以及[\]^_`以及{|}~
			if((temp >= 32 && temp <= 47)||(temp >=58 && temp <=64)||(temp >=91 && temp <=96)||(temp >=123 && temp <=126)){
				return true;
    		}
		}
		return false;
	}
	
	public static boolean hasEnterText(String str){
		Pattern wp = Pattern.compile("(\r\n|\r|\n|\n\r)"); 
		Matcher m = wp.matcher(str);
		if(m.find()){
			return true;
		}else{
			return false;
		}
	}
	@SuppressWarnings("rawtypes")
	public static Map<String, String> getSortMap(String sortStr){
    	Map<String, String> map = new HashMap<String, String> ();
    	try{
    		if(!StringUtil.isNullStr(sortStr)){
    			List<Map> sortList = (List<Map>) JSONUtil.deserialize(sortStr);
    			for(Map sortMap : sortList){
    				map.put("sort", StringUtil.null2Str(sortMap.get("property")));
    				map.put("dir", StringUtil.null2Str(sortMap.get("direction")));
    			}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return map;
    }
	
	/**
	 * 去除字符串中的回车、换行符、制表符
	 * \n 回车(\u000a) 
	 * \t 水平制表符(\u0009)
	 * \s 空格(\u0008) 
	 * \r 换行(\u000d)
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	
	// 过滤特殊字符
	public static String strFilter(String str){
			// 只允许字母和数字
			// String regEx = "[^a-zA-Z0-9]";
			// 清除掉所有特殊字符
			String regEx = "[`~!@#$%^&* _　\\[\\]\\-()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(str);
			return m.replaceAll("").trim();
	}
	
	
    public static void main(String[] args){
    	
    }
}
