package com.wondertek.mobilevideo.core.util;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class FilePathHelper
{
	public static final String SEPARATOR = "/";
	public static final String POINT=".";

	/**
	 * 在文件路径后面加上文件路径分割符
	 * 
	 * @param directory
	 * @return String
	 */
	public static String addSeparator(String directory)
	{
		if (directory == null || "".equals(directory.trim()))
			return "";
		directory = replaceSeparator(directory);
		if (!directory.endsWith(SEPARATOR))
		{ 
			directory += SEPARATOR;
		}
		return directory;
	}

	/**
	 * 在文件路径的前面加上文件路径分割符，主要用于相对路径变成绝对路径
	 * 
	 * @param directory
	 * @return String
	 */
	public static String addHeadSeparator(String directory)
	{
		if (directory == null || "".equals(directory.trim()))
			return "";
		directory = replaceSeparator(directory);
		if (!directory.startsWith(SEPARATOR))
		{
			directory = SEPARATOR + directory;
		}
		return directory;
	}

	/**
	 * 去掉前面的盘符
	 * 
	 * @param directory
	 * @return String
	 */
	public static String delHeadSeparator(String directory)
	{
		if (directory == null || "".equals(directory.trim()))
			return "";
		directory = replaceSeparator(directory);
		if (directory.startsWith(SEPARATOR))
		{
			directory = directory.substring(1);
		}
		return directory;
	}

	/**
	 * 去掉后面的盘符
	 * 
	 * @param directory
	 * @return String
	 */
	public static String delSeparator(String directory)
	{
		if (directory == null || "".equals(directory.trim()))
			return "";
		directory = replaceSeparator(directory);
		if (directory.endsWith(SEPARATOR))
		{
			directory = directory.substring(0, directory.length() - 1);
		}
		return directory;
	}
	
	public static String delHeadAndEndSeparator(String directory){
		if (directory == null || "".equals(directory.trim()))
			return "";
		directory = replaceSeparator(directory);
		if(directory.startsWith(SEPARATOR)){
			directory = directory.substring(1);
		}
		if(directory.endsWith(SEPARATOR)){
			directory = directory.substring(0, directory.length() - 1);
		}
		return directory;
		
	}

	public static String getRelativePath(String fullPath,String basePath){
		if(fullPath == null || fullPath.equals("")){
			return fullPath;
		}
		if(basePath == null || basePath.equals("")){
			return fullPath;
		}
		fullPath = replaceSeparator(fullPath);
		basePath = replaceSeparator(basePath);
		LogUtil.log.info("===========getRelativePath fullPath:" + fullPath + ", basePath:" + basePath );
		if(fullPath.indexOf(basePath) >= 0){
			return fullPath.substring(fullPath.indexOf(basePath)+basePath.length()); 
		}else{
			return fullPath;
		}
	}
	
	/**
	 * 从path中获得文件名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileNameFromPath(String filePath)
	{
		String result = StringUtils.substringAfterLast(formatDir(filePath),
				SEPARATOR);
		return StringUtils.isBlank(result) ? filePath : result;
	}

	/**
	 * 判断目录是否为空
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean isEmptyDir(String dir)
	{
		File f = new File(dir);
		File[] fs = f.listFiles();
		if (fs == null || fs.length == 0)
		{
			return true;
		}
		return false;
	}

	/**
	 * 格式化文件路径到网络盘符
	 * 
	 * @param filePath
	 * @return
	 */
	public static String formatDir(String filePath)
	{
		if (null == filePath)
		{
			return "";
		}

		return filePath.replace('\\', '/');
	}

	/**
	 * 格式化文件路径到windows盘符
	 * 
	 * @param filePath
	 * @return
	 */
	public static String formatDir2Win(String filePath)
	{
		if (null == filePath)
		{
			return "";
		}

		return filePath.replace('/', '\\');
	}

	/**
	 * 连接文件路径,只起到连接作用，目录两头不做盘符的修改
	 * 
	 * @param params
	 * @return a/b/c/ or /a/b/c/
	 */
	public static String joinPath(String... params)
	{
		if (params.length == 0)
			return "";
		String path = "";
		for (int i = 0; i < params.length; i++)
		{
			if (StringUtils.isBlank(params[i]))
				continue;
			if (StringUtils.isBlank(path))
				path = delSeparator(params[i]);
			else if (i < params.length - 1)
				path += addHeadSeparator(delSeparator(params[i]));
			else
				path += addHeadSeparator(params[i]);
		}
		return path;
	}

	/**
	 * 创建目录
	 * 
	 * @param path
	 */
	public static void mkdirs(String path)
	{
		File f = new File(path);
		if (!f.exists())
		{
			f.mkdirs();
		}
	}
	
	/**
	 * 创建目录
	 * 
	 * @param path
	 */
	public static void mkdirs4File(String filePath)
	{
		File p = new File(filePath).getParentFile();
		if (!p.exists())
		{
			p.mkdirs();
		}
	}

	/**
	 * 返回当前路径下面的文件或者目录
	 * 
	 * @param dir
	 * @return
	 */
	public static String[] getFileList(String dir)
	{
		String[] fileList = null;
		File f = new File(dir);
		if (!f.exists())
			fileList = null;
		else
		{
			fileList = f.list();
		}
		return fileList;
	}

	/*
	 * 返回转码源文件名前缀
	 */
	public static String getFilePrefix(String fileName){
		String[] strArray = fileName.split("_sc");
		return strArray[0];
	}
	
	/**
	 * 返回当前的主目录
	 * @param path
	 * @return
	 */
	public static String getSuperiorPath(String path){
		String resultPath = "";
		String[] tempArr = path.split(FilePathHelper.SEPARATOR);
		for(int i =0; i< tempArr.length -1; i++){
			resultPath += tempArr[i] + FilePathHelper.SEPARATOR;
		}
		return resultPath;
	}
	
	/**
	 * 递归获取文件列表
	 * @param file
	 * @param filter
	 * @param fileList
	 * @param containSubDir
	 */
	public static void loadFileList(File file , FileFilter filter , List<File> fileList, boolean containSubDir)
	{
		if (file == null || !file.exists() || !file.isDirectory() || fileList == null)
			return ;
		File[] fs = file.listFiles(filter);
		for (File f : fs)
		{
			if (f.isDirectory() && containSubDir)
				loadFileList(f, filter, fileList, containSubDir);
			else if (f.isFile())
				fileList.add(f);
		}
	}
	
	public static String getFilePostfix(String fileName,String defPostfix){
		String result = StringUtils.substringAfterLast(fileName,POINT);
		return StringUtils.isBlank(result) ? defPostfix : result;
	}
	
	public static Boolean isExist(String filePath){
		File file = new File(filePath);
		return file.exists();
	}
	
	public static String getHeadCharFromPath(String filePath){
		if(filePath != null && !filePath.equals("")){
			filePath = delHeadSeparator(filePath);
			String[] str = filePath.split(SEPARATOR);
			if(str.length > 0){
				return str[0];
			}
		}
		return "";
	}
	
	public static String delHeadCharFromPath(String filePath){
		if(filePath != null && !filePath.equals("")){
			filePath = delHeadSeparator(filePath);
			if(filePath.indexOf(SEPARATOR) > -1){
				return filePath.substring(filePath.indexOf(SEPARATOR));
			}
		}
		return "";
	}
	
	public static String replaceSeparator(String path) {
		return (path == null) ? null : path.replaceAll("\\\\", "/");
	}
	/**
	 * separator 变更方法
	 * @param path 当前路径
	 * @param model 当前变更模式 
	 * 				1 / -- replaceSeparator(String path, int model)
	 * @return 变更后的路径
	 */
	public static String replaceSeparator(String path, int model) {
		switch (model) {
		case 1:return (path == null) ? null : path.replace("/", File.separator);
		default:return (path == null) ? null : path.replaceAll("\\\\", "/");
		}
	}
	
	public static void main(String[] args)
	{
		System.out.println("1");
//		List<File> fileList = new ArrayList<File>();
////		
//		FilePathHelper.loadFileList(new File("E:/work"),null,fileList,true);
//		System.out.println(getFileList("E:/work"));
		String filePath = "/22/333/333/444/";
		System.out.println(delHeadCharFromPath(filePath));
		System.out.println(filePath);
	}
	
	public static String getFilePathDir(String filePath){
		if(filePath.contains(POINT)){
			filePath = filePath.substring(0, filePath.lastIndexOf(SEPARATOR));
		}
		return filePath;
	}

}
