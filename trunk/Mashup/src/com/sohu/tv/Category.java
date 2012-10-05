package com.sohu.tv;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sohu.tv.util.SoUtil;

/**
 * 分类导航
 * 
 * @author chengxinsun
 * @date 2011-6-15
 */
public class Category {

	private String[] cateValues;
	private String[] searchKeys;
	private String cateName;
	private String cateAlias;
	private String cateCode;

	public String getCateAlias() {
		return cateAlias;
	}

	public void setCateAlias(String cateAlias) {
		this.cateAlias = cateAlias;
	}

	public String getCateCode() {
		return cateCode;
	}

	public void setCateCode(String cateCode) {
		this.cateCode = cateCode;
	}

	/**
	 * create Category by Json
	 * 
	 * @param json
	 * @throws JSONException
	 */
	public static Category getCategoryByJSON(JSONObject json)
			throws JSONException {
		Category Category = new Category();
		try {
			Class<Category> ownerClass = (Class<Category>) Category.getClass();
			Object obj = (Object) ownerClass.newInstance();// 实例化类
			Iterator keys = json.keys();
			while (keys.hasNext()) {

				String key = (String) keys.next();
				Object valueObj = json.get(key);
				if (valueObj instanceof String) {
					Method mtd = ownerClass.getMethod(
							"set" + WordUtils.capitalize(key),
							new Class[] { String.class });// 取得所需类的方法对象
					mtd.invoke(obj, new Object[] { valueObj });// 执行相应赋值方法
				} else if (valueObj instanceof JSONArray) {
					JSONArray ja = (JSONArray) valueObj;
					String[] _strArray = SoUtil.getStringArrayByJSONArray(ja);
					Method mtd = ownerClass.getMethod(
							"set" + WordUtils.capitalize(key),
							new Class[] { String[].class });// 取得所需类的方法对象
					mtd.invoke(obj, new Object[] { _strArray });// 执行相应赋值方法
				}

			}
			Category = (Category) obj;// 取得赋值以后的对象
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Category;
	}
	
	/**
	 * create Category by Json
	 * @param json
	 * @return
	 * @throws SoException
	 */
	public static List<Category> constructCategorys(JSONObject json)
			throws SoException {
		
		try {
			
			Integer status = json.getInt("status");

			if (status != 200) {
				throw new SoException("Error return result! status :" + status);
			}
			JSONObject _data = json.getJSONObject("data");
			if(null==_data || _data.length()==0){
				return new ArrayList();
			}
			JSONArray list = _data.getJSONArray("categorys");
			int size = list.length();
			List<Category> Categorys = new ArrayList<Category>(size);
			for (int i = 0; i < size; i++) {
				Categorys.add(getCategoryByJSON(list.getJSONObject(i)));
			}
			return Categorys;
		} catch (JSONException jsone) {
			throw new SoException(jsone);
		}
	}

	public String[] getCateValues() {
		return cateValues;
	}

	public void setCateValues(String[] cateValues) {
		this.cateValues = cateValues;
	}

	public String[] getSearchKeys() {
		return searchKeys;
	}

	public void setSearchKeys(String[] searchKeys) {
		this.searchKeys = searchKeys;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}


}
