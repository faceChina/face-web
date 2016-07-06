package com.zjlp.face.web.component.solr.order.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.component.solr.SolrService;
import com.zjlp.face.web.component.solr.order.SalesOrderSolr;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.service.SalesOrderService;

@Service("orderSolr")
public class SalesOrderSolrImpl implements SalesOrderSolr{
	
	private Logger _logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SolrService solrService;	
	@Autowired
	private SalesOrderService orderService;

	



	@Override
	public void addOrderSolr(String orderNo)  throws Exception{
		AssertUtil.notNull(orderNo,"orderNo不能为空");
		SalesOrder order = orderService.getSalesOrderByOrderNo(orderNo);
		AssertUtil.notNull(order,"orderNo为"+orderNo+"的订单不存在，请核对！！！");
		Date solrTime = order.getSolrTime();
		Date updateTime = order.getUpdateTime();
		if(null==solrTime || (0 < updateTime.compareTo(solrTime))){	//以后添条件 	
			SolrInputDocument doc = _getDoc(order);
			_setSalesOrderHttpSolrServer();
			solrService.addObjectSolr(doc);
			_chanageSorlTime(orderNo,true);
			_logger.debug("orderNo为"+orderNo+"solrTime更新成功");
		}else{
			_logger.info("orderNo为"+orderNo+"的索引已存在！");
		}		
	}

	@Override
	public void delOrderSolr(String orderNo)  throws Exception{
		AssertUtil.notNull(orderNo,"orderNo不能为空");
		_setSalesOrderHttpSolrServer();
		solrService.delObjectsSolr(orderNo);
		_logger.info("orderNo为"+orderNo+"的索引删除成功！");
		_chanageSorlTime(orderNo,false);
		_logger.debug("orderNo为"+orderNo+"solrTime更新成功");	
	}

	@Override
	public void clearAllOrderSolr() throws Exception{
		_setSalesOrderHttpSolrServer();
		solrService.clearAll();
		_logger.info("索引已全部清空！");
		
	}

	@Override
	public void addOrdersSolr(List<String> orderNos)  throws Exception{
		AssertUtil.notNull(orderNos,"ordersNos不能为空");
		_setSalesOrderHttpSolrServer();
		for(String orderNo :orderNos){
			addOrderSolr(orderNo);										
		}
		_logger.info("批量添加索引成功！");
	}

	@Override
	public void updateOrderSolr(String orderNo)  throws Exception{
		addOrderSolr(orderNo);
		_logger.info("orderNo为"+orderNo+"的索引更新成功！");
	}

	@Override
	@Deprecated
	public SolrDocumentList SearchOrderSolr(Integer start,Integer rows,String queryStrings,String[] fqStrings,List<SortClause> sorts) throws Exception{
		_setSalesOrderHttpSolrServer();
		SolrDocumentList getSalesOrderList = null;
		getSalesOrderList = solrService.Search(start, rows, queryStrings,fqStrings,sorts);
		_logger.info("从"+start+"开始的"+rows+"条，数据查询成功！");
		return getSalesOrderList;

	}

	@Override
	@Deprecated
	public List<String> ListSolrDocument(SolrDocumentList solrDocumentList,
			String serchObject) {
		_setSalesOrderHttpSolrServer();
		return solrService.ListSolrDocument(solrDocumentList, serchObject);
	}
	
	@Override
	@Deprecated
	public List<String> ListSolrDocument(SolrDocumentList solrDocumentList) {
		_setSalesOrderHttpSolrServer();
		return solrService.ListSolrDocument(solrDocumentList);
	}
	
	@Override
	public  List<SalesOrder> SearchOrderSolr(SalesOrder salesOrder,Integer start,Integer rows,String queryStrings,String[] fqStrings,List<SortClause> sorts)  throws Exception{
		_setSalesOrderHttpSolrServer();
		List<Object> objs = solrService.Search(salesOrder,start,rows,queryStrings,fqStrings,sorts);
		List<SalesOrder> salesOrders = new ArrayList<SalesOrder>();
		for (Object obj:objs){
			salesOrders.add((SalesOrder)obj);
		}
		return salesOrders;
	}
	
	//设置订单solr的地址
	private void _setSalesOrderHttpSolrServer(){
		solrService.setHttpSolrServer(1);
	}
	
	//更新订单solr时间
	private void _chanageSorlTime(String orderNo,Boolean isAdd){
		AssertUtil.notNull(orderNo,"ordersNo不能为空");
		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setOrderNo(orderNo);
		if(isAdd){
			salesOrder.setSolrTime(new Date());
		}else{
			salesOrder.setSolrTime(new Date(0));
		}
		orderService.editSalesOrder(salesOrder);
	}
	
	
	//订单solr数据整合
	private SolrInputDocument _getDoc(SalesOrder order){
		AssertUtil.notNull(order,"order不能为空");
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", order.getOrderNo());//父订单的交易编号
		doc.addField("status", order.getStatus());//交易状态
		doc.addField("title", order.getTitle());//交易标题
		doc.addField("fromInner", order.getFromInner());//交易内部来源
		doc.addField("quantity", order.getQuantity());//商品购买数量
		doc.addField("price", order.getPrice());//商品单价
		doc.addField("totalPrice", order.getTotalPrice());	//应付金额	 
		doc.addField("payPrice", order.getPayPrice());//实付金额
		doc.addField("discountPrice", order.getDiscountPrice());//优惠金额		 		
		doc.addField("userId", order.getUserId());	//买家/用户ID
		
		doc.addField("shopNo", order.getShopNo());//购买店铺ID
		doc.addField("shopName", order.getShopName());//购买店铺名称 
				
		doc.addField("orderTime", order.getOrderTime());//订购时间
		doc.addField("paymentTime", order.getPaymentTime());//支付时间 
		
		doc.addField("deliveryTime", order.getDeliveryTime());//发货时间			
		doc.addField("confirmTime", order.getConfirmTime());//确认时间	
		doc.addField("closingTime", order.getClosingTime());//关闭时间	
		doc.addField("cancelTime", order.getCancelTime());//取消时间
		doc.addField("deleteTime", order.getDeleteTime());//删除时间		 
		doc.addField("timeoutTime", order.getTimeoutTime());//超时到期时间		 
		doc.addField("createTime", order.getCreateTime());//创建时间		 
		doc.addField("updateTime", order.getUpdateTime());//更新时间
		
		return doc;
	}
	
}
