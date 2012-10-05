package act.mashup.global;

import org.jdom.Element;

public class ImageItem {
	private String ImageTitle;
	private String ImageThumbURL;
	private String ImageURL;
	private String ImageRefererURL;

	public ImageItem() {
		super();
	}

	public String getImageTitle() {
		return ImageTitle;
	}

	public void setImageTitle(String imageTitle) {
		ImageTitle = imageTitle;
	}

	public String getImageThumbURL() {
		return ImageThumbURL;
	}

	public void setImageThumbURL(String imageThumbURL) {
		ImageThumbURL = imageThumbURL;
	}

	public String getImageURL() {
		return ImageURL;
	}

	public void setImageURL(String imageURL) {
		ImageURL = imageURL;
	}

	public String getImageRefererURL() {
		return ImageRefererURL;
	}

	public void setImageRefererURL(String imageRefererURL) {
		ImageRefererURL = imageRefererURL;
	}

	@Override
	public String toString() {
		return "ImageItem [ImageTitle=" + ImageTitle + ", ImageThumbURL=" + ImageThumbURL + ", ImageURL=" + ImageURL + ", ImageRefererURL=" + ImageRefererURL + "]";
	}
	
	public Element toElement(){
		Element element = new Element("ImageItem");
		
		Element _ele0 = new Element("ImageTitle");
		_ele0.addContent(this.getImageTitle());
		element.addContent(_ele0);
		
		Element _ele1 = new Element("ImageThumbURL");
		_ele1.addContent(this.getImageThumbURL());
		element.addContent(_ele1);
		
		Element _ele2 = new Element("ImageRefererURL");
		_ele2.addContent(this.getImageRefererURL());
		element.addContent(_ele2);
	
		return element;
	}

}
