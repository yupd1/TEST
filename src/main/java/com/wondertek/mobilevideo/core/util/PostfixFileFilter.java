package com.wondertek.mobilevideo.core.util;

import java.io.File;
import java.io.FileFilter;

/*
 
 */
public class PostfixFileFilter implements FileFilter {
	private String[] postfixs;

	public PostfixFileFilter(String...postfixs) {
		this.postfixs = postfixs;
	}
	/*
	 *后缀   
	 */

	public boolean accept(File file) {
		if (postfixs == null || postfixs.length ==0)
			return true;
		String fileName = file.getName();
		if(fileName == null || fileName.equals("")){
			return false;
		}
		for(String postfix : postfixs){
			if(postfix != null && fileName.toLowerCase().endsWith("."+postfix.toLowerCase())){
				return true;
			}
		}
		return false;
	}
}

