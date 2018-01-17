package com.wondertek.mobilevideo.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CodeUtil {
	
	public static String encodeFromUTF8(String utfStr) {
		String encodeStr = "";
		if (utfStr != null && !"".equals(utfStr)) {
			try {
				encodeStr = URLEncoder.encode(utfStr, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return encodeStr;
	}

	public static String decodeToUTF8(String encodeStr) {
		String utfStr = "";
		if (encodeStr != null && !"".equals(encodeStr)) {
			try {
				utfStr = URLDecoder.decode(encodeStr, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return utfStr;
	}

}
