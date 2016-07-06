package com.zjlp.face.web.util.template;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class LoadTempalteData {
	  
	private static final String FILE_SCHEME = "file:/";

	
	private Logger _log = Logger.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	public <T> List<T> loadTempalteDatas(Class<? extends T> clazz, Integer type) throws Exception {
		_log.info("loadTemplateDatas开始");
		URL filePath = new URL(getFilePath(type));
		_log.info("filePath:"+filePath.getPath());
		Map<String, List<? extends Object>>  templateMap = TemplateConfigParseUtil.getInstatnce().parseXml(filePath, clazz);
		List<T> templateList = (List<T>) templateMap.get(clazz.getName().toUpperCase() + "S");
		_log.info("loadTemplateDatas！结束");
		return templateList;
	}
	
	public String getFilePath(Integer type) throws MalformedURLException {
		String url = null;
		String strURL = getClass().getResource("LoadTempalteData.class").toString();
        strURL = strURL.substring(strURL.indexOf("/"),strURL.lastIndexOf("/com/zjlp/face/web"));
        StringBuffer urlBuffer = new StringBuffer();
		switch (type) {
		case 1:
			url = urlBuffer.append(FILE_SCHEME).append("/").append(strURL).append("/data/initTemplateData.xml").toString();
			break;
		case 2:
			url = urlBuffer.append(FILE_SCHEME).append("/").append(strURL).append("/data/initBaseModularData.xml").toString();
			break;
		default:
			break;
		}
		return url;
	}
	public static void main(String[] args) throws Exception {
		LoadTempalteData loadTempalteData = new LoadTempalteData();
//		loadTempalteData.loadTempalteDatas(OwTemplateDto.class);
		System.out.println(loadTempalteData.getFilePath(1));
		System.out.println(new URL(loadTempalteData.getFilePath(1)));
	}
}
