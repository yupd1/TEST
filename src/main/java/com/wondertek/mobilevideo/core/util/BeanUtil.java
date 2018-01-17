package com.wondertek.mobilevideo.core.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class BeanUtil {
	private final static Log	log	= LogFactory.getLog(BeanUtil.class);

	@SuppressWarnings("rawtypes")
	public static Map<String, Object> bean2Map(Object o, Class c) {
		Field[] fields = c.getDeclaredFields();
		Map<String, Object> map = new HashMap<String, Object>();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				map.put(field.getName(), field.get(o));
			} catch (IllegalArgumentException e) {
				log.error("class:" + c.getName() + "property:" + field.getName() + "set. error occar");
			} catch (IllegalAccessException e) {
				log.error("class:" + c.getName() + "property:" + field.getName() + "set. error occar");
			}
		}
		return map;
	}

	// 通过MAP解析出对象
	@SuppressWarnings("rawtypes")
	public static Object map2Bean(Object o, Class c, Map propertyMap) throws IntrospectionException {
		BeanInfo beanInfo = Introspector.getBeanInfo(c);
		for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
			String propertyName = pd.getName();
			if (propertyMap.containsKey(propertyName)) {
				try {
					pd.getWriteMethod().invoke(o, propertyMap.get(pd.getName()));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return o;
	}

	/**
	 * 将单个标准的JavaBean转换为xml,转换后的格式为
	 *  <beanClassName>
	 * 		<attr1Name><![CDATA[attr1Value]]>
	 * 		</attr1Name>
	 * 		 ...... 
	 * </beanClassName>
	 * 
	 * @param bean
	 * @return
	 */
	public static String bean2Xml(Object bean) {
		StringBuffer sb = new StringBuffer();
		String valueTmp = "<![CDATA[%s]]>";
		String elementTmp = "<%s>%s</%s>";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:MM:ss:SSS");
		log.info("begin convert bean to xml ["+(new Date()).getTime()+"]......");
		if (null != bean) {
			log.debug("get class name ==> " + bean.getClass().getSimpleName());
			sb.append("<" + bean.getClass().getSimpleName() + ">");
			Field[] fds = bean.getClass().getDeclaredFields();
			for (Field fd : fds) {
				//log.debug("get bean attr name ==> " + fd.getName());
				try {
					String m_name = "get" + fd.getName().substring(0, 1).toUpperCase() + fd.getName().substring(1);
					//log.debug("get method ==> " + m_name);
					Method m = bean.getClass().getMethod(m_name);
					Object o = m.invoke(bean);
				//	log.debug("invoked method [" + m_name + "]......");
					if (null == o) {
						//log.debug("invoked method["+m.getName()+"], get return Object is null  ......");
						continue;
					}
					if (fd.getType().equals(Long.class) || fd.getType().equals(Integer.class) || fd.getType().equals(Double.class) || fd.getType().equals(Float.class)) {
						// 数字类型
						sb.append(String.format(elementTmp, fd.getName(), o.toString(), fd.getName()));
					} else if (fd.getType().equals(Date.class)) {
						// 日期类型
						Date date = (Date) o;
						sb.append(String.format(elementTmp, fd.getName(), sdf.format(date), fd.getName()));
					}else if (fd.getType().equals(String.class)||fd.getType().equals(Boolean.class)){
						sb.append(String.format(elementTmp, fd.getName(), String.format(valueTmp, o.toString()), fd.getName()));
					}
				} catch (Exception e) {
				}
			}
			sb.append("</" + bean.getClass().getSimpleName() + ">");
		}
		log.info("end convert bean to xml ["+(new Date()).getTime()+"]");
		return sb.toString();
	}

	/**
	 * 将多个标准的JavaBean转换为xml,转换后的格式为
	 *  <root>
	 *   <beanClassName>
	 * 		<attr1Name><![CDATA[attr1Value]]></attr1Name>
	 * 		 ......
	 * 	 </beanClassName>
	 * 	......
	 * </root>
	 * 
	 * @param bean
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String beanList2Xml(List beanList) {
		StringBuffer sb = new StringBuffer();
		log.info("start convert beanList2XML ["+(new Date()).getTime()+"]......");
		if (null != beanList && beanList.size() > 0) {
			sb.append("<root>");
			for (Object o : beanList) {
				sb.append(bean2Xml(o));
			}
			sb.append("</root>");
		}
		log.info("end convert beanList2XML ["+(new Date()).getTime()+"]");
		return sb.toString();
	}

	/**
	 * 将通过bean2Xml生成的xml数据反向成bean
	 * 
	 * @param xmlStr
	 * @param clazz
	 *            反向Bean的class,这个bean,必须带有空参数的构造函数
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object xml2Bean(String xmlStr, Class clazz) {
		Object obj = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:MM:ss:SSS");
		log.info("start parse xml to bean ["+(new Date()).getTime()+"]......");
		try {
			Document doc = DocumentHelper.parseText(xmlStr);
			Element root = doc.getRootElement();
			log.debug("parse xml from String ,get root ==> " + root.getName());
			obj = clazz.newInstance();
			List<Element> attrEles = root.elements();
			Map<String, Method> setMdMap = new HashMap<String, Method>();
			for (Method method : clazz.getMethods()) {
				if (method.getName().startsWith("set") && method.getParameterTypes().length == 1) {
					setMdMap.put(method.getName(), method);
					//log.debug("find set methods ==> " + method.getName() +" param type ==> "+method.getParameterTypes()[0].getSimpleName());
				}
			}
			for (Element e : attrEles) {
				String attrName = e.getName();
				String methodName = "set" + attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
				Method m = setMdMap.get(methodName);
				String value = e.getTextTrim();
				//log.debug("find bean attr name ==> " + attrName + " with value ==> " + value);
				if (StringUtils.isBlank(value))
					continue;
				Object paramType = m.getParameterTypes()[0];
				if (paramType.equals(Long.class))
					m.invoke(obj, Long.parseLong(value));
				if (paramType.equals(Integer.class))
					m.invoke(obj, Integer.parseInt(value));
				if (paramType.equals(Double.class))
					m.invoke(obj, Double.parseDouble(value));
				if (paramType.equals(Float.class))
					m.invoke(obj, Float.parseFloat(value));
				if (paramType.equals(Boolean.class))
					m.invoke(obj, new Boolean(value));
				if (paramType.equals(Date.class))
					m.invoke(obj, sdf.parse(value));
				if (paramType.equals(String.class))
					m.invoke(obj, value);
				log.debug("invoke ==> " + methodName + " with value ==> " + value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("end parse xml to bean ["+(new Date()).getTime()+"]");
		return obj;
	}

	/**
	 * 将通过beanList2Xml生成的xml数据反向成bean的List
	 * 
	 * @param xmlStr
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List xml2BeanList(String xmlStr, Class clazz) {
		log.info("start parse xml to bean list["+(new Date()).getTime()+"]......");
		List objList = new ArrayList();
		try {
			Document doc = DocumentHelper.parseText(xmlStr);
			Element root = doc.getRootElement();
			log.debug("parse xml from String ,get root ==> " + root.getName());
			List<Element> beanEleList = root.elements(clazz.getSimpleName());
			log.debug("parse xml from String ,get bean Element size ==> " + beanEleList.size());
			for (Element element : beanEleList) {
				Object obj = xml2Bean(element.asXML(), clazz);
				if (null != obj)
					objList.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("end parse xml to bean list, parsed bean ==> " + objList.size()+" ["+(new Date()).getTime()+"]");
		return objList;
	}
}
