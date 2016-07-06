package com.zjlp.face.web.component.solr.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.component.solr.SolrService;
import com.zjlp.face.web.component.solr.domain.Solr;

@Service("solrService")
public class SolrServiceImpl implements SolrService {
	
	@Autowired
	private  Solr solr;
	
	private HttpSolrServer httpSolrServer;
	
	

	private  String[] getHttpSolrServers(){
		String httpSolrServers = solr.getHttpSolrServers();
		AssertUtil.notNull(httpSolrServers,"httpSolrServers不能为空,数据插入失败！");
		if(httpSolrServers.contains(",")){
			return solr.getHttpSolrServers().split(",");
		}else{//只有一个参数的时候，返回一个的数组
			return new String[]{httpSolrServers};
		}
		
	}
	@Override
	public void setHttpSolrServer(Integer i) {
		String[] httpSolrServers = getHttpSolrServers();
		this.httpSolrServer = new HttpSolrServer(httpSolrServers[i]);
	}
	/*
	 * 数据组合
	*/ 
	@Override
	@Deprecated
	public SolrInputDocument addDoc(Object object,Map<String,String> methodNamesMap) 
			throws Exception {
			SolrInputDocument doc = new SolrInputDocument();
			for (Map.Entry<String, String> entry : methodNamesMap.entrySet()) {  
	
//				System.out.println(object.getClass().getMethod(entry.getValue()).invoke(object, null));
				Object[] param = null;
	            doc.addField(entry.getKey(), object.getClass().getMethod(entry.getValue()).invoke(object, param));
	        }  

			return doc;
	}
	


	/*
	 * 添加索引
	 */
	@Override
	@Deprecated
	public  void addObjectSolr(Object object,Map<String,String> methodNamesMap) 
			throws Exception{
		//是否插入成功
		AssertUtil.notNull(object,"object不能为空,数据插入失败！");
			//插完数据库建solr
		SolrInputDocument doc = null;			
		doc = addDoc(object,methodNamesMap);
		httpSolrServer.add(doc);
		httpSolrServer.commit();
		System.out.println("----索引创建完毕!!!----");				
	}
	
	@Override
	public void addObjectSolr(SolrInputDocument doc) throws Exception{
		AssertUtil.notNull(doc,"doc不能为空");
		httpSolrServer.add(doc);
		httpSolrServer.commit();
		System.out.println("----索引创建完毕!!!----");
	}
	/*
	 * 批量添加索引
	 */
	@Override
	@Deprecated
	public  void addObjectsSolr(List<?> objects,Map<String,String> methodNamesMap) 
			throws Exception{
		for(Object object:objects){
			addObjectSolr(object,methodNamesMap);
		}
	}
	

	/*
	 * 删除索引
	 */
	@Override
	public void delObjectsSolr(String id) throws Exception{
			httpSolrServer.deleteById(id);
			httpSolrServer.commit();
			System.out.println("----索引删除完毕!!!----");		
	}
	/*
	 * 清空索引
	 */
	@Override
	 public void clearAll() throws Exception{//清空索引
			httpSolrServer.setRequestWriter(new BinaryRequestWriter());//提高性能采用流输出方式
			httpSolrServer.deleteByQuery("*:*");				
			httpSolrServer.commit();
			System.out.println("----清除索引完毕!!!----");
		}
	
	/*
	* 更新索引
	*/
	@Override
	@Deprecated
	public  void updateObjectSolr(Object object,Map<String,String> methodNamesMap,String id) 
			throws Exception{
		addObjectSolr(object,methodNamesMap);
	}
	
	/*
	* 查找索引
	*/
	@Override
	@Deprecated
	 public  SolrDocumentList Search(Integer start,Integer rows,String queryStrings,String[] fqStrings,List<SortClause> sorts) throws Exception{
        SolrQuery query = new SolrQuery();
        query.setQuery(queryStrings);
        query.setFilterQueries(fqStrings);
        query.setStart(start);
        query.setRows(rows);
        query.setSorts(sorts);
        SolrDocumentList list = null;	 
		QueryResponse rsp = httpSolrServer.query(query);
		list = rsp.getResults();		     
		return list;		 
	 }
	/*
	* 查找索引,以下高亮未调试
	*/
/*	@Override
	 public  SolrDocumentList Search(Integer start,Integer rows,String queryString,String color) {
	        SolrQuery query = new SolrQuery();
	        query.setQuery(queryString);
	        query.setStart(start);
	        query.setRows(rows);
	        SolrDocumentList list = null;	 
//以下高亮未调试	        
	 	   query.set("q", "pname:"+queryString);//高亮查询字段
		   query.setHighlight(true);//开启高亮功能
		   query.addHighlightField("pname");//高亮字段
		   query.setHighlightSimplePre("&amp;lt;font color=\""+color+"\"&amp;gt;");//渲染标签
		   query.setHighlightSimplePost("&amp;lt;/font&amp;gt;");//渲染标签
		 try {
			 QueryResponse rsp = httpSolrServer.query(query);
			 list = rsp.getResults();		     
		 } catch (SolrServerException e) {
		     e.printStackTrace();
		     System.out.println("查询数据失败！"); 
		 }
		return list;		 
	 }*/
	 
	
	/*
	 * 列出数据中的一项。
	*/
	@Override
	@Deprecated
	public List<String> ListSolrDocument(SolrDocumentList solrDocumentList,String serchObject){
		List<String> solrDocumentList2 = new ArrayList<String>();
		 for (int i = 0; i < solrDocumentList.size(); i++) {
			 SolrDocument sd = solrDocumentList.get(i);
			 String object = sd.getFieldValue(serchObject).toString();
//		     System.out.println(object);
		     solrDocumentList2.add(object);
		}
		 return solrDocumentList2;
	}
	
