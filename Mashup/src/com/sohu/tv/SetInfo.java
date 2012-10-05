package com.sohu.tv;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sohu.tv.util.SoUtil;

/**
 * 专辑视频
 * 
 * @author chengxinsun
 * 
 */
public class SetInfo {
	/**
	 * 分集数
	 */
	private Integer count;

	/**
	 * 当前专辑下的所有视频
	 */
	private List<Video> videos;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public static SetInfo constructVideos(JSONObject json) throws SoException {
		try {
			Integer status = json.getInt("status");

			if (status != 200) {
				throw new SoException("Error return result! status :" + status);
			}
			SetInfo setInfo = new SetInfo();
			Video video = new Video();
			setInfo.setCount(json.getJSONObject("data").getInt("count"));
			JSONArray list = json.getJSONObject("data").getJSONArray("videos");
			Integer size = list.length();
			List<Video> videos = new ArrayList<Video>(size);
			for (Integer i = 0; i < size; i++) {
				Class<Video> ownerClass = (Class<Video>) video.getClass();
				Video obj = (Video) ownerClass.newInstance();// 实例化类

				Video result_v = (Video) SoUtil.createObjectByJSONObject(
						ownerClass, obj, list.getJSONObject(i));
				videos.add(result_v);
			}
			setInfo.setVideos(videos);
			return setInfo;
		} catch (Exception jsone) {
			throw new SoException(jsone);
		}
	}

}
