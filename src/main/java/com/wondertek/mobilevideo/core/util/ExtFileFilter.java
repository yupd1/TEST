package com.wondertek.mobilevideo.core.util;

import java.io.File;

public class ExtFileFilter implements java.io.FileFilter {

	String ext;
	public ExtFileFilter(String ext) {
		this.ext = ext;
	}

	public boolean accept(File pathname) {
		return pathname.getName().endsWith(ext)?true:false;
	}

}

