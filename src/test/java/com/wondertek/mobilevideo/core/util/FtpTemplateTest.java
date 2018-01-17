package com.wondertek.mobilevideo.core.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.wondertek.mobilevideo.core.util.ftp.FtpFileVo;
import com.wondertek.mobilevideo.core.util.ftp.FtpServer;
import com.wondertek.mobilevideo.core.util.ftp.FtpTemplate;

public class FtpTemplateTest {
	private String ftpIp =  "211.136.119.80";
	private String ftpUser =  "cp818818";
	private String ftpPwd = "cp818818";
	private String ftpUrl = "/";
	
	public FtpServer ftpServer;
	public FtpServer getFtpServer(){
		if(ftpServer == null){
//			ftpIp = "192.168.1.133";
//			ftpUser =  "oms";
//			ftpPwd = "oms";
//			ftpUrl = "/bc/";
			
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
	public void testFtpUploadOrDel(){
		FtpServer ftpServer=this.getFtpServer();
		try {
			String[] filePaths = new String[2];
			filePaths[0] = "cc.zip";
			filePaths[1] = "/liujun2/cc.zip";


			String path = filePaths[1];
			String fullPath = "C:/sync/syncContent";
			FtpTemplate.upLoadOrDelFile(ftpServer, "C:/", filePaths, false);
			//FtpTemplate.downloadFile(ftpServer, "/liujun", "cc.zip", fullPath, true);
		} catch (CmsException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	@Ignore
	public void testDownLoadFilesByNames(){
		int count = 0;
		try {
			FtpServer ftpServer=this.getFtpServer();
			
			List<String> fileNames = new ArrayList<String>();
			fileNames.add("temp_692988.xml");
			fileNames.add("temp_726060.xml");
			while(count < 10){
				List<FtpFileVo> fileLists = FtpTemplate.downLoadFilesByNames(ftpServer,"/queryBcList/","d:/tmp/",fileNames,false,false,false);
				if(fileLists != null && !fileLists.isEmpty()){
					for (FtpFileVo ftpFileVo : fileLists) {
						System.out.println(ftpFileVo.getFileName());
					}
				}
				count++;
				System.out.println(count);
			}
		} catch (CmsException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(count);
	}
	
	@Test
	@Ignore
	public void testListNames(){
		int count = 0;
		try {
			FtpServer ftpServer=this.getFtpServer();
			while(count < 10){
				List<String> fileLists = FtpTemplate.listNames(ftpServer, "/", null, null);
				if(fileLists != null && !fileLists.isEmpty()){
					for (String ftpFile : fileLists) {
						System.out.println(ftpFile);
					}
				}
				count++;
				System.out.println(count);
			}
		} catch (CmsException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(count);
	}
}
