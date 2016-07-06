package com.zjlp.face.web.component.solr;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public interface SolrService {
	
	/**
	 * 
	 * @Title: setHttpSolrServer 
	 * @Description: 切换仓库用：与spring-beans.xml里的httpSolrServers对应，0开始。
	 * 在filfter里配置：http://192.168.1.120:8080/solr/goods,http://192.168.1.120:8080/solr/lists
	 * 以","隔开，结尾不能加","
	 * @param i
	 * @date 2015年3月11日 下午4:00:21  
	 * @author liujia
	 */
	public void setHttpSolrServer(Integer i);
	
	/**
	 * 
	 * @Title: addDoc 
	 * @Description: 数据组合 (不推荐使用)
	 * @param object
	 * @param methodNamesMap
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @date 2015年3月12日 下午7:24:45  
	 * @author liujia
	 */
	@Deprecated
	public SolrInputDocument addDoc(Object object,Map<String,String> methodNamesMap) 
			throws Exception;
	
	/**
	 * @Description: 添加索引
	 * @param id：商品id
	 * @date 2015年3月11日 下午1:07:48  
	 * @author liujia
	 */
	@Deprecated
	public  void addObjectSolr(Object object,Map<String,String> methodNamesMap) 
			throws Exception;
	/**
	 * 
	 * @Title: addObjectSolr 
	 * @Description: 添加某项索引
	 * @param doc：doc.addField(name, value); name 与sorl中的schema.xml里<field name="id".../>name相对应 ;value为添加进去的值
	 * 如doc.addField("id", good.getId())
	 * @date 2015年3月11日 下午3:55:21  
	 * @author liujia
	 */
	public void addObjectSolr(SolrInputDocument doc)  throws Exception;
	/**
	 * 
	 * @Title: delObjectsSolr 
	 * @Description: 删除某项索引
	 * @param id：schema.xml里<field name="id".../>里id所对应的值
	 * @date 2015年3月11日 下午3:58:59  
	 * @author liujia
	 */
	public void delObjectsSolr(String id)  throws Exception;
	
	/**
	 * 
	 * @Title: clearAll 
	 * @Description:清空索引，慎用
	 * @date 2015年3月11日 下午3:59:58  
	 * @author liujia
	 */
	 public void clearAll()  throws Exception;
	 
	 /**
	  * 
	  * @Title: addObjectsSolr 
	  * @Description: 批量添加索引
	  * @param ids：商品id集合
	  * @date 2015年3月11日 下午1:11:46  
	  * @author liujia
	  */
	@Deprecated
	public  void addObjectsSolr(List<?> objects,Map<String,String> methodNamesMap)  
			throws Exception;
	
	/**
	 *  @Description: 更新索引
	 * @param id：商品id
	 * @date 2015年3月11日 下午1:12:42  
	 * @author liujia
	 */
	@Deprecated
	public  void updateObjectSolr(Object object,Map<String,String> methodNamesMap,String id)  
			throws Exception;
	
	/**
	 * 
	 * @Title: Search 
	 * @Description: 查找索引
	 * @param start：开始数
	 * @param rows：列出数
	 * @param queryStrings：精确、模糊查询等参数 中间用AND、OR、NOT。如："shop_type_ids:*1,2 AND id:1"，没有条件传 *:*
	 * @param fqStrings:date等范围参数 ：用 TO 如：update_time:[2014-07-24T6:59:59.999Z TO *],sales_volume:[0 TO 200]。没有条件传 ""
	 * @return
	 * @date 2015年3月12日 下午7:21:56  
	 * @author liujia
	 */
	 public  SolrDocumentList Search(Integer start,Integer rows,String queryStrings,String[] fqStrings,List<SortClause> sorts)  throws Exception;
	 
	/*
	* 查找索引（有高亮）还未实现
	*/
//	 public  SolrDocumentList Search(Integer start,Integer rows,String queryString,String color);
	 

	 /**
	  * 
	  * @Title: ListSolrDocument 
	  * @Description: 列出数据中的一项的所有值
	  * @param solrDocumentList
	  * @param serchObject
	  * @return
	  * @date 2015年3月12日 下午7:21:42  
	  * @author liujia
	  */
	@Deprecated
	public List<String> ListSolrDocument(SolrDocumentList solrDocumentList,String serchObject);
	

	/**
	 * 
	 * @Title: ListSolrDocument 
	 * @Description:  列出数据中的一项 格式为：{id=2, shopno=111111111...}
	 * @param solrDocumentList
	 * @return {id=2, shopno=111111111...}
	 * @date 2015年3月12日 下午7:21:31  
	 * @author liujia
	 */
	@Deprecated
	public List<String>  ListSolrDocument(SolrDocumentList solrDocumentList);
	
	/**
	 * 
	 * @Title: Search 
	 * @Description: 查找索引
	 * @param object:查找对象
	 * @param start：开始数
	 * @param rows：列出数
	 * @param queryStrings：精确、模糊查询等参数 中间用AND、OR、NOT。如："shop_type_ids:*1,2 AND id:1"，没有条件传 *:*
	 * @param fqStrings:date等范围参数 ：用 TO 如：update_time:[2014-07-24T6:59:59.999Z TO *],sales_volume:[0 TO 200]。没有条件传 ""
	 * @return Object：返回对象 
	 * @throws Exception
	 * @date 2015年3月24日 上午9:33:22  
	 * @author liujia
	 */
	public  List<Object> Search(Object object,Integer start,Integer rows,String queryStrings,String[] fqStrings,List<SortClause> sorts)  throws Exception;


	

}
