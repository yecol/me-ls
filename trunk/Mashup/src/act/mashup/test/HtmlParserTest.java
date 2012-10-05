package act.mashup.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPather;
import org.htmlcleaner.XPatherException;

public class HtmlParserTest{
	public static void main(String[] ags) throws MalformedURLException, IOException, XPatherException{
		HtmlCleaner cleaner = new HtmlCleaner();
		final String siteUrl = "http://yecols.cn/blog/";	 
		TagNode node = cleaner.clean(new URL(siteUrl));
		System.out.println(node.toString());
		List children=node.getChildren();
		for(Object o:children){
			TagNode tagn=(TagNode)o;
			System.out.println(tagn.toString());
		}
		XPather xp=new XPather("/head/meta[3]");
		for(Object o:xp.evaluateAgainstNode(node)){
			TagNode tn=(TagNode)o;
			System.out.println(tn.getAttributeByName("name"));
			System.out.println(tn.toString());
		}
		System.out.println("term");
	}
}