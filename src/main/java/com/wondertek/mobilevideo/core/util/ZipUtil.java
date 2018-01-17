package com.wondertek.mobilevideo.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

 

/**
 * 类说明: zip解压缩类
 *
 * @author ysk E-mail:libra_ysk@hotmail.com
 * @version 创建时间：2008-12-8 下午04:12:54
 * 
 */
public class ZipUtil {

        /**
         * zip压缩功能测试. 
         *
         * @param srcDir 所要压缩的目录名（包含绝对路径）
         * @param zipFileName 压缩后的文件名
         * @throws Exception
         */
        public static void createZip(String srcDir, String zipFileName) throws Exception {
                File folderObject = new File(srcDir);
            if (folderObject.exists()){
                List<File> fileList = getSubFiles(new File(srcDir));
                //压缩文件名
                ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName));
                ZipEntry ze = null;
                byte[] buf = new byte[1024];
                int readLen = 0;
                for (int i = 0; i < fileList.size(); i++) {
                    File f = (File) fileList.get(i);
                    //创建一个ZipEntry，并设置Name和其它的一些属性
                    ze = new ZipEntry(getFileNameInZip(srcDir, f));
                    ze.setSize(f.length());
                    ze.setTime(f.lastModified());
                    //将ZipEntry加到zos中，再写入实际的文件内容
                    zos.putNextEntry(ze);
                    InputStream is = new BufferedInputStream(new FileInputStream(f));
                    while ((readLen = is.read(buf, 0, 1024)) != -1) {
                            zos.write(buf, 0, readLen);
                    }
                    is.close();
                }
                zos.close();
            }else{
                throw new Exception("this folder isnot exist!");
            }
        }

        /**
         * 将多个文件增加到压缩文件
         * @param filePaths
         * @param zipFileName
         * @throws Exception
         */
        public static void createZipByFileList(String[] filePaths, String zipFileName) throws Exception {
          if(null != filePaths && filePaths.length>0){
        	  File tf = new File(zipFileName);
        	  if(!tf.exists())
        		  FileUploadUtil.createFolder(tf.getParent());
        	  ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName));
              ZipEntry ze = null;
              byte[] buf = new byte[1024];
              int readLen = 0;
        	  for (String path : filePaths) {
        		  File file = new File(path);
        		  if (file.exists()){
	                  //创建一个ZipEntry，并设置Name和其它的一些属性
	                  ze = new ZipEntry(file.getName());
	                  ze.setSize(file.length());
	                  ze.setTime(file.lastModified());
	                  //将ZipEntry加到zos中，再写入实际的文件内容
	                  zos.putNextEntry(ze);
	                  InputStream is = new BufferedInputStream(new FileInputStream(file));
	                  while ((readLen = is.read(buf, 0, 1024)) != -1) {
	                          zos.write(buf, 0, readLen);
	                  }
	                  is.close();
        		  }
              }
              zos.close();
          }
        }
        /**
         * 测试解压缩功能. 
         *
         * @param sourceZip
         * 		zip文件名
         * @param outputDir
         * 		输出目录
         * @throws IOException
         */
        @SuppressWarnings("unchecked")
		public static void UnzipToDir(String sourceZip, String outputDir)
                        throws IOException{
			  ZipFile zfile=new ZipFile(sourceZip);
			  try
			  {
				  Enumeration zList=zfile.entries();
				  ZipEntry ze=null;
				  byte[] buf=new byte[1024];
				  while(zList.hasMoreElements()){
					  //从ZipFile中得到一个ZipEntry
					  ze=(ZipEntry)zList.nextElement();
					  if(ze.isDirectory()){
						  continue;
					  }
					  //以ZipEntry为参数得到一个InputStream，并写到OutputStream中
					  OutputStream os=new BufferedOutputStream(new FileOutputStream(getRealFileName(outputDir, ze.getName())));
					  InputStream is=new BufferedInputStream(zfile.getInputStream(ze));
					  int readLen=0;
					  while ((readLen=is.read(buf, 0, 1024))!=-1) {
						  os.write(buf, 0, readLen);
					  }
					  is.close();
					  os.close();
				  }
			  }finally
			  {
				  zfile.close();
			  }
        }

        /**
         * 取得指定目录下的所有文件列表，包括子目录.
         *
         * @param dir
         *            File 指定的目录
         * @return 包含java.io.File的List
         */
        private static List<File> getSubFiles(File dir) {
                List<File> ret = new ArrayList<File>();
            File[] tmp = dir.listFiles();
            for (int i = 0; i < tmp.length; i++) {
                    if (tmp[i].isFile()) {
                            ret.add(tmp[i]);
                    }
                    if (tmp[i].isDirectory()) {
                            ret.addAll(getSubFiles(tmp[i]));
                    }
            }
            return ret;
        }

        /**
         * 给定根目录，返回一个zip中的文件路径所对应的实际文件名.
         *
         * @param dir
         *            指定根目录
         * @param fileNameInZip
         *            相对路径名，来自于ZipEntry中的name
         * @return java.io.File 实际的文件
         */
        private static File getRealFileName(String dir, String fileNameInZip) {
            String[] dirs = fileNameInZip.split("/");
            File ret = new File(dir);
            if (dirs.length > 1) {
                for (int i = 0; i < dirs.length - 1; i++) {
                        ret = new File(ret, dirs[i]);
                }
            }
            if (!ret.exists()) {
                ret.mkdirs();
            }
            ret = new File(ret, dirs[dirs.length - 1]);
            return ret;
        }

        /**
         * 给定根目录，返回文件名的在zip中的路径
         *
         * @param dir
         *            java.lang.String 根目录
         * @param realFileName
         *            java.io.File 实际的文件名
         * @return 相对文件名
         */
        private static String getFileNameInZip(String dir, File realFileName) {
            File real = realFileName;
            File base = new File(dir);
            String ret = real.getName();
            while (true) {
                real = real.getParentFile();
                if (real == null)
                        break;
                if (real.equals(base))
                        break;
                else {
                        ret = real.getName() + "/" + ret;
                }
            }
            return ret;
        }
}
