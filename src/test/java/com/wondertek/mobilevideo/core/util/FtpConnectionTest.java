package com.wondertek.mobilevideo.core.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import com.wondertek.mobilevideo.core.util.ftp.FtpConnection;
import com.wondertek.mobilevideo.core.util.ftp.FtpPoolManager;
import com.wondertek.mobilevideo.core.util.ftp.FtpServer;
import com.wondertek.mobilevideo.core.util.ftp.FtpTemplate;


public class FtpConnectionTest {
	private String ftpIp =  "211.136.119.80";
	private String ftpUser =  "cp818818";
	private String ftpPwd = "cp818818";
	private String ftpUrl = "/";
	
	public FtpServer ftpServer;
	public FtpServer getFtpServer(){
		if(ftpServer == null){
			ftpIp = "192.168.1.133";
			ftpUser =  "oms";
			ftpPwd = "oms";
			ftpUrl = "/bc/";
			
			ftpServer = new FtpServer();
			ftpServer.setFtpMark("OMS");
			ftpServer.setFtpMode(FtpServer.FTP_MODE_FTP);
			ftpServer.setIp(ftpIp);
			ftpServer.setFtpuser(ftpUser);
			ftpServer.setFtppasswd(ftpPwd);
			ftpServer.setFtpurl(ftpUrl);
			ftpServer.setPort(21L);
		}
		return ftpServer;
	}
	
	@Test
	@Ignore
	public void testIsExistPath(){
		FtpConnection ftpConnection = null;
		try {
			Date finishDate = DateUtil.parseDate(DateUtil.DATE_TIME_NO_SPACE_PATTERN, "20150331154100");
			while(new Date().after(finishDate)){
				ftpConnection = FtpTemplate.getConnection(getFtpServer());
				String[] filePaths = new String[2];
				String filePath = "d:/tmp/test/Test.html";
				filePaths[0] = CmsUtil.replaceSeparator(filePath);
				filePaths[1] = CmsUtil.replaceSeparator("/test1/test2/test3/test4/Test1.html");
			
				ftpConnection.isExistPath(getFtpServer(), "/test/", filePaths);
				
				ftpConnection.upLoadFile(new File(filePath));
				filePath = "d:/tmp/test/allclasses-frame.html";
				ftpConnection.upLoadFile(new File(filePath));
				filePath = "d:/tmp/test/constant-values.html";
				ftpConnection.upLoadFile(new File(filePath));
				filePath = "d:/tmp/test/deprecated-list.html";
				ftpConnection.upLoadFile(new File(filePath));
				filePath = "d:/tmp/test/help-doc.html";
				ftpConnection.upLoadFile(new File(filePath));
				filePath = "d:/tmp/test/index.html";
				ftpConnection.upLoadFile(new File(filePath));
				filePath = "d:/tmp/test/index-all.html";
				ftpConnection.upLoadFile(new File(filePath));
				break;
			}
		} catch (CmsException e) {
		} catch (IOException e) {
		}finally{
			if(ftpConnection != null){
				if(ftpConnection != null){
					FtpPoolManager.freeConnection(ftpServer, ftpConnection);
				}
			}
		}
	}
	
