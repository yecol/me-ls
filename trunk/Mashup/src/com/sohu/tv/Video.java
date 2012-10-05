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
 * 搜索 Video
 * 
 * @author chengxinsun
 * @date 2011-6-13
 */
public class Video implements java.io.Serializable {
	/**
	 * 视频ID
	 */
	public Long vid;

	/**
	 * 专辑ID
	 */
	private Long sid;

	/**
	 * 视频标题
	 */
	private String tv_name;

	/**
	 * 视频描述
	 */
	private String tv_desc;

	/**
	 * 播放地址
	 */
	private String tv_url;

	/**
	 * 视频类型 eg:喜剧片、动作片
	 */
	private String tv_cont_cat;

	/**
	 * 视频导演
	 */
	private String director;

	/**
	 * 视频主演
	 */
	private String main_actor;

	/**
	 * 视频地区
	 */
	private String area;

	/**
	 * 视频年份
	 */
	private Integer tv_year;

	/**
	 * 视频来源
	 */
	private String tv_source;

	/**
	 * 视频评分
	 */
	private Double tv_score;

	/**
	 * 视频评分人数
	 */
	private Integer tv_score_count;

	/**
	 * 视频缩略图
	 */
	private String video_big_pic;

	/**
	 * 视频播放次数
	 */
	private Long tv_play_count;

	/**
	 * 视频在专辑中的播放顺序
	 */
	private Integer tv_play_order;

	/**
	 * 视频发布时间
	 */
	private String tv_application_time;

	/**
	 * 是否独家 0 否，1 是
	 */
	private Integer only;

	/**
	 * 是否付费 0否，1 是
	 */
	private Integer fee;

	/**
	 * 专辑地址
	 */
	private String s_url;

	/**
	 * 是否支持包月 0否，1是
	 */
	private Integer fee_month;

	/**
	 * 所获奖项
	 */
	private String cup;

	/**
	 * 所获奖项说明
	 */
	private String cup_item;

	private String sub_title;

	private String tip_num;

	private Integer fee_rule_id;

	private String ver_small_pic;

	private String ver_big_pic;

	private String hor_big_pic;

	private String hor_small_pic;

	private Integer pid;

	private Integer cid;

	private String tv_comment;

	private Integer top50_day_time;

	private Integer tv_trend_rank;

	private Integer voters;

	private Integer top50_day_rank;

	private Double tv_trend_count;

	private Integer tv_all_count;

	private Integer tv_set_now;

	private Integer tv_count;

	private String actor;

	private String video_publish_time;

	private String tip;

	private Integer code;

	private String tv_cont_cats;

	private Integer tv_set_total;

	private String tv_big_pic;

	private String tv_alias;

	private Integer tv_category_id;

	private String video_small_pic;

	private Integer tv_effective;

	private Integer tv_area_id;

	private String tv_english_name;

	private String video_image;
	
	private String subject_name;
	
	private String issue_time;
	
	private String time_length;
	
	private String update_time;
	
	private Long nid;
	
	private Long tv_ver_id;
	
	private Integer video_version;
	
	private Integer video_order;
	
	private Long tv_id;
	
	private Long play_list_id;
	
	private String tv_sub_name;
	
	private Integer subjectId;

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Long getPlay_list_id() {
		return play_list_id;
	}

