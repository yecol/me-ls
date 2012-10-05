package act.mashup.module;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import act.mashup.global.EngineNode;
import act.mashup.global.ImageItem;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;
import act.mashup.global.VideoItem;
import act.mashup.util.ChineseSegment;

import com.sohu.tv.SohuSingleton;
import com.yahoo.boss.YahooBoss;

public class EnrichMedia extends AbstractListModule {

	// 支持动态配置

	private Integer in;
	private YahooBoss yahooBoss;
	private int relPhotoCount;// 相关图片搜索的结果数量 2-3
	private int relVideoCount;// 相关视频搜索的结果数量 2-3

	private String searchKey;
	private ArrayList<String> segments;
	private ChineseSegment cs;

	// private List<ImageItem> imageItems;
	// private List<VideoItem> videoItems;

	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}

	@Override
	protected void Prepare() throws Exception {
		Element relPhoto = en.getParas().getChild("relPhotoCount", KV.gf);
		Element relVideo = en.getParas().getChild("relVideoCount", KV.gf);

		cs = ChineseSegment.getInstance();
		Integer photoIstream, videoIstream;
		try {
			photoIstream = Integer.parseInt(relPhoto.getAttributeValue("istream"));
			videoIstream = Integer.parseInt(relVideo.getAttributeValue("istream"));
		} catch (NumberFormatException nfe) {
			photoIstream = 0;
			videoIstream = 0;
		}
		if (en.isDynamic() == true && photoIstream != 0) {
			relPhotoCount = Math.min(5, Integer.parseInt(results.get(photoIstream).GetResultMap().get("relPhotoCount").toString()));
			relVideoCount = Math.min(5, Integer.parseInt(results.get(videoIstream).GetResultMap().get("relVideoCount").toString()));
		} else {
			try {
				relPhotoCount = Math.min(5, Integer.parseInt(relPhoto.getValue().trim()));
			} catch (NumberFormatException e) {
				relPhotoCount = 0;
			}
			try {
				relVideoCount = Math.min(5, Integer.parseInt(relVideo.getValue().trim()));
			} catch (NumberFormatException e) {
				relVideoCount = 0;
			}
		}

		yahooBoss = new YahooBoss();
		in = en.getInputs().get(0);
		items.addAll(results.get(in).GetResultList());
	}

	@Override
	protected void Execute() throws Exception {
		for (Iterator it = items.iterator(); it.hasNext();) {
			Item item = (Item) it.next();
			ArrayList<String> segments = cs.getSegments(item.getValue(KV.TITLE).toString());

			if (segments.size() >= 2)
				searchKey = segments.get(0).concat(segments.get(1));
			else if (segments.size() > 0) {
				searchKey = segments.get(0);
			}

			if (searchKey != null && searchKey.equals("") == false) {

				List<ImageItem> imageItems = yahooBoss.SearchImageWithKey(searchKey, relPhotoCount);
				if (imageItems != null) {
					item.setValue("relImages", imageItems);
				}

				List<VideoItem> videoItems = SohuSingleton.searchVideoItemByKey(searchKey, relPhotoCount);
				if (videoItems != null) {
					item.setValue("relVideos", videoItems);
				}
			}
		}
		rlt.SetResultList(items);

	}

	// private String ExtractGeoInformation(ArrayList<String> segments) {
	// for (String segment : segments) {
	// if (cp.FindPlace(segment) == true)
	// return segment;
	// }
	// return null;
	// }

}
