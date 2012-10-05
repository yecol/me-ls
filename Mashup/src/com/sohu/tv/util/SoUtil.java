package com.sohu.tv.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sohu.tv.SoException;


/**
 * 读属性文件
 * 
 * @author chengxinsun
 * @date 2011-6-15
 */
public class SoUtil {

	private static final Log log = LogFactory.getLog(SoUtil.class);

	public static Properties getResourceAsProperties(String resource) {
		Properties props = new Properties();
		InputStream in = null;
		ClassLoader loader = SoUtil.class.getClassLoader();
		if (loader != null)
			in = loader.getResourceAsStream(resource);
		if (in == null)
			in = ClassLoader.getSystemResourceAsStream(resource);
		if (in == null)
			log.info("Could not find resource " + resource);
		try {
			props.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}

	/**
	 * 转JSONArray 到 Long[]
	 * 
	 * @param ja
	 * @return
	 */

	public static long[] getLongArrayByJSONArray(JSONArray ja) {
		if (null == ja)
			return null;
		long[] _longArray = new long[ja.length()];
		try {
			for (int i = 0; i <= ja.length(); i++) {
				System.out.println("long[] : " + ja.getLong(i));
				_longArray[i] = ja.getLong(i);
			}
		} catch (Exception e) {
			new SoException(e);
		}
		return _longArray;
	}

	/**
	 * 转JSONArray 到 Float[]
	 * 
	 * @param ja
	 * @return
	 */
	public static float[] getFloatArrayByJSONArray(JSONArray ja) {
		if (null == ja)
			return null;
		float[] _floatArray = new float[ja.length()];
		try {
			for (int i = 0; i <= ja.length(); i++) {
				_floatArray[i] = Float.valueOf(ja.getDouble(i) + "");
			}
		} catch (Exception e) {
			new SoException(e);
		}
		return _floatArray;
	}

	/**
	 * 转JSONArray 到 String[]
	 * 
	 * @param ja
	 * @return
	 */
	public static String[] getStringArrayByJSONArray(JSONArray ja) {
		if (null == ja)
			return null;
		String[] _stringArray = new String[ja.length()];
		try {
			for (int i = 0; i <= ja.length(); i++) {
				_stringArray[i] = ja.getString(i);
			}
		} catch (Exception e) {
			new SoException(e);
		}
		return _stringArray;
	}

	/**
	 * 判断是否为long 数组 类型
	 * 
	 * @param key
	 * @return
	 */
	public static boolean isLongArray(String key) {
		String[] floatKey = new String[] { "clipsBytes" };
		List list = new ArrayList();
		list = Arrays.asList(floatKey);
		return list.contains(key);
	}

	/**
	 * 判断是否为float 数组类型
	 * 
	 * @param key
	 * @return
	 */
	public static boolean isFloatArray(String key) {
		String[] floatKey = new String[] { "clipsDuration" };
		List list = new ArrayList();
		list = Arrays.asList(floatKey);
		return list.contains(key);
	}

	public static <T> Object createObjectByJSONObject(Class<T> ownerClass,
			Object obj, JSONObject json) throws SoException {
		try {

			// 实例化类
			// System.out.println(json.toString());
			// 得到所有key
			Iterator keys = json.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				if (StringUtils.isBlank(key)) {
					continue;
				}
				Object valueObj = json.get(key);
				
				if(valueObj==null){
					log.warn("Value is NULL ."+"key is "+"["+key+"]");
					continue;
				}
				Field field=null;
				try{
					 field = ownerClass.getDeclaredField(key);
				}catch(NoSuchFieldException nf){
					log.warn("Lost attribute : "+"["+key+"}");
					continue;
				}
				Object fileType = field.getType();

				if (valueObj instanceof JSONArray) {
					JSONArray ja = (JSONArray) valueObj;
					// long 数组
					if (SoUtil.isLongArray(key)) {
						long[] _strArray = SoUtil.getLongArrayByJSONArray(ja);
						Method mtd = ownerClass.getMethod("set"
								+ WordUtils.capitalize(key),
								new Class[] { long[].class });// 取得所需类的方法对象
						mtd.invoke(obj, new Object[] { _strArray });// 执行相应赋值方法
						// float 数组
					} else if (SoUtil.isFloatArray(key)) {
						float[] _strArray = SoUtil.getFloatArrayByJSONArray(ja);
						Method mtd = ownerClass.getMethod("set"
								+ WordUtils.capitalize(key),
								new Class[] { float[].class });// 取得所需类的方法对象
						mtd.invoke(obj, new Object[] { _strArray });// 执行相应赋值方法
					} else {// 默认String
						if (key.equals("tv_cont_cats")) {
							String[] _strArray = SoUtil
									.getStringArrayByJSONArray(ja);
							Method mtd = ownerClass.getMethod("set"
									+ WordUtils.capitalize(key),
									new Class[] { String.class });// 取得所需类的方法对象
							mtd.invoke(obj, new Object[] { Arrays.toString(_strArray)});// 执行相应赋值方法
						} else {
							String[] _strArray = SoUtil
									.getStringArrayByJSONArray(ja);
							Method mtd = ownerClass.getMethod("set"
									+ WordUtils.capitalize(key),
									new Class[] { String[].class });// 取得所需类的方法对象
							mtd.invoke(obj, new Object[] { _strArray });// 执行相应赋值方法
						}
					}

				} else if (valueObj instanceof JSONObject) {
					JSONObject _json = (JSONObject) valueObj;
					createObjectByJSONObject(ownerClass, obj, _json);
				} else {
					if (fileType == Integer.class) {
						Method mtd = ownerClass.getMethod("set"
								+ WordUtils.capitalize(key),
								new Class[] { Integer.class });// 取得所需类的方法对象
					
						String temp = String.valueOf(valueObj);
						Integer i =null;
						if(StringUtils.isBlank(temp) || temp.equals("null")){
							 i = new Integer("0");
						}else{
							 i = new Integer(temp);
						}
						mtd.invoke(obj, new Object[] { i });// 执行相应赋值方法
					} else if (fileType == Long.class) {
						Method mtd = ownerClass.getMethod("set"
								+ WordUtils.capitalize(key),
								new Class[] { Long.class });// 取得所需类的方法对象
						String temp = String.valueOf(valueObj);
						 Long l =null;
						if(StringUtils.isBlank(temp) || temp.equals("null")){
							l=new Long(0);
						}else{
							 l=new Long(temp);;
						}
						mtd.invoke(obj, new Object[] { l });// 执行相应赋值方法
					} else if (fileType == Double.class) {
						Method mtd = ownerClass.getMethod("set"
								+ WordUtils.capitalize(key),
								new Class[] { Double.class });// 取得所需类的方法对象

						Double d = Double.valueOf(String.valueOf(valueObj));
						mtd.invoke(obj, new Object[] { d });// 执行相应赋值方法
					} else if (fileType == Float.class) {
						Method mtd = ownerClass.getMethod("set"
								+ WordUtils.capitalize(key),
								new Class[] { Float.class });// 取得所需类的方法对象

						Float f = Float.valueOf(String.valueOf(valueObj));
						mtd.invoke(obj, new Object[] { f });// 执行相应赋值方法
					} else if (fileType == String.class) {
						Method mtd = ownerClass.getMethod("set"
								+ WordUtils.capitalize(key),
								new Class[] { String.class });// 取得所需类的方法对象

						mtd.invoke(obj, new Object[] { valueObj });// 执行相应赋值方法
					}
				}

			}
			T t = (T) obj;// 取得赋值以后的对象
			return t;
		} catch (Exception e) {
			if (e instanceof JSONException) {
				throw new SoException(e);
			}
			e.printStackTrace();
		}
		return null;
	}

}
