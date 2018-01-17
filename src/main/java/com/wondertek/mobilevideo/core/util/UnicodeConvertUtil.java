package com.wondertek.mobilevideo.core.util;

import java.text.StringCharacterIterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wondertek.mobilevideo.core.util.StringUtil;

/**
 * 实现字符串和[uxxxx]之间的相互转换
 * @author hubin
 */
public class UnicodeConvertUtil 
{
	private static final transient Log log = LogFactory.getLog(UnicodeConvertUtil.class);
	/**
	 * 把普通字符串转换成\\u(一个斜线,此处不写两个会报错)
	 * @param gbString
	 * @return
	 */
	public static String gbEncoding(final String gbString) 
    {         
        char[] utfBytes = gbString.toCharArray();               
        String unicodeBytes = "";                
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) 
        {  
            String hexB = Integer.toHexString(utfBytes[byteIndex]);                       
            if (hexB.length() <= 2) 
            {                           
                hexB = "00" + hexB;                     
            }                       
            unicodeBytes = unicodeBytes + "[u" + hexB + "]";                   
         }                   
        log.debug("unicodeBytes is: " + unicodeBytes);                   
        return unicodeBytes;
     }                     
    
	/**
	 * 把\\u转换成原来的字符串
	 * @param dataStr
	 * @return
	 */
    public static String decodeUnicode(final String dataStr) 
    {                
        int start = 0;                  
        int end = 0;                
        final StringBuffer buffer = new StringBuffer();     
        String charStr = "";   
        try 
        {
			while (start > -1) 
			{                     
			    end = dataStr.indexOf("\\u", start + 2);                      
			    if (end == -1) 
			    {                          
			        charStr = dataStr.substring(start + 2, dataStr.length());                     
			    } else 
			    {                        
			    	charStr = dataStr.substring(start + 2, end);                      
			    }                     
			    //System.out.println("charStr = " + charStr);
			    char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。                    
			    buffer.append(new Character(letter).toString());                    
			    start = end;                  
			}
		} 
        catch (Exception e) 
        {
        	log.error("UnicodeConvertUtil解析\\u字符出现异常, 字符为: " + charStr);
        	e.printStackTrace();
		}                  
        return buffer.toString();             
     }       
    
    /**
     * 只对评论中的emoji表情进行编码,转换为\\u(一个斜线,此处不写两个会报错)
     * @param orignalComment
     * @return
     */
    public static String encodeOnlyEmoji(final String orignalComment)
    {
    	StringBuilder stringBuilder = new StringBuilder();
		StringCharacterIterator characterIterator = new StringCharacterIterator(orignalComment);
		for(char cha = characterIterator.first(); cha != characterIterator.DONE;cha = characterIterator.next())
		{
			//不满足正则条件的为emoji字符,如果没有误伤的话
			if(!String.valueOf(cha).matches("[\\u4e00-\\u9fa5a-zA-Z0-9~!@#\\$%\\^&\\*\\(\\)-=_\\+\\[\\]\\{\\}\\\\|;':\",\\./<>\\?“”！@#￥%……&\\*\\（\\）\\-=——\\+【】、\\|；‘’：，。、《》？\\s+]"))
			{
				//需要进行转码
				stringBuilder.append(gbEncoding(String.valueOf(cha)));
			}
			else
			{
				stringBuilder.append(cha);
			}
		}
		return stringBuilder.toString();
    }
    
    /**
     * 对解码后的字符串进行转义
     * @param dataStr
     * @param replaceFlag 是否把\\u替换成[emoji]
     *                    高版本：false
     *                    低版本：true
     * @return
     */
    public static String findAndDecodeUnicodeClt(String dataStr,Boolean... replaceFlag){
    	return findAndDecodeUnicodeClt(null,dataStr,replaceFlag);
    }

	public static String findAndDecodeUnicodeClt(Map<String,String> map, String dataStr,Boolean... replaceFlag){
		String decodeStr = findAndDecodeUnicode(map,dataStr,replaceFlag);
		String returnStr = StringEscapeUtils.escapeJava(decodeStr);
		return returnStr; // 转义，转\a为\\a
	}

	/**
	 * 对解码后的字符串进行转义(默认使用高版本解析)
	 * @param dataStr
	 * @return
	 */
	public static String findAndDecodeUnicodeClt(String dataStr){
		String decodeStr = findAndDecodeUnicode(dataStr,false);
		String returnStr = StringEscapeUtils.escapeJava(decodeStr);
		return returnStr; // 转义，转\a为\\a
	}
    
    /**
     * 对字符串中可能存在的\\u字符进行解码处理
     * @param dataStr 要处理的字符串,可能有也可能没有\\u字符
     * @param replaceFlag 是否把\\u替换成[emoji]
     * @param replaceFlag 数组,replaceFlag[0]--是否把\\u替换成[emoji],replaceFlag[1](不一定存在)--把3.0以上版本中代表
	 *                    自定义表情的[e:xxx]替换成[uxxxx],默认为true
     * @return
     */
    public static String findAndDecodeUnicode(String dataStr,Boolean... replaceFlag)
    {
    	return findAndDecodeUnicode(null,dataStr,replaceFlag);
    }

	/**
	 * 对字符串中可能存在的\\u字符进行解码处理
	 * @param map 加载了新表情编号和emoji编码的对应关系
	 * @param dataStr 要处理的字符串,可能有也可能没有\\u字符
	 * @param replaceFlag 是否把\\u替换成[emoji]
	 * @param replaceFlag 数组,
	 *                    replaceFlag[0]--是否把\\u替换成[emoji],
	 *                    replaceFlag[1](不一定存在)--把3.0以上版本中代表自定义表情的[e:xxx]替换成[uxxxx],默认为true;
	 *                    replaceFlag[2](不一定存在)--
	 * @return
	 */
	public static String findAndDecodeUnicode(Map<String,String> map, String dataStr, Boolean... replaceFlag) {
		/*Pattern pattern = Pattern.compile("\\\\u[a-zA-Z0-9]{1,4}");
    	Matcher matcher = pattern.matcher(dataStr);
    	while(matcher.find())
    	{
    		String matchedStr = matcher.group();
    		if(StringUtil.isNullStr(matchedStr))
    		{
    			continue ;
    		}
    		dataStr = dataStr.replaceFirst(matchedStr.replace("\\u", "\\\\u"), decodeUnicode(matchedStr));
    	}*/

		dataStr = StringEscapeUtils.unescapeJava(dataStr); // 反转义，转\\ue108为\\u108 unicode符
		//Pattern reUnicode = Pattern.compile("\\\\u([0-9a-zA-Z]{4})");
		Pattern reUnicode = Pattern.compile("\\[u([0-9a-zA-Z]{4})\\]");
		//匹配新增表情的符号,形如[e:001]
		Pattern newExpressionPattern = Pattern.compile("\\[e:\\d{3}\\]");

		/*
		 有些地方的调用没有传入此参数,而且不好去改动,
		 此处默认为true,把[e:001]通过对应关系转换为[uxxxx]
		 */
		boolean replaceNewExpressionFlag = true;
		if(replaceFlag != null && replaceFlag.length > 1) {
			replaceNewExpressionFlag = replaceFlag[1];
		}

		//[e:001]通过对应关系转换为[uxxxx]
		if(replaceNewExpressionFlag) {
			StringBuffer stringBuffer = new StringBuffer();
			Matcher newMather = newExpressionPattern.matcher(dataStr);
			while(newMather.find()) {
				String matchStr = newMather.group();
				String key = matchStr.substring(3,6);
				String value = "[u0000]"; //随意给个默认值,如果给客户端提供的是这个,说明从map没有获取到有效值
				if(map != null) {
					if(!StringUtil.isNullStr(map.get(key))) {
						value = map.get(key);
					}
					newMather.appendReplacement(stringBuffer,value);
				}
				else {
					log.error("***此处需要新表情和emoji对应关系的map,但map is null! ***");
				}
			}
			newMather.appendTail(stringBuffer);
			dataStr = stringBuffer.toString();
		}

		StringBuffer sb = new StringBuffer(dataStr.length());
		//标识是否需要把表情换成字符串[emoji],version<=2.2时需要为true
		boolean flag;
		if(replaceFlag.length == 0) {
			flag = false;
		}
		else {
			flag = replaceFlag[0];
		}
		Matcher m = reUnicode.matcher(dataStr);
		while (m.find()) {
			//需要把[uxxxx]替换成[emoji]
			String matchedStr = m.group();
			if(!StringUtil.isNullStr(matchedStr))
			{
				matchedStr = "\\" + matchedStr.replaceAll("\\[", "").replaceAll("\\]", "");
			}
			if(flag)
			{
				m.appendReplacement(sb,"[emoji]");
			}
			else
			{
				m.appendReplacement(sb,
						StringEscapeUtils.unescapeJava(matchedStr));
			}
		}
		m.appendTail(sb);

		return sb.toString();
		//return dataStr;
	}

}
