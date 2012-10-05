package com.sohu.tv;

import org.json.JSONException;
import org.json.JSONObject;

import com.sohu.tv.util.SoUtil;

/**
 * 视频分段
 * 
 * @author chengxinsun
 * @date 2011-6-15
 */
public class VideoSplits {

	private long vid;//
	// 供调度使用,配置调度返回的方式,2:200返回;1:301返回
	private int prot;
	// 城市编号
	private int ctCode;// ct: 34
	private int holiday;
	private int fee;
	// 类别名称
	private String categoryName;// caname: "电视剧"
	private int previewSecond;// preview: 2
	private String tv_url;
	// 软字幕路径
	private String scap;//
	// 硬字幕版本，如果没有硬字幕则显示0,-1代表未处理
	private int hcap;
	// 预加载秒数
	private int priorLoad;// pL: 30
	private int categoryId; // caid: 2

	private int p2pflag;
	// 网络类型
	private int netTypeCode;// nt;
	// p2p下载线程数量
	private int p2pThreadNum;// tn;
	// 播放方式
	private int playType;// fms;
	// p2p下载每线程限速
	private int p2pSpeed;// sp;
	// 用户类型
	private int user;// uS;
	private int status;
	private int videoType;// vt: 1
	// 播放状态
	private int play;
	private long pid;
	// 调度地址
	private String allot;

	/*************** data start ***************************/
	private long highVid;
	private int ipLimit;
	private String[] clipsURL;
	private int version;
	private long[] clipsBytes;
	private int totalDuration;
	private int height;
	private String coverImg;
	// clipsSynUrl
	private String[] su;
	// 精彩推荐
	private String eP;
	// 超清id
	private long superVid;
	// 片尾开始时间
	private int eT;

	private float[] clipsDuration;//
	// 视频宽
	private long width;
	// 帧率
	private int fps;
	private long norVid;
	// hashCodes
	private String[] hc;
	// 相关联ID
	private long relativeId;
	// 分段个数
	private int num;
	private String tvName;
	// url 加密
	private String[] ck;
	// 片头结束时间
	private int sT;
	private long totalBytes;
	// channel
	private String ch;
	private int totalBlocks;

	/************************* dat end **************************************/

	public long getVid() {
		return vid;
	}

