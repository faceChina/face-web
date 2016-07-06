package com.jzwgj.component.solr.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.web.component.solr.order.SalesOrderSolr;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
//@Transactional
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class SalesOrderSolrImplTest {
	@Autowired
	private SalesOrderSolr orderSolr;
	
	@Test
	public void addOrder()  throws Exception{
//		orderSolr.clearAllOrderSolr();
/*		String orderNo = "S15031619125585414";
		orderSolr.addOrderSolr(orderNo);
		String[] fg = new String[2];
		fg[0]="updateTime:[2014-07-24T00:00:00.000Z TO *] ";
		fg[1]="payPrice:[0 TO 200] ";
		List<SortClause> sorts = new ArrayList<SortClause>();
		sorts.add(new SortClause("id", "asc"));
		sorts.add(new SortClause("orderTime", "desc"));	
		SolrDocumentList list = orderSolr.SearchOrderSolr(0,10, "*:*",
				fg,sorts);//模糊搜索
		List<String> lists = orderSolr.ListSolrDocument(list,"orderTime");
		if (0 < lists.size()){
			for(String list1 : lists){
				System.out.println("list1:"+list1);
			}
		}
		orderSolr.updateOrderSolr(orderNo);
		list = orderSolr.SearchOrderSolr(0,10, "title:的",
				fg,sorts);//模糊搜索
		lists = orderSolr.ListSolrDocument(list,"orderTime");
		if (0 < lists.size()){
			for(String list1 : lists){
				System.out.println("list1:"+list1);
			}				
		}
		
		orderSolr.delOrderSolr(orderNo);
		
		List<String> orderNos = new ArrayList<String>();
		orderNos.add("S15031619125585414");
		orderNos.add("S15032314381471972");
		orderSolr.addOrdersSolr(orderNos);
		list = orderSolr.SearchOrderSolr(0,10, "title:的",
				fg,sorts);//模糊搜索
		lists = orderSolr.ListSolrDocument(list,"orderTime");
		if (0 < lists.size()){
			for(String list1 : lists){
				System.out.println("orderTime:"+list1);
			}				
		}
		
		lists = orderSolr.ListSolrDocument(list);
		if (0 < lists.size()){
			for(String list1 : lists){
				System.out.println(list1);
			}				
		}*/
		
		
		String[] fg = new String[1];
		fg[0]="";
/*		fg[0]="updateTime:[2014-07-24T00:00:00.000Z TO *] ";*/
/*		fg[1]="salesVolume:[0 TO 200] ";*/
		List<SortClause> sorts = new ArrayList<SortClause>();
		sorts.add(new SortClause("id", "asc"));
/*		sorts.add(new SortClause("updateTime", "desc"));*/	

		List<SalesOrder> salesOrders = orderSolr.SearchOrderSolr(new SalesOrder(),0,10,"*:*",fg, sorts);
		for(SalesOrder salesOrder2 :salesOrders){
			System.out.println(salesOrder2.getOrderNo());
			System.out.println(salesOrder2.getShopNo());
			System.out.println(salesOrder2.getUpdateTime());
		}
/*		StringReader sr=new StringReader(text);  
        IKSegmenter ik=new IKSegmenter(sr, true);  */
/*		orderSolr.updateSalesOrderSolr(orderNo);
		list = orderSolr.SearchSalesOrderSolr(0,10, "orderNo:"+orderNo);
		lists = orderSolr.ListSolrDocument(list,"");
		orderSolr.delSalesOrderSolr(orderNo);
		list = orderSolr.SearchSalesOrderSolr(0,10, "orderNo:"+orderNo);
		lists = orderSolr.ListSolrDocument(list,"");*/
	}

}
