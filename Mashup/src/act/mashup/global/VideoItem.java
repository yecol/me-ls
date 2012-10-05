package act.mashup.global;

import org.jdom.Element;

public class VideoItem {
	private String VideoTitle;
	private String VideoDesc;
	private String VideoDuration;
	private String VideoThumbURL;
	private String VideoLinkURL;
	private String VideoURL;

	public VideoItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getVideoTitle() {
		return VideoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		VideoTitle = videoTitle;
	}

	public String getVideoDesc() {
		return VideoDesc;
	}

	public void setVideoDesc(String videoDesc) {
		VideoDesc = videoDesc;
	}

	public String getVideoDuration() {
		return VideoDuration;
	}

	public void setVideoDuration(String videoDuration) {
		VideoDuration = videoDuration;
	}

	public String getVideoThumbURL() {
		return VideoThumbURL;
	}

	public void setVideoThumbURL(String videoThumbURL) {
		VideoThumbURL = videoThumbURL;
	}

	public String getVideoLinkURL() {
		return VideoLinkURL;
	}

	public void setVideoLinkURL(String videoLinkURL) {
		VideoLinkURL = videoLinkURL;
	}

	public String getVideoURL() {
		return VideoURL;
	}

	public void setVideoURL(String videoURL) {
		VideoURL = videoURL;
	}

	@Override
	public String toString() {
		return "VideoItem [VideoTitle=" + VideoTitle + ", VideoDesc=" + VideoDesc + ", VideoDuration=" + VideoDuration + ", VideoThumbURL=" + VideoThumbURL + ", VideoLinkURL="
				+ VideoLinkURL + ", VideoURL=" + VideoURL + "]";
	}
	
	public Element toElement(){
		Element element = new Element("VideoItem");
		
		Element _ele0 = new Element("VideoTitle");
		_ele0.addContent(this.getVideoTitle());
		element.addContent(_ele0);
		
		Element _ele1 = new Element("VideoDesc");
		_ele1.addContent(this.getVideoDesc());
		element.addContent(_ele1);
		
		Element _ele2 = new Element("VideoThumbURL");
		_ele2.addContent(this.getVideoThumbURL());
		element.addContent(_ele2);
		
		Element _ele3 = new Element("VideoURL");
		_ele3.addContent(this.getVideoURL());
		element.addContent(_ele3);
	
		return element;
	}

}