	public long getHighVid() {
		return highVid;
	}
	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}
	public void setHighVid(long highVid) {
		this.highVid = highVid;
	}

	public int getIpLimit() {
		return ipLimit;
	}

	public void setIpLimit(int ipLimit) {
		this.ipLimit = ipLimit;
	}

	public String[] getClipsURL() {
		return clipsURL;
	}

	public void setClipsURL(String[] clipsURL) {
		this.clipsURL = clipsURL;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public long[] getClipsBytes() {
		return clipsBytes;
	}

	public void setClipsBytes(long[] clipsBytes) {
		this.clipsBytes = clipsBytes;
	}

	public int getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(int totalDuration) {
		this.totalDuration = totalDuration;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public String[] getSu() {
		return su;
	}

	public void setSu(String[] su) {
		this.su = su;
	}

	public String geteP() {
		return eP;
	}

	public void seteP(String eP) {
		this.eP = eP;
	}

	public long getSuperVid() {
		return superVid;
	}

	public void setSuperVid(long superVid) {
		this.superVid = superVid;
	}

	public int geteT() {
		return eT;
	}

	public void setET(int eT) {
		this.eT = eT;
	}


	public float[] getClipsDuration() {
		return clipsDuration;
	}

	public void setClipsDuration(float[] clipsDuration) {
		this.clipsDuration = clipsDuration;
	}

	public long getWidth() {
		return width;
	}

	public void setWidth(long width) {
		this.width = width;
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	public long getNorVid() {
		return norVid;
	}

	public void setNorVid(long norVid) {
		this.norVid = norVid;
	}

	public String[] getHc() {
		return hc;
	}

	public void setHc(String[] hc) {
		this.hc = hc;
	}

	public long getRelativeId() {
		return relativeId;
	}

	public void setRelativeId(long relativeId) {
		this.relativeId = relativeId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getTvName() {
		return tvName;
	}

	public void setTvName(String tvName) {
		this.tvName = tvName;
	}

	public String[] getCk() {
		return ck;
	}

	public void setCk(String[] ck) {
		this.ck = ck;
	}

	public int getsT() {
		return sT;
	}

	public void setST(int sT) {
		this.sT = sT;
	}

	public long getTotalBytes() {
		return totalBytes;
	}

	public void setTotalBytes(long totalBytes) {
		this.totalBytes = totalBytes;
	}

	public String getCh() {
		return ch;
	}

	public void setCh(String ch) {
		this.ch = ch;
	}

	public int getTotalBlocks() {
		return totalBlocks;
	}

	public void setTotalBlocks(int totalBlocks) {
		this.totalBlocks = totalBlocks;
	}

	public void setVid(long vid) {
		this.vid = vid;
	}

	public int getProt() {
		return prot;
	}

	public void setProt(int prot) {
		this.prot = prot;
	}

	public int getCtCode() {
		return ctCode;
	}

	public void setCtCode(int ctCode) {
		this.ctCode = ctCode;
	}
	
	public void setCtCode(Integer ctCode) {
		this.ctCode = ctCode;
	}

	public int getHoliday() {
		return holiday;
	}

	public void setHoliday(int holiday) {
		this.holiday = holiday;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getPreviewSecond() {
		return previewSecond;
	}

	public void setPreviewSecond(int previewSecond) {
		this.previewSecond = previewSecond;
	}

	public String getTv_url() {
		return tv_url;
	}

	public void setTv_url(String tv_url) {
		this.tv_url = tv_url;
	}

	public String getScap() {
		return scap;
	}

	public void setScap(String scap) {
		this.scap = scap;
	}

	public int getHcap() {
		return hcap;
	}

	public void setHcap(int hcap) {
		this.hcap = hcap;
	}

	public int getPriorLoad() {
		return priorLoad;
	}

	public void setPriorLoad(int priorLoad) {
		this.priorLoad = priorLoad;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getP2pflag() {
		return p2pflag;
	}

	public void setP2pflag(int p2pflag) {
		this.p2pflag = p2pflag;
	}

	public int getNetTypeCode() {
		return netTypeCode;
	}

	public void setNetTypeCode(int netTypeCode) {
		this.netTypeCode = netTypeCode;
	}

	public int getP2pThreadNum() {
		return p2pThreadNum;
	}

	public void setP2pThreadNum(int p2pThreadNum) {
		this.p2pThreadNum = p2pThreadNum;
	}

	public int getPlayType() {
		return playType;
	}

	public void setPlayType(int playType) {
		this.playType = playType;
	}

	public int getP2pSpeed() {
		return p2pSpeed;
	}

	public void setP2pSpeed(int p2pSpeed) {
		this.p2pSpeed = p2pSpeed;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getVideoType() {
		return videoType;
	}

	public void setVideoType(int videoType) {
		this.videoType = videoType;
	}

	public int getPlay() {
		return play;
	}

	public void setPlay(int play) {
		this.play = play;
	}



	public String getAllot() {
		return allot;
	}

	public void setAllot(String allot) {
		this.allot = allot;
	}

	/**
	 * create VideoSplitsData by Json
	 * 
	 * @param json
	 * @throws JSONException
	 */

	public static VideoSplits constructVideoSplitss(JSONObject json)throws SoException {
		try {
			System.out.println(json);
			int status = json.getInt("status");

			if (status != 200) {
				throw new SoException("Error return result! status :" + status);
			}
			json = json.getJSONObject("data");
			VideoSplits videoSplits = new VideoSplits();
			Class<VideoSplits> clazz = (Class<VideoSplits>) videoSplits
					.getClass();
			Object obj = (Object) clazz.newInstance();
			return (VideoSplits) SoUtil.createObjectByJSONObject(clazz,obj,json);
		} catch (Exception e) {
			if(e instanceof JSONException ){
				throw new SoException(e);
			}
			e.printStackTrace();
		}
		return null;
	}
}