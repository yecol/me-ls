package act.mashup.util.Similarity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import act.mashup.global.KV;
import act.mashup.util.ChineseSegment;
import act.mashup.util.Log;

public class LoadDict {

	Map<String, String> sogou = new HashMap<String, String>();
	// 词频信息
	Map<String, String> sogou1 = new HashMap<String, String>();
	// 词性信息
	private static final LoadDict instance = new LoadDict();

	private LoadDict() {
		//System.out.println("RESOURCES:"+getClass().getResource("/").toString());
		try {
			// 字典以及词频信息加载自搜狗互联网词库
			String dictPathStr=getClass().getResource("/").toString()+KV.sogouDictPath;
			URL dictPath=new URL(dictPathStr);
			//System.out.println("RESOURCES:"+getClass().getResource("/").toString());
			File dict = new File(dictPath.toURI());
			FileInputStream fis = new FileInputStream(dict);
			BufferedInputStream bis = new BufferedInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			//System.out.println("RESOURCES:"+getClass().getResource("/").toString());
			//BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(KV.sogouDictPath)));

			// int time=0;
			boolean flag = true;
			while (flag) {
				String line = br.readLine();
				if (line == null) {
					flag = false;
					break;
				}
				String[] textArray = line.split("\t");
				// 取词频信息
				//if(textArray[3].matches("[NV]"))
				this.sogou.put(textArray[0], textArray[1]);
			}
			Log.logger.debug("Sougou Dict Loaded. size="+this.sogou.size());
			br.close();
			bis.close();
			fis.close();
		} catch (FileNotFoundException e) {
			Log.logger.fatal(e);
		} catch (IOException e) {
			Log.logger.fatal(e);
		} catch (URISyntaxException e) {
			Log.logger.fatal(e);
		}
	}

	//获得单例
	public static synchronized LoadDict getDict() {
		return instance;
	}

	public Map<String, String> getSogou() {
		return sogou;
	}

	public void setSogou(Map<String, String> sogou) {
		this.sogou = sogou;
	}

	public Map<String, String> getSogou1() {
		return sogou1;
	}

	public void setSogou1(Map<String, String> sogou1) {
		this.sogou1 = sogou1;
	}

	// 主方法，用于测试。
	public static void main(String[] args) {
		LoadDict ld = LoadDict.getDict();
		Map<String, String> map = ld.getSogou();

	}

}
