package com.zjlp.face.web.component.solr.order;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.common.SolrDocumentList;

import com.zjlp.face.web.server.trade.order.domain.SalesOrder;


public interface SalesOrderSolr {

	/**
	 * 
	 * @Title: addOrderSolr 
	 * @Description: 添加订单索引
	 * @param id：订单id
	 * @date 2015年3月11日 下午1:07:48  
	 * @author liujia
	 */
	public  void addOrderSolr(String orderNo)  throws Exception;
	
	/**
	 * 
	 * @Title: delOrderSolr 
	 * @Description: 删除单个订单索引 
	 * @param id：订单id
	 * @date 2015年3月11日 下午1:08:15  
	 * @author liujia
	 */
	public void delOrderSolr(String orderNo)  throws Exception;
	
	
	/**
	 * @Title: clearAllOrderSolr 
	 * @Description: 清空订单索引,慎用 (测试用，solrTime没更新的) 
	 * @date 2015年3月11日 下午1:08:51  
	 * @author liujia
	 */
	@Deprecated
	 public void clearAllOrderSolr()  throws Exception;
	 

	 /**
	  * 
	  * @Title: addOrdersSolr 
	  * @Description: 批量添加订单索引
	  * @param ids：订单id集合
	  * @date 2015年3月11日 下午1:11:46  
	  * @author liujia
	  */
	public  void addOrdersSolr(List<String> orderNos)  throws Exception;
	

	/**
	 * 
	 * @Title: updateOrderSolr 
	 * @Description: 更新订单索引
	 * @param id：订单id
	 * @date 2015年3月11日 下午1:12:42  
	 * @author liujia
	 */
	public  void updateOrderSolr(String orderNo)  throws Exception;
	
	/**
	 * 
	 * @Title: SearchOrderSolr 
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
	 public  SolrDocumentList SearchOrderSolr(Integer start,Integer rows,String queryStrings,String[] fqStrings,List<SortClause> sorls)  throws Exception;
	 

	 /**
	  * 
	  * @Title: ListSolrDocument 
	  * @Description:列出数据中的一项的集合。
	  * @param solrDocumentList:solrDocumentList:SearchOrderSolr搜索出的结果
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
	 * @param solrDocumentList:SearchOrderSolr搜索出的结果
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
	 * @param object:SalesOrder salesOrder:new一个的即可
	 * @param start：开始数
	 * @param rows：列出数
	 * @param queryStrings：精确、模糊查询等参数 中间用AND、OR、NOT。如："shop_type_ids:*1,2 AND id:1"，没有条件传 *:*
	 * @param fqStrings:date等范围参数 ：用 TO 如：update_time:[2014-07-24T6:59:59.999Z TO *],sales_volume:[0 TO 200]。没有条件传 ""
	 * @return Object：返回对象 
	 * @throws Exception
	 * @date 2015年3月24日 上午9:33:22  
	 * @author liujia
	 */
	public  List<SalesOrder> SearchOrderSolr(SalesOrder salesOrder,Integer start,Integer rows,String queryStrings,String[] fqStrings,List<SortClause> sorts)  throws Exception;
	
	
}
