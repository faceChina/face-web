package com.zjlp.face.web.server.product.good.domain.vo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zjlp.face.jredis.client.AbstractRedisDaoSupport;
import com.zjlp.face.web.util.redis.RedisFactory;
import com.zjlp.face.web.util.redis.RedisKey;

@SuppressWarnings("all")
public class TempImg {
	
	public static final String SHOPTYPE = "SHOPTYPE";
	
	public static final String MEMBER = "MEMBER";
	
	public static final String SPLIT_LINE = "_";
	
	public static final String FILE_SEPARATOR = "/";
	
	private static final String SPLIT_KEY = "\\|";
	
	public static final String FLORD_LINE = "|";
	
	private static final String TEMPLATE = "TEMPLATE";
	
	private static final String basePath = new StringBuilder(File.separator).append("resource").append(File.separator)
			.append("base").append(File.separator).append("img").append(File.separator).append("template").toString();

	/**
	 * 获取对应的模板图片数据
	 * @Title: getList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param inputKey
	 * @return
	 * @throws IOException
	 * @date 2014年11月17日 下午7:38:56  
	 * @author Administrator
	 */
	public static Map<String, ImgTemplateVo> getTempMapByType(String inputKey) throws Exception {
		//空判断
		Map<String, ImgTemplateVo> result = new LinkedHashMap<String, ImgTemplateVo>();
		if (StringUtils.isBlank(inputKey)) {
			return result;
		}
		//缓存值获取
		Map<String, Object> map = getCachedValue();
		if (null == map || map.isEmpty()) {
			return result;
		}
		//对应图片获取
		Object value = getByKey(map, inputKey);
		//格式转换
		if (null != value && value instanceof Map) {
			List<ImgTemplateVo> list = asList((Map<String, Object>) value);
			for (ImgTemplateVo vo : list) {
				result.put(vo.getCode(), vo);
			}
			return result;
		}
		return result;
	}
	
	/**
	 * 缓存值获取
	 * @Title: getCachedValue 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param key
	 * @return   
	 * @date 2014年11月24日 下午5:01:38  
	 * @author Administrator
	 */
	private static Map<String, Object> getCachedValue() {
		Object object= null;
		//获取Redis缓存
		if (null != RedisFactory.getWgjStringHelper()) {
			return RedisFactory.getWgjStringHelper().get(RedisKey.ShopType_getCachedValue_key, 
					new AbstractRedisDaoSupport<Map<String, Object>>() {
				@Override
				public Map<String, Object> support() throws RuntimeException {
					return initImg(getDirectory(), null);
				}
			});
		} else {
			return initImg(getDirectory(), null);
		}
	}
	
	/**
	 * 提供key
	 * @Title: getKey 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param key
	 * @param fileName
	 * @return
	 * @date 2014年11月18日 上午10:11:40  
	 * @author Administrator
	 */
	public static String getKey (String key, String fileName) {
		return new StringBuilder().append(key).append(SPLIT_LINE).append(fileName).toString();
	}
	
	/**
	 * 获取图片模板基础路径
	 * @Title: getDirectory 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2014年11月17日 下午6:55:44  
	 * @author Administrator
	 */
	private static File getDirectory() {
		String strURL = TempImg.class.getClassLoader().getResource(FILE_SEPARATOR).toString();
        strURL = strURL.substring(strURL.indexOf(FILE_SEPARATOR), strURL.lastIndexOf("/WEB-INF/classes"));
        StringBuilder sb = new StringBuilder(strURL);
        sb.append("/resource/base/img/template/");
        return new File(sb.toString());
	}
	
	/**
	 * 图片模板初始化
	 * @Title: initImg 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param parentFile
	 * @param key
	 * @return
	 * @date 2014年11月17日 下午6:56:06  
	 * @author Administrator
	 */
	private static Map<String, Object> initImg(File parentFile, String key) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if (null == parentFile || !parentFile.isDirectory()) {
			return map;
		}
		StringBuilder sb = new StringBuilder();
		for (File childFile : parentFile.listFiles()) {
			sb.delete(0, sb.length());
			if (!StringUtils.isBlank(key)) {
				sb.append(key).append("_");
			} else {
				sb.append("");
			}
			String tempKey = sb.append(childFile.getName()).toString();
			boolean isDirectory = childFile.isDirectory();
			//子类型处理
			if (isDirectory) {
				Map<String, Object> temp = initImg(childFile, tempKey);
				map.put(tempKey, temp);
				continue;
			}
			//图片归类
			ImgTemplateVo vo = new ImgTemplateVo();
			String path = childFile.getAbsoluteFile().getPath();
			vo.setCode(tempKey.substring(0, tempKey.lastIndexOf(".")));
			vo.setImgPath(path.substring(path.indexOf(basePath), path.length()).replace(File.separator, FILE_SEPARATOR));
			map.put(vo.getCode(), vo);
		}
		return map;
	}
	
	/**
	 * 图片数据格式转换
	 * @Title: asList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param map
	 * @return
	 * @date 2014年11月17日 下午7:32:30  
	 * @author Administrator
	 */
	private static List<ImgTemplateVo> asList(final Map<String, Object> map) {
		List<ImgTemplateVo> list = new ArrayList<ImgTemplateVo>();
		for (Object value : map.values()) {
			if (null == value) {
				continue;
			}
			if (value instanceof Map) {
				list.addAll(asList((Map<String, Object>) value));
				continue;
			} else if (value instanceof ImgTemplateVo) {
				list.add((ImgTemplateVo) value);
			}
		}
		return list;
	}
	
	private static Object getByKey(Map<String, Object> map, String inputKey) {
		if (StringUtils.isBlank(inputKey)) {
			return null;
		}
	    String[] keys = inputKey.split(SPLIT_KEY);
	    StringBuilder tempKey = new StringBuilder();
	    Object value = null;
	    for (int i = 0; i < keys.length; i++) {
	    	if (tempKey.length() != 0) {
	    		tempKey.append(SPLIT_LINE);
	    	}
	    	tempKey.append(keys[i]);
			if (map.containsKey(tempKey.toString())) {
				value = map.get(tempKey.toString());
				if (value instanceof Map) {
					map = (Map<String, Object>) value;
				} else if (i == keys.length - 1) {   //最后一条
					return value;
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
		return map;
	}
	
	public static void main(String[] args) throws IOException {
		
	}
}
