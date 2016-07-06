package com.zjlp.face.web.util.template;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class TemplateConfigParseUtil {

    private Logger _logger = Logger.getLogger(this.getClass());

    private static TemplateConfigParseUtil TEMPLATECONFIGPARSE = new TemplateConfigParseUtil();

    private TemplateConfigParseUtil(){
    }

    public static TemplateConfigParseUtil getInstatnce(){
        return TEMPLATECONFIGPARSE;
    }

    @SuppressWarnings("unchecked")
    public Map<String, List<? extends Object>> parseXml(URL filePath, Class<? extends Object>[] clazzs) throws FileNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
        if(null == filePath){
            _logger.error("项目路径为空，无法获取文件信息!");
            throw new FileNotFoundException("项目路径为空，无法获取文件信息!");
        }
        try{
        	if (_logger.isDebugEnabled()) {
        		_logger.debug("【初始化模版(XML)数据】开始执行..." + "文件路径：" + filePath.getPath());
			}
            Map<String, List<? extends Object>> templateMap = new HashMap<String, List<? extends Object>>();
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(filePath);
            Element root = document.getRootElement();
            for(Class<? extends Object> clazz : clazzs){
                if(null == clazz)
                    continue;
                List<Object> tableList = new ArrayList<Object>();
                String[] temp = clazz.getName().toString().split("\\.");
                String className = temp[temp.length - 1].toUpperCase() + "S";
                Element tablesElement = root.element(className);
                for(Iterator<Element> it = tablesElement.elementIterator(); it.hasNext();){
                    Element tableElement = it.next();
                    if (_logger.isDebugEnabled()) {
                    	_logger.debug("【初始化模版(XML)数据】开始加载" + tableElement.getName() + "表数据");
					}
                    Field[] fields = clazz.getDeclaredFields();
                    Object obj = clazz.newInstance();
                    for(Field field : fields){
                        String target = field.getName().toUpperCase();
                        if(null == tableElement.element(target))
                            continue;
                        Element fieldElement = tableElement.element(target);
                        Object value = fieldElement.getText();
                        this._setter(obj, field, value);
                    }
                    if(null == obj){
                        throw new RuntimeException("对象转换失败");
                    }
                    tableList.add(obj);
                }
                templateMap.put(clazz.getName().toUpperCase() + "S", tableList);
            }
            if (_logger.isDebugEnabled()) {
            	_logger.debug("【初始化模版(XML)数据】数据加载完成");
			}
            return templateMap;
        }catch(Exception e){
            throw new RuntimeException("对象转换失败", e);
        }
    }

    @SuppressWarnings("unchecked")
	public Map<String, List<? extends Object>> parseXml(URL filePath, Class<? extends Object> clazz) throws FileNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
        if(null == filePath){
            _logger.error("项目路径为空，无法获取文件信息!");
            throw new FileNotFoundException("项目路径为空，无法获取文件信息!");
        }
        if(null == clazz) {
        	 _logger.error("获取对象名称为空，无法转换成相对应的对象!");
        	 throw new FileNotFoundException("获取对象名称为空，无法转换成相对应的对象!");
        }
        try{
        	if (_logger.isDebugEnabled()) {
        		_logger.debug("【初始化模版(XML)数据】开始执行..." + "文件路径：" + filePath.getPath());
			}
            Map<String, List<? extends Object>> templateMap = new HashMap<String, List<? extends Object>>();
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(filePath);
            Element root = document.getRootElement();
            List<Object> tableList = new ArrayList<Object>();
            String[] temp = clazz.getName().toString().split("\\.");
            String className = temp[temp.length - 1].toUpperCase() + "S";
            Element tablesElement = root.element(className);
            for(Iterator<Element> it = tablesElement.elementIterator(); it.hasNext();){
                Element tableElement = it.next();
                if (_logger.isDebugEnabled()) {
                	_logger.debug("【初始化模版(XML)数据】开始加载" + tableElement.getName() + "表数据");
				}
                Field[] fields = clazz.getDeclaredFields();
                Object obj = clazz.newInstance();
                for(Field field : fields){
                    String target = field.getName().toUpperCase();
                    if(null == tableElement.element(target))
                        continue;
                    Element fieldElement = tableElement.element(target);
                    Object value = fieldElement.getText();
                    this._setter(obj, field, value);
                }
                if(null == obj){
                    throw new RuntimeException("对象转换失败");
                }
                tableList.add(obj);
            }
            templateMap.put(clazz.getName().toUpperCase() + "S", tableList);
            if (_logger.isDebugEnabled()) {
            	_logger.debug("【初始化模版(XML)数据】数据加载完成");
			}
            return templateMap;
        }catch(Exception e){
            throw new RuntimeException("对象转换失败", e);
        }
    }
    
    private void _setter(Object obj, Field field, Object value) throws Exception{
        String upName = _getMethodName(field.getName());
        Method method = obj.getClass().getMethod("set" + upName, field.getType());
        if(field.getGenericType().toString().equals("class java.lang.Integer")){
            value = Integer.valueOf((String) value);
        }
        method.invoke(obj, value);
    }

    // 把一个字符串的第一个字母大写、效率是最高的、
    private String _getMethodName(String fildeName) throws Exception{
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }


}
