package com.wondertek.mobilevideo.core.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

//import com.longtop.ecommerce.bo.Organization;

/**
 * 从FTP读取文件
 * 
 * @author tony
 * 
 */
public class FtpUtils {

	private FTPClient ftpClient;
	private String fileName, strencoding;
	private int columns, rowCount;
	private String ip = "192.168.1.133"; // 服务器IP地址
	private String userName = "oms"; // 用户名
	private String userPwd = "oms"; // 密码
	private int port = 21; // 端口号
	/**
	 * init ftp servere
	 */
	public FtpUtils() {
		this.reSet();
	}

	public void reSet() {
		// 以当前系统时间拼接文件名
		columns = 0;
		rowCount = 0;
		strencoding = "GBK";
		this.connectServer(ip, port, userName, userPwd);
	}

	/**
	 * 以当前系统时间生成文件名
	 * 
	 * @return
	 */
	private String getFileName() {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
		String str = "";
		try {
			str = sdFormat.format(new Date());
		} catch (Exception e) {
			return "";
		}
		if (str.equals("1900-01-01")) {
			str = "";
		}

		return str;
	}

	/**
	 * @param ip
	 * @param port
	 * @param userName
	 * @param userPwd
	 * @param path
	 * @throws SocketException
	 * @throws IOException
	 *             function:连接到服务器
	 */
	public void connectServer(String ip, int port, String userName, String userPwd) {
		ftpClient = new FTPClient();
		try {
			// 连接
			ftpClient.connect(ip, port);
			// 登录
			ftpClient.login(userName, userPwd);
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setWorkDir(String path){
		if (path != null && path.length() > 0) {
			// 跳转到指定目录
			try {
				ftpClient.changeWorkingDirectory(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @throws IOException
	 *             function:关闭连接
	 */
	public void closeServer() {
		if (ftpClient.isConnected()) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param path
	 * @return function:读取指定目录下的文件名
	 * @throws IOException
	 */
	public List<String> getFileList(String path) {
		List<String> fileLists = new ArrayList<String>();
		// 获得指定目录下所有文件名
		FTPFile[] ftpFiles = null;
		try {
			ftpFiles = ftpClient.listFiles(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
			FTPFile file = ftpFiles[i];
			if (file.isFile()) {
				fileLists.add(file.getName());
			}
		}
		return fileLists;
	}

	/**
	 * @param fileName
	 * @param sourceFile
	 * @return
	 * @throws IOException
	 *             function:下载文件
	 */
	public boolean unloadFile(String fileName, String sourceFile) {
		boolean flag = false;
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			flag = ftpClient.retrieveFile(sourceFile, fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 返回一个文件流
	 * 
	 * @param fileName
	 * @return
	 */
	public String readFile(String fileName) {
		String result = "";
		InputStream ins = null;
		try {
			ins = ftpClient.retrieveFileStream(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
			String inLine = reader.readLine();
			while (inLine != null) {
				result += (inLine + System.getProperty("line.separator"));
				inLine = reader.readLine();
			}
			reader.close();
			if (ins != null) {
				ins.close();
			}

			// 主动调用一次getReply()把接下来的226消费掉. 这样做是可以解决这个返回null问题
			ftpClient.getReply();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @param fileName
	 * @return function:从服务器上读取指定的文件
	 * @throws ParseException
	 * @throws IOException
	 */
	public List readFile() throws ParseException {

		// List<Organization> contentList = new ArrayList<Organization>();
		List<String> contentList = new ArrayList<String>();
		InputStream ins = null;
		try {
			// 从服务器上读取指定的文件
			ins = ftpClient.retrieveFileStream(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(ins, strencoding));

			String inLine = reader.readLine();

			while (inLine != null) {
				if (inLine.length() + 1 > columns)
					columns = inLine.length() + 1;
				String beanStr = inLine + System.getProperty("line.separator");
				if (beanStr.indexOf(",") > 0) {
					String[] beanStrs = beanStr.split(",");
					// Organization org = new Organization();
					DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
					Date date = null;
					date = format1.parse(beanStrs[0].substring(1, beanStrs[0].length() - 1));
					// org.setBuildDate(date);
					String parentId = null;
					if ((beanStrs[1].substring(1, beanStrs[1].length() - 1)).equals("")) {
						parentId = "00";// 默认值，表示顶级机构
						// org.setOrgLevel("00");//机构级别为00
					} else
						parentId = (beanStrs[1].substring(1, beanStrs[1].length() - 1));
					// org.setParentId(parentId);//去掉引号
					// org.setOrgCode(beanStrs[2].substring(1,
					// beanStrs[2].length()-1));
					// org.setOrgBrief(beanStrs[3].substring(1,
					// beanStrs[3].length()-1));
					if (beanStrs[4].length() > 3) {
						// org.setOrgName(beanStrs[4].substring(1,
						// beanStrs[4].length()-3));
					}
					contentList.add("");
				}

				inLine = reader.readLine();
				rowCount++;
			}
			reader.close();
			if (ins != null) {
				ins.close();
			}
			// 主动调用一次getReply()把接下来的226消费掉. 这样做是可以解决这个返回null问题
			ftpClient.getReply();
			System.out.println("此次任务一共读取[" + contentList.size() + "]条数据记录");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentList;
	}

	/**
	 * @param fileName
	 *            function:删除文件
	 */
	public void deleteFile(String fileName) {
		try {
			ftpClient.deleteFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		
		String path = "/200000/node/publish/"; // 读取文件的存放目录
		
		FtpUtils ftp = new FtpUtils();
//		ftp.setWorkDir(path);
//		ftp.readFile();
		List<String> fileList = ftp.getFileList(path);
		for (String fileName : fileList) {
			System.out.println("====file name : "+fileName+" ====start=");
			System.out.println(ftp.readFile(fileName));
			System.out.println("====file name : "+fileName+" ====end=");
			
		}
		/*
		 * ftp.unloadFile("D:\\test_t_department.txt",
		 * "t_department_20110623.txt"); List<String> files =
		 * ftp.getFileList(path); for(int i = 0 ; i < files.size() ; i++){
		 * String fileName = files.get(i); ftp.unloadFile("D:\\test",
		 * "t_department_20110623.txt"); System.out.println(fileName); String
		 * result = ftp.readFile("t_department_20110623.txt");
		 * System.out.println(result); ftp.deleteFile(fileName); }
		 */

	}
}
