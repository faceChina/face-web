package com.zjlp.face.web.component.solr.good;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.common.SolrDocumentList;

import com.zjlp.face.web.server.product.good.domain.Good;


public interface GoodSolr {

	/**
	 * 
	 * @Title: addGoodSolr 
	 * @Description: 添加商品索引
	 * @param id：商品id
	 * @date 2015年3月11日 下午1:07:48  
	 * @author liujia
	 */
	public  void addGoodSolr(Long id)  throws Exception;
	
	/**
	 * 
	 * @Title: delGoodSolr 
	 * @Description: 删除单个商品索引 
	 * @param id：商品id
	 * @date 2015年3月11日 下午1:08:15  
	 * @author liujia
	 */
	public void delGoodSolr(Long id)  throws Exception;
	
	
	/**
	 * @Title: clearAllGoodSolr 
	 * @Description: 清空商品索引,慎用(测试用，solrTime没更新的) 
	 * @date 2015年3月11日 下午1:08:51  
	 * @author liujia
	 */
	@Deprecated
	 public void clearAllGoodSolr()  throws Exception;
	 

	 /**
	  * 
	  * @Title: addGoodsSolr 
	  * @Description: 批量添加商品索引
	  * @param ids：商品id集合
	  * @date 2015年3月11日 下午1:11:46  
	  * @author liujia
	  */
	public  void addGoodsSolr(List<Long> ids)  throws Exception;
	

	/**
	 * 
	 * @Title: updateGoodSolr 
	 * @Description: 更新商品索引
	 * @param id：商品id
	 * @date 2015年3月11日 下午1:12:42  
	 * @author liujia
	 */
	public  void updateGoodSolr(Long id)  throws Exception;
	
	/**
	 * 
	 * @Title: SearchGoodSolr 
	 * @Description: 查找索引
	 * @param start：开始数
	 * @param rows：列出数
	 * @param queryStrings：精确、模糊查询等参数 中间用AND、OR、NOT。如："shop_type_ids:*1,2 AND id:1"，没有条件传 *:*
	 * @param fqStrings:date等范围参数 ：用 TO 如：update_time:[2014-07-24T6:59:59.999Z TO *],sales_volume:[0 TO 200]。没有条件传 ""
	 * @param sorts:排序 desc:倒序 esc：正序
	 * @return
	 * @date 2015年3月11日 下午1:12:50  
	 * @author liujia
	 */
	@Deprecated
	 public  SolrDocumentList SearchGoodSolr(Integer start,Integer rows,String queryStrings,String[] fqStrings,List<SortClause> sorts)  throws Exception;

	 /**
	  * @Title: ListSolrDocument 
	  * @Description:列出数据中的一项的集合。
	  * @param solrDocumentList:solrDocumentList:SearchGoodSolr搜索出的结果
	  * @param serchObject:一项的名字
	  * @return
	  * @date 2015年3月12日 下午7:15:46  
	  * @author liujia
	  */
	@Deprecated
	public List<String> ListSolrDocument(SolrDocumentList solrDocumentList,String serchObject);
	

	/**
	 * 
	 * @Title: ListSolrDocument 
	 * @Description: 把数据取出来转换成,格式为：{id=2, shopno=111111111...},排序自己排
	 * @param solrDocumentList:SearchGoodSolr搜索出的结果
	 * @return {id=2, shopno=111111111...}
	 * @date 2015年3月12日 下午7:14:20  
	 * @author liujia
	 */
	@Deprecated
	public List<String>  ListSolrDocument(SolrDocumentList solrDocumentList);
	
	/**
	 * 
	 * @Title: Search 
	 * @Description: 查找索引
	 * @param good:查找对象,new一个即可
	 * @param start：开始数
	 * @param rows：列出数
	 * @param queryStrings：精确、模糊查询等参数 中间用AND、OR、NOT。如："shop_type_ids:*1,2 AND id:1"，没有条件传 *:*
	 * @param fqStrings:date等范围参数 ：用 TO 如：update_time:[2014-07-24T6:59:59.999Z TO *],sales_volume:[0 TO 200]。没有条件传 ""
	 * @return Object：返回对象 
	 * @throws Exception
	 * @date 2015年3月24日 上午9:33:22  
	 * @author liujia
	 */
	public <T> List<T> SearchGoodSolr(T t,Integer start,Integer rows,String queryStrings,String[] fqStrings,List<SortClause> sorts)throws Exception;
}
