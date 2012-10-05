package com.sohu.tv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import act.mashup.global.VideoItem;

public class SohuSingleton {
	private static String apiKey = "3249826a4be8785656c51e5e071b2d08";
	private static String secretKey = "GnAhbxhFtGDOkw3q3arYxbG4jPbPV8cs";
	private static String oauthToken = "";
	private static String tokenSecret = "";

	/**
	 * @param args
	 * @throws SoException
	 * @throws JSONException
	 */
	public static List<VideoItem> searchVideoItemByKey(String searchKey, int resultSize) {
		SoClient client = new SoClient(apiKey, secretKey, oauthToken, tokenSecret);
		HashMap<String, Object> searchParam = new HashMap<String, Object>();
		searchParam.put("key", searchKey);
		SetInfo resultSets = client.search(searchParam);

		List<VideoItem> videoItems = new ArrayList<VideoItem>();

		if (resultSets == null) {
			return null;
		}

		for (int i = 0; i < Math.min(resultSize, resultSets.getCount()); i++) {

			VideoItem videoItem = new VideoItem();
			videoItem.setVideoTitle(resultSets.getVideos().get(i).getTv_name());
			videoItem.setVideoDesc(resultSets.getVideos().get(i).getTv_desc());
			videoItem.setVideoDuration(resultSets.getVideos().get(i).getTip());
			videoItem.setVideoThumbURL(resultSets.getVideos().get(i).getVer_small_pic());
			videoItem.setVideoURL(resultSets.getVideos().get(i).getTv_url());

			videoItems.add(videoItem);
		}

		if (videoItems.size() == 0) {
			return null;
		} else
			System.out.println(videoItems.toString());
		return videoItems;

	}

	public static void main(String[] args) throws JSONException, SoException {
		searchVideoItemByKey("金正日", 3);
	}

	// teleplay/category 电视剧的分类导航
	// movie/category 电影的分类导航
	// zongyi/category 综艺片的分类导航
	public static void getCategory(SoClient client) throws JSONException, SoException {
		List<Category> list = client.getCategory("movie/category");
	}

	/**
	 * 取得焦点视频信息
	 * 
	 * @param client
	 * @throws SoException
	 * @throws JSONException
	 */
	// news/focus 获取焦点新闻
	// teleplay/focus 获取今日电视剧类的焦点剧目
	// movie/focus 获取今日焦点电影
	public static void getFocus(SoClient client) throws JSONException, SoException {
		List<FocusData> fds = client.getFocus("teleplay/focus");
	}

	/**
	 * 取得推荐视频信息
	 * 
	 * @param client
	 * @throws SoException
	 * @throws JSONException
	 */
	// news/recommend 获取推荐新闻
	// teleplay/recommend 获取今日电视剧类的推荐剧目
	// movie/recommend 获取今日电影类的推荐剧目
	// zongyi/recommend 获取综艺分类推荐
	public static void getRecommend(SoClient client) throws JSONException, SoException {
		List<Video> list = client.getRecommend("teleplay/recommend");
	}

	/**
	 * 取得专辑信息
	 * 
	 * @param client
	 * @throws JSONException
	 * @throws SoException
	 */
	public static void getSetInfo(SoClient client) throws JSONException, SoException {
		Video v = client.getSetInfo(310153);
	}

	/**
	 * 取得分集列表
	 * 
	 * @param client
	 * @throws JSONException
	 * @throws SoException
	 */
	public static void getSetList(SoClient client) throws JSONException, SoException {
		SetInfo si = client.getSetList(1003377, 1, 20);
	}

	/*
	 * 电影排行榜 movie/top/views/daily 电影排行榜 ： 播放日榜 movie/top/views/weekly 电影排行榜 ：
	 * 播放周榜 movie/top/views/monthly 电影排行榜 ： 播放月榜 movie/top/views/all 电影排行榜 ：
	 * 播放全部榜 movie/top/score/perfect 电影评分榜: 佳片榜 movie/top/score/good 电影评分榜: 好片榜
	 * movie/top/score/common 电影评分榜: 一般榜 movie/top/score/poor 评分榜: 烂片榜
	 * ******************************* 电视剧排行榜 teleplay/top/views/daily 电视剧排行榜 ：
	 * 播放日榜 teleplay/top/views/weekly 电视剧排行榜 ： 播放周榜 teleplay/top/views/monthly
	 * 电视剧排行榜 ： 播放月榜 teleplay/top/views/all 电视剧排行榜 ： 播放全部榜
	 * teleplay/top/score/perfect 电视剧评分榜: 佳片榜 teleplay/top/score/good 电视剧评分榜:
	 * 好片榜 teleplay/top/score/common 电视剧评分榜: 一般榜 teleplay/top/score/poor 电视剧评分榜:
	 * 烂片榜 ************************************** 综艺片排行榜 zongyi/top/views/daily
	 * 综艺片排行榜 ： 播放日榜 zongyi/top/views/weekly 综艺片排行榜 ： 播放周榜
	 * zongyi/top/views/monthly 综艺片排行榜 ： 播放月榜 zongyi/top/views/all 综艺片排行榜 ：
	 * 播放全部榜 zongyi/top/score/perfect 综艺片评分榜: 佳片榜 zongyi/top/score/good 综艺片评分榜:
	 * 好片榜 zongyi/top/score/common 综艺片评分榜: 一般榜 zongyi/top/score/poor 综艺片评分榜:
	 * 烂片榜榜
	 */
	public static void getTop(SoClient client) throws JSONException, SoException {
		List<Video> videos = client.getTop("movie/top/views/daily");

	}

	/**
	 * 取得视频分段信息
	 * 
	 * @param client
	 * @throws SoException
	 * @throws JSONException
	 */
	public static void getVideo(SoClient client) throws JSONException, SoException {
		VideoSplits vs = client.getVideo(310153);
		System.out.println("TV_NAME : " + vs.getTvName());
	}

	public static void test(SoClient client) throws JSONException, SoException {

		Video vi = client.getSetInfo(1003377);
		System.out.println(vi.getTv_name());
	}

}
