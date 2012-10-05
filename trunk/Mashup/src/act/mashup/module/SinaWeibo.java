package act.mashup.module;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import weibo4j.Comment;
import weibo4j.Paging;
import weibo4j.Status;
import weibo4j.Weibo;

import act.mashup.global.EngineNode;
import act.mashup.global.Item;
import act.mashup.global.KV;
import act.mashup.global.Result;
import act.mashup.util.Log;

public class SinaWeibo extends AbstractListModule{
	
	String token;
	String tokenSecret;
	String type;
	Item _item;
	private static String t_getCommentsTimeline="1";
	private static String t_getPublicTimeline="2";
	private static String t_getFriendsTimeline="3";
	private static String t_getUserTimeline="4";
	private static String t_getMentions="5";
	private DateFormat dateFormat;

		


	// 供Engine调用的函数
	public void run(EngineNode en, Map<Integer, Result> results) {
		super.run(en, results);
	}
	
	@Override
	protected void Execute() throws Exception {
		if(type.equals(t_getCommentsTimeline)){
	        try {
	        	Weibo weibo = new Weibo();
	        	weibo.setToken(this.token,this.tokenSecret);
	        	List<Comment> comments = weibo.getCommentsTimeline();
	    		for(Comment comment : comments) {
	    			_item = new Item();
					_item.setValue(KV.TITLE, comment.getText());
					_item.setValue(KV.AUTHOR, comment.getUser().getScreenName());
					_item.setValue(KV.PUBLISHDATE, dateFormat.format(comment.getCreatedAt()));
					items.add(_item);
	    		}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if(type.equals(t_getFriendsTimeline)){
	        try {
	        	Weibo weibo = new Weibo();
	        	weibo.setToken(this.token,this.tokenSecret);
	        	Paging page=new Paging(1);
	        	List<Status> statuses = weibo.getFriendsTimeline(page);
				for (Status status : statuses) {
					_item = new Item();
					_item.setValue(KV.TITLE, status.getText());
					_item.setValue(KV.AUTHOR, status.getUser().getScreenName());
					_item.setValue(KV.PUBLISHDATE, dateFormat.format(status.getCreatedAt()));
					items.add(_item);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if(type.equals(t_getMentions)){
	        try {
	        	Weibo weibo = new Weibo();
	        	weibo.setToken(this.token,this.tokenSecret);
	        	List<Status> list = weibo.getMentions();
				for (Status status : list) {
					_item = new Item();
					_item.setValue(KV.TITLE, status.getText());
					_item.setValue(KV.AUTHOR, status.getUser().getScreenName());
					_item.setValue(KV.PUBLISHDATE, dateFormat.format(status.getCreatedAt()));
					items.add(_item);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if(type.equals(t_getPublicTimeline)){
	        try {
	        	Weibo weibo = new Weibo();
	        	weibo.setToken(this.token,this.tokenSecret);
	        	List<Status> statuses =weibo.getPublicTimeline();
				for (Status status : statuses) {
					_item = new Item();
					_item.setValue(KV.TITLE, status.getText());
					_item.setValue(KV.AUTHOR, status.getUser().getScreenName());
					_item.setValue(KV.PUBLISHDATE, dateFormat.format(status.getCreatedAt()));
					items.add(_item);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		else if(type.equals(t_getUserTimeline)){
	        try {
	        	Weibo weibo = new Weibo();
	        	weibo.setToken(this.token,this.tokenSecret);
	        	List<Status> statuses =weibo.getUserTimeline();
				for (Status status : statuses) {
					_item = new Item();
					_item.setValue(KV.TITLE, status.getText());
					_item.setValue(KV.AUTHOR, status.getUser().getScreenName());
					_item.setValue(KV.PUBLISHDATE, dateFormat.format(status.getCreatedAt()));
					items.add(_item);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		rlt.SetResultList(items);

	}

	@Override
	protected void Prepare() throws Exception {
		
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		System.setProperty("weibo4j.oauth.consumerKey", KV.weiboConsumerKey);
		System.setProperty("weibo4j.oauth.consumerSecret", KV.weiboConsumerSecret);
		
		token = en.getParas().getChildTextTrim("token", KV.gf);
		tokenSecret = en.getParas().getChildTextTrim("tokenSecret", KV.gf);
		type=en.getParas().getChildTextTrim("type", KV.gf);
		
		Log.logger.info("weiboConsumerKey="+KV.weiboConsumerKey);
		Log.logger.info("weiboConsumerSecret="+KV.weiboConsumerSecret);
		Log.logger.info("token="+token);
		Log.logger.info("tokenSecret="+tokenSecret);
		

	}
}