	public void setPlay_list_id(Long play_list_id) {
		this.play_list_id = play_list_id;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public Integer getVideo_version() {
		return video_version;
	}

	public void setVideo_version(Integer video_version) {
		this.video_version = video_version;
	}

	public Integer getVideo_order() {
		return video_order;
	}

	public void setVideo_order(Integer video_order) {
		this.video_order = video_order;
	}

	public String getTime_length() {
		return time_length;
	}

	public void setTime_length(String time_length) {
		this.time_length = time_length;
	}

	public String getIssue_time() {
		return issue_time;
	}

	public void setIssue_time(String issue_time) {
		this.issue_time = issue_time;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	public String getTv_english_name() {
		return tv_english_name;
	}

	public void setTv_english_name(String tv_english_name) {
		this.tv_english_name = tv_english_name;
	}

	public Integer getTv_area_id() {
		return tv_area_id;
	}

	public void setTv_area_id(Integer tv_area_id) {
		this.tv_area_id = tv_area_id;
	}

	public Integer getTv_effective() {
		return tv_effective;
	}

	public void setTv_effective(Integer tv_effective) {
		this.tv_effective = tv_effective;
	}

	public String getVideo_small_pic() {
		return video_small_pic;
	}

	public void setVideo_small_pic(String video_small_pic) {
		this.video_small_pic = video_small_pic;
	}

	public Integer getTv_category_id() {
		return tv_category_id;
	}

	public void setTv_category_id(Integer tv_category_id) {
		this.tv_category_id = tv_category_id;
	}

	public String getTv_alias() {
		return tv_alias;
	}

	public void setTv_alias(String tv_alias) {
		this.tv_alias = tv_alias;
	}

	public String getTv_big_pic() {
		return tv_big_pic;
	}

	public void setTv_big_pic(String tv_big_pic) {
		this.tv_big_pic = tv_big_pic;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getTv_comment() {
		return tv_comment;
	}

	public void setTv_comment(String tv_comment) {
		this.tv_comment = tv_comment;
	}

	public Integer getTv_trend_rank() {
		return tv_trend_rank;
	}

	public void setTv_trend_rank(Integer tv_trend_rank) {
		this.tv_trend_rank = tv_trend_rank;
	}

	public Integer getVoters() {
		return voters;
	}

	public void setVoters(Integer voters) {
		this.voters = voters;
	}

	public Integer getTop50_day_rank() {
		return top50_day_rank;
	}

	public void setTop50_day_rank(Integer top50_day_rank) {
		this.top50_day_rank = top50_day_rank;
	}

	public Double getTv_trend_count() {
		return tv_trend_count;
	}

	public void setTv_trend_count(Double tv_trend_count) {
		this.tv_trend_count = tv_trend_count;
	}

	public Integer getTv_all_count() {
		return tv_all_count;
	}

	public void setTv_all_count(Integer tv_all_count) {
		this.tv_all_count = tv_all_count;
	}

	public Integer getTv_set_now() {
		return tv_set_now;
	}

	public void setTv_set_now(Integer tv_set_now) {
		this.tv_set_now = tv_set_now;
	}

	public Integer getTv_count() {
		return tv_count;
	}

	public void setTv_count(Integer tv_count) {
		this.tv_count = tv_count;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getVideo_publish_time() {
		return video_publish_time;
	}

	public void setVideo_publish_time(String video_publish_time) {
		this.video_publish_time = video_publish_time;
	}

	public String getVideo_properties() {
		return video_properties;
	}

	public void setVideo_properties(String video_properties) {
		this.video_properties = video_properties;
	}

	public String getVideo_copy_company() {
		return video_copy_company;
	}

	public void setVideo_copy_company(String video_copy_company) {
		this.video_copy_company = video_copy_company;
	}

	public String getVar_publish_time() {
		return var_publish_time;
	}

	public void setVar_publish_time(String var_publish_time) {
		this.var_publish_time = var_publish_time;
	}

	public String getTv_guest() {
		return tv_guest;
	}

	public void setTv_guest(String tv_guest) {
		this.tv_guest = tv_guest;
	}

	public Integer getTv_play_time() {
		return tv_play_time;
	}

	public void setTv_play_time(Integer tv_play_time) {
		this.tv_play_time = tv_play_time;
	}

	public String getTv_tag() {
		return tv_tag;
	}

	public void setTv_tag(String tv_tag) {
		this.tv_tag = tv_tag;
	}

	private String video_properties;

	private String video_copy_company;

	private String var_publish_time;

	private String tv_guest;

	private Integer tv_play_time;

	private String tv_tag;

	private String tv_keyword;

	private String tv_producer;

	public String getTv_keyword() {
		return tv_keyword;
	}

	public void setTv_keyword(String tv_keyword) {
		this.tv_keyword = tv_keyword;
	}

	public Long getVid() {
		return vid;
	}

	public void setVid(Long vid) {
		this.vid = vid;
	}

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public String getTv_name() {
		return tv_name;
	}

	public void setTv_name(String tv_name) {
		this.tv_name = tv_name;
	}

	public String getTv_desc() {
		return tv_desc;
	}

	public void setTv_desc(String tv_desc) {
		this.tv_desc = tv_desc;
	}

	public String getTv_url() {
		return tv_url;
	}

	public void setTv_url(String tv_url) {
		this.tv_url = tv_url;
	}

	public String getTv_cont_cat() {
		return tv_cont_cat;
	}

	public void setTv_cont_cat(String tv_cont_cat) {
		this.tv_cont_cat = tv_cont_cat;
	}

	public String getTv_cont_cats() {
		return tv_cont_cats;
	}

	public void setTv_cont_cats(String tv_cont_cats) {
		this.tv_cont_cats = tv_cont_cats;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getMain_actor() {
		return main_actor;
	}

	public void setMain_actor(String main_actor) {
		this.main_actor = main_actor;
	}

	public Integer getTv_set_total() {
		return tv_set_total;
	}

	public void setTv_set_total(Integer tv_set_total) {
		this.tv_set_total = tv_set_total;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getTv_year() {
		return tv_year;
	}

	public void setTv_year(Integer tv_year) {
		this.tv_year = tv_year;
	}

	public String getTv_source() {
		return tv_source;
	}

	public void setTv_source(String tv_source) {
		this.tv_source = tv_source;
	}

	public Double getTv_score() {
		return tv_score;
	}

	public void setTv_score(Double tv_score) {
		this.tv_score = tv_score;
	}

	public Integer getTv_score_count() {
		return tv_score_count;
	}

	public void setTv_score_count(Integer tv_score_count) {
		this.tv_score_count = tv_score_count;
	}

	public String getVideo_big_pic() {
		return video_big_pic;
	}

	public void setVideo_big_pic(String video_big_pic) {
		this.video_big_pic = video_big_pic;
	}

	public Long getTv_play_count() {
		return tv_play_count;
	}

	public void setTv_play_count(Long tv_play_count) {
		this.tv_play_count = tv_play_count;
	}

	public Integer getTv_play_order() {
		return tv_play_order;
	}

	public void setTv_play_order(Integer tv_play_order) {
		this.tv_play_order = tv_play_order;
	}

	public String getTv_application_time() {
		return tv_application_time;
	}

	public void setTv_application_time(String tv_application_time) {
		this.tv_application_time = tv_application_time;
	}

	public Integer getOnly() {
		return only;
	}

	public void setOnly(Integer only) {
		this.only = only;
	}

	public Integer getFee() {
		return fee;
	}

	public void setFee(Integer fee) {
		this.fee = fee;
	}

	public String getS_url() {
		return s_url;
	}

	public void setS_url(String s_url) {
		this.s_url = s_url;
	}

	public Integer getFee_month() {
		return fee_month;
	}

	public void setFee_month(Integer fee_month) {
		this.fee_month = fee_month;
	}

	public String getCup() {
		return cup;
	}

	public void setCup(String cup) {
		this.cup = cup;
	}

	public String getCup_item() {
		return cup_item;
	}

	public void setCup_item(String cup_item) {
		this.cup_item = cup_item;
	}

	public String getSub_title() {
		return sub_title;
	}

	public void setSub_title(String sub_title) {
		this.sub_title = sub_title;
	}

	public String getTip_num() {
		return tip_num;
	}

	public void setTip_num(String tip_num) {
		this.tip_num = tip_num;
	}

	public Integer getFee_rule_id() {
		return fee_rule_id;
	}

	public void setFee_rule_id(Integer fee_rule_id) {
		this.fee_rule_id = fee_rule_id;
	}

	public String getVer_small_pic() {
		return ver_small_pic;
	}

	public void setVer_small_pic(String ver_small_pic) {
		this.ver_small_pic = ver_small_pic;
	}

	public String getVer_big_pic() {
		return ver_big_pic;
	}

	public void setVer_big_pic(String ver_big_pic) {
		this.ver_big_pic = ver_big_pic;
	}

	public String getHor_big_pic() {
		return hor_big_pic;
	}

	public void setHor_big_pic(String hor_big_pic) {
		this.hor_big_pic = hor_big_pic;
	}

	public String getHor_small_pic() {
		return hor_small_pic;
	}

	public void setHor_small_pic(String hor_small_pic) {
		this.hor_small_pic = hor_small_pic;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Video() {
		super();
	}

	/**
	 * create Video by Json
	 * 
	 * @param json
	 * @throws JSONException
	 */
	public static Video getVideoByJSON(JSONObject json) throws JSONException {
		Video video = new Video();
		try {
			Class<Video> ownerClass = (Class<Video>) video.getClass();
			Object obj = (Object) ownerClass.newInstance();// 实例化类
			// System.out.prIntegerln(json.toString());
			Iterator keys = json.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				Method mtd = ownerClass.getMethod("set"
						+ WordUtils.capitalize(key),
						new Class[] { String.class });// 取得所需类的方法对象
				mtd.invoke(obj, new Object[] { json.get(key) });// 执行相应赋值方法
			}
			video = (Video) obj;// 取得赋值以后的对象
		} catch (Exception e) {
			e.printStackTrace();
		}

		return video;
	}

	/**
	 * create Video by Json
	 * 
	 * @param json
	 * @throws JSONException
	 */
	public static Video getSetInfoByJSON(JSONObject json) throws JSONException {
		System.out.println("JSON : "+json);
		Video video = new Video();
		try {
			//json = json.getJSONObject("set");
			Integer status = json.getInt("status");

			if (status != 200) {
				throw new SoException("Error return result! status :" + status);
			}
			Class<Video> ownerClass = (Class<Video>) video.getClass();
			Object obj = (Object) ownerClass.newInstance();// 实例化类
			JSONObject resultJson = json.getJSONObject("data");
			video = (Video) SoUtil.createObjectByJSONObject(ownerClass, obj,
					resultJson);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return video;
	}

	public static List<Video> constructVideos(JSONObject json)
			throws SoException {
		try {
			Integer status = json.getInt("status");

			if (status != 200) {
				throw new SoException("Error return result! status :" + status);
			}
			Video video = new Video();
			JSONObject _data = json.getJSONObject("data");
			if(null==_data || _data.length()==0){
				return new ArrayList();
			}
			JSONArray list = _data.getJSONArray("videos");
			Integer size = list.length();
			List<Video> videos = new ArrayList<Video>(size);
			for (Integer i = 0; i < size; i++) {
				Class<Video> ownerClass = (Class<Video>) video.getClass();
				Video obj = (Video) ownerClass.newInstance();// 实例化类

				Video result_v = (Video) SoUtil.createObjectByJSONObject(
						ownerClass, obj, list.getJSONObject(i));
				videos.add(result_v);
			}
			return videos;
		} catch (Exception jsone) {
			throw new SoException(jsone);
		}
	}

	public Integer getTop50_day_time() {
		return top50_day_time;
	}

	public void setTop50_day_time(Integer top50_day_time) {
		this.top50_day_time = top50_day_time;
	}

	public String getTv_producer() {
		return tv_producer;
	}

	public void setTv_producer(String tv_producer) {
		this.tv_producer = tv_producer;
	}

	public String getVideo_image() {
		return video_image;
	}

	public void setVideo_image(String video_image) {
		this.video_image = video_image;
	}

	public Long getNid() {
		return nid;
	}

	public void setNid(Long nid) {
		this.nid = nid;
	}

	public Long getTv_ver_id() {
		return tv_ver_id;
	}

	public void setTv_ver_id(Long tv_ver_id) {
		this.tv_ver_id = tv_ver_id;
	}

	public Long getTv_id() {
		return tv_id;
	}

	public void setTv_id(Long tv_id) {
		this.tv_id = tv_id;
	}

	public String getTv_sub_name() {
		return tv_sub_name;
	}

	public void setTv_sub_name(String tv_sub_name) {
		this.tv_sub_name = tv_sub_name;
	}

	/*
	 * public static Video constructVideo(JSONObject json) throws SoException {
	 * try { JSONArray list = json.getJSONObject("data").getJSONArray("videos");
	 * return getVideoByJSON(list.getJSONObject(0)); } catch (JSONException
	 * jsone) { throw new SoException(jsone); } }
	 */
}