	@Test
	@Ignore
	public void testListFTPFiles(){
		FtpConnection ftpConnection = null;
		try {
			Date finishDate = DateUtil.parseDate(DateUtil.DATE_TIME_NO_SPACE_PATTERN, "20150331154100");
			while(new Date().after(finishDate)){
				ftpConnection = FtpTemplate.getConnection(getFtpServer());
				
				String ftpPath = "/test/test1/test2/test3/test4/";
				ftpConnection.setWorkingDirectory(ftpConnection.getDefaultHomePath());
				String userHomePath = ftpServer.getFtpurl();
				if(!"/".equals(userHomePath)){
					userHomePath = CmsUtil.replaceSeparator(userHomePath);
					if (userHomePath.startsWith("/"))
						userHomePath = userHomePath.substring(1);				
					if(!ftpConnection.setWorkingDirectory(userHomePath))
						throw new CmsException("ftp path not found:ftpPath:"+userHomePath);
				}
				//进入指定目录
				if(!"/".equals(ftpPath)){
					ftpPath = CmsUtil.replaceSeparator(ftpPath);
					if (ftpPath.startsWith("/"))
						ftpPath = ftpPath.substring(1);				
					if(!ftpConnection.setWorkingDirectory(ftpPath))
						throw new CmsException("ftp path not found:ftpPath:"+ftpPath);
				}
				FileFilter filter = new FileFilter(){
					@Override
					public boolean accept(File file) {
						if(file.getName().endsWith(".html")){
							return true;
						}
						return false;
					}
				};
				String[] files = ftpConnection.listFTPFiles(filter);
				if(files != null){
					for(String file: files){
						System.out.println(file);
					}
				}
				System.out.println("END1");
				ftpConnection.setWorkingDirectory(ftpConnection.getDefaultHomePath());
				if(!"/".equals(userHomePath)){
					userHomePath = CmsUtil.replaceSeparator(userHomePath);
					if (userHomePath.startsWith("/"))
						userHomePath = userHomePath.substring(1);				
					if(!ftpConnection.setWorkingDirectory(userHomePath))
						throw new CmsException("ftp path not found:ftpPath:"+userHomePath);
				}
				files = ftpConnection.listFTPFiles("/bc/test/test1/test2/test3/test4/", filter);
				if(files != null){
					for(String file: files){
						System.out.println(file);
					}
				}
				System.out.println("END2");
				break;
			}
		} catch (CmsException e) {
		} catch (IOException e) {
		}finally{
			if(ftpConnection != null){
				if(ftpConnection != null){
					FtpPoolManager.freeConnection(ftpServer, ftpConnection);
				}
			}
		}
	}
	
	@Test
	@Ignore
	public void testDownLoadFile() throws Exception{
		FtpConnection ftpConnection = null;
		try {
			Date finishDate = DateUtil.parseDate(DateUtil.DATE_TIME_NO_SPACE_PATTERN, "20150331154100");
			while(new Date().after(finishDate)){
				ftpConnection = FtpTemplate.getConnection(getFtpServer());
				
				String ftpPath = "/test/test1/test2/test3/test4/";
				ftpConnection.setWorkingDirectory(ftpConnection.getDefaultHomePath());
				String userHomePath = ftpServer.getFtpurl();
				if(!"/".equals(userHomePath)){
					userHomePath = CmsUtil.replaceSeparator(userHomePath);
					if (userHomePath.startsWith("/"))
						userHomePath = userHomePath.substring(1);				
					if(!ftpConnection.setWorkingDirectory(userHomePath))
						throw new CmsException("ftp path not found:ftpPath:"+userHomePath);
				}
				//进入指定目录
				if(!"/".equals(ftpPath)){
					ftpPath = CmsUtil.replaceSeparator(ftpPath);
					if (ftpPath.startsWith("/"))
						ftpPath = ftpPath.substring(1);				
					if(!ftpConnection.setWorkingDirectory(ftpPath))
						throw new CmsException("ftp path not found:ftpPath:"+ftpPath);
				}
				ftpConnection.downLoadFile("Test.html", "d:/tmp/test/download/", "T_", false, true);
				ftpConnection.downLoadFile("allclasses-frame.html", "d:/tmp/test/download/", "T_", false, true);
				ftpConnection.downLoadFile("constant-values", "d:/tmp/test/download/", "T_", true, true);
				ftpConnection.downLoadFile("deprecated-list.html", "d:/tmp/test/download/", "T_", true, true);
				break;
			}
		} catch (CmsException e) {
		} catch (IOException e) {
		}finally{
			if(ftpConnection != null){
				if(ftpConnection != null){
					FtpPoolManager.freeConnection(ftpServer, ftpConnection);
				}
			}
		}
	}
	
	@Test
	@Ignore
	public void test(){
		ThreadPool threadPool = new ThreadPool("T_1",5);
		for(int i = 0; i < 10; i++){
			threadPool.execute(createTask());
		}
		threadPool.waitFinish();
		threadPool.closePool();
	}
	private Runnable createTask() {
		return new Runnable(){
			@Override
			public void run() {
				testIsExistPath();
			}
		};
	}
}