	/*
	 * 把数据中的条数分开
	 */
	@Override
	@Deprecated
	public List<String>  ListSolrDocument(SolrDocumentList solrDocumentList){
		List<String> solrJsonList = new ArrayList<String>();
		 for (int i = 0; i < solrDocumentList.size(); i++) {
			 SolrDocument sd = solrDocumentList.get(i);
			 String object = sd.toString().substring(12);
			 solrJsonList.add(object);
			 }
		 
		return  solrJsonList;
	}
	
	public  List<Object> Search(Object object,Integer start,Integer rows,
			String queryStrings,String[] fqStrings,List<SortClause> sorts)  throws Exception{
        SolrQuery query = new SolrQuery();
        query.setQuery(queryStrings);
        query.setFilterQueries(fqStrings);
        query.setStart(start);
        query.setRows(rows);
        query.setSorts(sorts);
        SolrDocumentList list = null;	 
		QueryResponse rsp = httpSolrServer.query(query);
		list = rsp.getResults();	     
		return _ListSolrDocument(object,list);
	};
	
	
	/**
	 * 
	 * @Title: _ListSolrDocument 
	 * @Description: solrDocumentList转List<object>
	 * @param object
	 * @param solrDocumentList
	 * @return
	 * @throws Exception
	 * @date 2015年3月24日 下午3:34:05  
	 * @author liujia
	 */
	public List<Object>  _ListSolrDocument(Object object,SolrDocumentList solrDocumentList)  throws Exception{
		List<String> solrJsonList = new ArrayList<String>();
		 for (int i = 0; i < solrDocumentList.size(); i++) {
			 SolrDocument sd = solrDocumentList.get(i);
			 String str = sd.toString().substring(12);
			 solrJsonList.add(str);
			 }
		 
		return  _ListSolrDocument(object,solrJsonList);
	}
	
	/***
	 * 
	 * @Title: _ListSolrDocument 
	 * @Description: List<String>转List<object>
	 * @param object
	 * @param solrList
	 * @return
	 * @throws Exception
	 * @date 2015年3月24日 下午3:33:47  
	 * @author liujia
	 */
	public List<Object> _ListSolrDocument(Object object,List<String> solrList) throws Exception{
		List<Object> objects = new ArrayList<Object>();
		for(String str:solrList){
			//去"{}"大括号
			StringBuffer strBuffer = new StringBuffer(str);
			strBuffer.deleteCharAt(0);
			strBuffer.deleteCharAt(strBuffer.length()-1);
			str = strBuffer.toString();
			//转换成key=Value数组，其中的值可能为key=
			String[] oneFields = str.split(",");
			//反射出方法及对象
			Class<?> c = object.getClass();
			Method[] methods = c.getMethods();
			Object obj = c.newInstance();
			//数据，赋值
			for(String oneField:oneFields){
				String[] keyValue = oneField.trim().split("=");
				String key = keyValue[0];
				String value = "";
				if(2==keyValue.length){
					value=keyValue[1];
				}
				//从str里反射出数据,并为obj相应参数赋值
				_SetValue(obj,c,methods,key,value);
				
			}
			objects.add(obj);			
		}		
		return  objects;
	}

	/***
	 * 目前只支持String\Long\Integer\Date赋值
	 * @Title: _SetValue 
	 * @Description: 实体类赋值：目前只支持String\Long\Integer\Date赋值
	 * （请确保solr里的关键字和类中的属性一样，如有不同，请在以下添加）如订单
	 * @param obj
	 * @param c
	 * @param methods
	 * @param name
	 * @param value
	 * @throws Exception
	 * @date 2015年3月24日 下午3:29:47  
	 * @author liujia
	 */
	@SuppressWarnings("deprecation")
	public void _SetValue(Object obj,Class<?> c,Method[] methods,String key,String value) throws Exception{
		for(Method method:methods){
			String methodName = method.getName();
			System.out.println(methodName);
			//订单独有
			if(methodName.equals("setOrderNo") && "id".equals(key)){
				method.invoke(obj, value);
				break;
			}
			//实体类赋值：目前只支持String\Long\Integer\Date赋值
			if(methodName.equals("set"+_firstLetterToUpper(key))){
				Field fieldX = c.getDeclaredField(key);
				if (fieldX.getType()==(String.class)){//比较同一份字节码要用等号
					method.invoke(obj, value);
				}else if(fieldX.getType()==(Long.class)){
					method.invoke(obj, Long.parseLong(value));
				}else if(fieldX.getType()==(Integer.class)){
					method.invoke(obj,Integer.parseInt(value));
				}else if(fieldX.getType()==(Date.class)){
					method.invoke(obj,new Date(value));
				}else{
					System.out.println(fieldX.getType()+"：此类型尚未配置!!!");
				}
				break;
			}
		}
	}
	/***
	 * 
	 * @Title: _firstLetterToUpper 
	 * @Description: 首字母大写
	 * @param str
	 * @return
	 * @date 2015年3月24日 下午3:32:07  
	 * @author liujia
	 */
	public  String _firstLetterToUpper(String str){
        char[] array = str.toCharArray();
        array[0] -= 32;
        return String.valueOf(array);
    }
}

