package com.wondertek.mobilevideo.core.util;

public class IntegerUtil {

	/**
	 * 16进制转化
	 * @param value
	 * @return
	 */
	public static String toHexValue(String value){
		StringBuffer sb = new StringBuffer();
		byte[] bytes = value.getBytes();
		
		for(byte b : bytes){
			String hex = Integer.toHexString(b & 0xFF);
			
			if (hex.length() < 2) {
		        hex = '0' + hex;
		    }
			sb.append(hex);
		}
		
		return sb.toString();
	}	
	
	/**
	 * 二进制转化
	 * @param value
	 * @return
	 */
	public static String toBinaryValue(String value){
		StringBuffer sb = new StringBuffer();
		byte[] bytes = value.getBytes();
		
		for(byte b : bytes){
			String binary = Integer.toBinaryString(b & 0xFF);
			
			while (binary.length() < 8) {
				binary = '0' + binary;
			}
			sb.append(binary);
		}
		
		return sb.toString();
	}
}
