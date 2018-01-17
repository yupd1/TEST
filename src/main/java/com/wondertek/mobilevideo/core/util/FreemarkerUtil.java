package com.wondertek.mobilevideo.core.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 简易模板编译
 * @author Kyle Guo
 * 
 * */
public class FreemarkerUtil {
	//模板缓存对象
	private static Map<String, Template> templteMap = new HashMap<String, Template>();
	
	/**
	 * 模板编译
	 * @param map 参数MAP
	 * @param templateDir 模板目录
	 * @param templateName 模板文件
	 * 
	 * @return 编译结果
	 * */
	public static String simpleCompile(Map<String, Object> map, String templateDir, String templateName){
		
		String result = "";
		try {
			
			Template tpl = templteMap.get(templateDir+ File.separator + templateName);
			
			if(tpl == null){
				
				FileTemplateLoader tl = new FileTemplateLoader(new File(templateDir));
				
				Configuration cfg = new Configuration();
				cfg.setEncoding(Locale.getDefault(), "utf-8");
				cfg.setStrictSyntaxMode(true);
				cfg.setWhitespaceStripping(true);
				cfg.setNumberFormat("0");
				
				cfg.setTemplateLoader(tl);
				
				tpl = cfg.getTemplate(templateName);
				templteMap.put(templateDir+ File.separator + templateName , tpl);
			}
			
			StringWriter sw = new StringWriter();
			
			try {
				tpl.process(map, sw);
			} catch (TemplateException e) {
				e.printStackTrace();
			}
			
			result = sw.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return result;
		
	}

}
