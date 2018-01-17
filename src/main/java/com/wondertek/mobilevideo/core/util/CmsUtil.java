package com.wondertek.mobilevideo.core.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class CmsUtil {

	private static String[] imgExt = { "png", "gif", "jpg", "jpeg", "tif",
			"tiff", "bmp", "dib" };

	/** 发布文件夹数量最大值 */
	public static final int PUB_DIRNUM_MAX = 1000;
	public static final String PATH_SEPARATOR = "/";
	public static final String DEPOSITORY = "depository";
	public static String DEPOSITORY_PATH = "";
	/** 内容目录 */
	public final static String PUB_DIR_CONT = "cont";
	public final static String PUB_DIR_IMAGE = "image";
	public static ResourceBundle errorCodeResourceBundle = null;

	/** 把id转化成文件路径（不含文件名部分） */
	public static String idToPath(Long cont_Id) {
		int k = PUB_DIRNUM_MAX;
		return cont_Id / k / k + PATH_SEPARATOR + cont_Id / k % k;
	}

	/** 根据id获取文件名部分(不含路径，但需要自己补文件后缀) */
	public static String idToShortName(Long id) {
		int k = PUB_DIRNUM_MAX;
		return id % k + "";
	}

	/** 把id转化成文件全路径名（含文件名部分，但需要自己补文件后缀） */
	public static String idToName(Long cont_Id) {
		int k = PUB_DIRNUM_MAX;
		return cont_Id / k / k + PATH_SEPARATOR + cont_Id / k % k
				+ PATH_SEPARATOR + cont_Id % k;
	}

	/** 根据id拼装图片文件的全路径 */
	public static String idToFullPathImage(Long id, String type) {
		return DEPOSITORY_PATH + PATH_SEPARATOR + PUB_DIR_IMAGE
				+ PATH_SEPARATOR + idToName(id) + "." + type;
	}

	/** 根据id拼装内容文件的全路径 */
	public static String idToFullPathContent(Long id, String type) {
		return DEPOSITORY_PATH + PATH_SEPARATOR + PUB_DIR_CONT + PATH_SEPARATOR
				+ idToName(id) + "." + type;
	}

	/**根据文件路径解析id*/
	public static Long nameToId(String name) {
		if(name == null|| name.isEmpty()) return null;
		String nm = replaceSeparator(name);
		if (nm.startsWith("/"))
			nm = nm.substring(1);
		String[] ids = nm.split("/");
		if (ids == null || ids.length != 3)
			return null;
		int k = PUB_DIRNUM_MAX;
		return Long.valueOf(ids[0]) * k * k + Long.valueOf(ids[1]) * k
				+ Long.valueOf(ids[2]);
	}

	public static String replaceSeparator(String path) {
		return (path == null) ? null : path.replaceAll("\\\\", "/").replaceAll(
				"//", "/");
	}

	public static boolean isAvalidIdPath(String idPath) {
		if (idPath == null || "".equals(idPath)) {
			return false;
		}

		if (idPath.startsWith("/"))
			idPath = idPath.substring(1);

		String[] arryIds = idPath.split("/");
		for (int i = 0; i < arryIds.length; i++) {
			try {
				Long.parseLong(arryIds[i]);
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	public static String getErrorText(String key, Object[] args) {
		if (errorCodeResourceBundle == null)
			errorCodeResourceBundle = ResourceBundle.getBundle("errorCode",
					Locale.CHINA);

		if (errorCodeResourceBundle != null) {
			try {
				String result = errorCodeResourceBundle.getString(key);
				if (result != null)
					return MessageFormat.format(result, args);
				else
					return result;
			} catch (Exception e) {
				return key;
			}
		} else {

		}
		return "";
	}

	public static String getErrorText(String key) {
		return getErrorText(key, null);
	}

	public static Map<Object, Object> transObjectPropertiesToFieldsMap(
			Object obj) {
		Map<Object, Object> newFields = new HashMap<Object, Object>();
		Field[] contentfields = obj.getClass().getDeclaredFields();
		AccessibleObject.setAccessible(contentfields, true);
		for (Field contentfield : contentfields) {
			try {
				if (!"serialVersionUID".equals(contentfield.getName()))
					newFields.put(contentfield.getName().toUpperCase(),
							BeanUtils.getProperty(obj, contentfield.getName()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return newFields;
	}

	public static String getContentUrl(String contUrl, Long nodeId,
			Long dataObjectId, String nodeUrlPath) {
		return nodeUrlPath + "/n" + nodeId + "d" + dataObjectId + "c" + contUrl;
	}

	/** 对字符串数组中的数据按HashCode进行冒泡排序，并返回排过序的数组
	 * 
	 * @param array
	 * @return */
	public static String[] sort(String[] array) {
		String temp;
		for (int i = 0; i < array.length; ++i) {
			for (int j = 0; j < array.length - i - 1; ++j) {
				if (array[j].hashCode() > array[j + 1].hashCode()) {
					temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
				}
			}
		}
		return array;
	}

	/** 格式化数据
	 * 
	 * @param number 如：0.122323233
	 * @param pattern 如：“#0.0000”，
	 * @return 如：0.1223 */
	public static String formatNumber(float number, String pattern) {
		DecimalFormat df = new DecimalFormat("#0.0000");
		return df.format(number);
	}

	/** 对一个list集合随机取出size个进行随机不重复排序
	 * 
	 * @param ls
	 * @param size
	 * @return */

	public static List random(List ls, int size) {
		if (ls == null || ls.size() == 0)
			return null;
		int total = ls.size();
		if (total < size) {
			return ls;
		}
		List res = new ArrayList();
		Random rd = new Random();
		for (int i = 0; i < size; i++) {
			// 得到一个位置
			int r = rd.nextInt(total - i);
			// 得到那个位置的数值
			Object obj = ls.get(r);
			res.add(obj);
			// 将该位置的数字移除
			ls.remove(r);

		}
		return res;
	}

	// 版本规范是0~999.0~999.0~999.0~999
	public static boolean checkVersion(String version) {
		if (version == null
				|| "".equals(version)
				|| (version.split("\\.").length != 3 && version.split("\\.").length != 4)) {
			return false;
		}
		try {
			// 之前的 版本都是两个点。现在改为3个点。
			if (version.split("\\.").length == 3) {
				version = version + ".0";
			}
			String[] numbers = version.split("\\.");
			for (int i = 0; i < numbers.length; i++) {
				Long num = Long.parseLong(numbers[i]);
				if (num > 999L || num < 0L)
					return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static Long versionToLong(String version) {
		if (!checkVersion(version))
			return 0L;
		String[] numbers = version.split("\\.");
		return Long.parseLong(numbers[0]) * 1000000
				+ Long.parseLong(numbers[1]) * 1000
				+ Long.parseLong(numbers[2]);
	}

	public static String getFileType(String name) {
		String type = "";
		if (StringUtil.isNullStr(name) == false
				&& StringUtil.null2Str(name).endsWith(".") == false
				&& StringUtil.null2Str(name).lastIndexOf(".") > 0) {
			type = name.substring(name.lastIndexOf(".") + 1);
		}
		return type;
	}

	public static boolean existsIgnoreCase(List<String> list, String name) {
		boolean exists = false;
		if (list != null && list.size() > 0) {
			for (String str : list) {
				if (str.equalsIgnoreCase(name)) {
					exists = true;
				}
			}
		}
		return exists;
	}

	public static void removeIgnoreCase(List<String> list, String name) {
		if (list != null && list.size() > 0) {
			for (String str : list) {
				if (str.equalsIgnoreCase(name)) {
					list.remove(str);
				}
			}
		}
	}

	public static List<String> strToList(String str, String split) {
		List<String> list = new ArrayList<String>();
		if (str != null && !"".equals(str)) {
			String[] arr = str.split(",");
			if (arr != null && arr.length > 0) {
				for (String tmp : arr) {
					if (tmp != null && !"".equals(tmp)) {
						list.add(tmp);
					}
				}
			}
		}
		return list;
	}

	/** 校验日期类型栏目 yyyyMMdd
	 * @param strData
	 * @return */
	public static boolean isAvalidStringData(String strData) {
		if (strData == null || strData.length() != 8)
			return false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			sdf.parse(strData);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public static String strXMLFormat(String strXML) {
		StringWriter out = new StringWriter();
		try {
			SAXReader reader = new SAXReader();
			StringReader sReader = new StringReader(strXML);
			Document doc = reader.read(sReader);
			OutputFormat formater = OutputFormat.createPrettyPrint();
			formater.setEncoding("utf-8");
			XMLWriter writer = new XMLWriter(out, formater);
			writer.write(doc);
			strXML = out.toString();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return strXML;
	}

	public static boolean isFileAccept(File file) {
		for (int i = 0; i < imgExt.length; i++) {
			if (file.getName().endsWith(imgExt[i])) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static String getHSSFCellValue(HSSFCell cell) {
		String value = new String();
		try {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				value = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					if (date != null) {
						value = DateUtil.formatDate(DateUtil.DATE_TIME_PATTERN,
								date);
					} else {
						value = "";
					}
				} else {
					value = new DecimalFormat("0").format(cell
							.getNumericCellValue());
				}
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				if (!cell.getStringCellValue().equals("")) {
					value = cell.getStringCellValue();
				} else {
					value = cell.getNumericCellValue() + "";
				}
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				value = "";
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				value = (cell.getBooleanCellValue() == true ? "Y" : "N");
				break;
			default:
				value = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return StringUtil.nullToString(value).trim();
	}

	/** 根据内容ID获得内容的三级存储目录，前后都带"/",如果内容ID不足10为前面补0
	 * @param contentID
	 * @return */
	public static String getContentThreeDir(String contID) {
		contID = StringUtils.leftPad(contID, 10, '0');
		return "/" + contID.substring(0, contID.length() - 6) + "/"
				+ contID.substring(contID.length() - 6, contID.length() - 3)
				+ "/" + contID.substring(contID.length() - 3, contID.length())
				+ "/";
	}

	/** 将RMB单位从分改成元
	 * @param yuan
	 * @return */
	public static float changeCompany(Object fen) {
		if (fen == null) {
			return 0;
		}
		float f = Float.valueOf(fen.toString());
		float ticketV = f / 100;
		return ticketV;
	}

	public static List<String> stringToList(String value, String regexChar) {
		List<String> list = new ArrayList<String>();
		if (StringUtil.isNullStr(value) == true
				|| StringUtil.isNullStr(regexChar) == true) {
			return list;
		} else {
			String[] values = value.split(regexChar);
			for (int i = 0; i < values.length; i++) {
				list.add(values[i]);
			}
		}
		return list;
	}

	public static List<Long> stringToLongList(String value, String regexChar) {
		List<Long> list = new ArrayList<Long>();
		if (StringUtil.isNullStr(value) == true
				|| StringUtil.isNullStr(regexChar) == true) {
			return list;
		} else {
			String[] values = value.split(regexChar);
			for (int i = 0; i < values.length; i++) {
				if (StringUtil.isNumber(values[i])) {
					list.add(Long.valueOf(values[i]));
				}
			}
		}
		return list;
	}

	public static List<Long> bigListToLongList(List<Object> objectList) {
		List<Long> idList = new ArrayList<Long>();
		if (objectList != null && objectList.size() > 0) {
			for (Object obj : objectList) {
				if (obj instanceof BigDecimal) {
					idList.add(((BigDecimal) obj).longValue());
				} else if (obj instanceof BigInteger) {
					idList.add(((BigInteger) obj).longValue());
				} else {
					idList.add((Long) obj);
				}
			}
		}
		return idList;
	}

	public static Long objectToLong(Object obj) {
		if (obj instanceof BigDecimal) {
			return ((BigDecimal) obj).longValue();
		} else if (obj instanceof Number) {
			return ((Number) obj).longValue();
		} else if (obj instanceof BigInteger) {
			return ((BigInteger) obj).longValue();
		} else {
			return (Long) obj;
		}
	}
	
	public static boolean isContainIllegalCharacter(String filePath, String illegalCharacter){
		if(StringUtil.isNullStr(illegalCharacter) == false){
			String fileContent = FileIO.readFile(filePath);
			Pattern urlExcludePattern = Pattern.compile(illegalCharacter);
			if(StringUtil.isNullStr(fileContent) == false && urlExcludePattern != null){
				Matcher m = urlExcludePattern.matcher(fileContent);  
				if(m.find()) return true;
			}
		}
		return false;
	}
	
	/** 根据华为内容id和cpid获取pom的图片路径 */
	public static String idToPomsImagePath(String pContId, String cpId) {
		return PATH_SEPARATOR + "zhengshi" + CmsUtil.getContentThreeDir(pContId) + pContId
				+ PATH_SEPARATOR + "display" + PATH_SEPARATOR + cpId + "_H32_sc.jpg";
	}
}
