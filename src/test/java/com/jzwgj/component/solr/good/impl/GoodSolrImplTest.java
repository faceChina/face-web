package com.jzwgj.component.solr.good.impl;

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

import com.zjlp.face.web.component.solr.good.GoodSolr;
import com.zjlp.face.web.server.product.good.domain.Good;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
//@Transactional
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class GoodSolrImplTest {
	@Autowired
	private GoodSolr goodSolr;
	
	@Test
	public void addGood() throws Exception{
//		goodSolr.clearAllGoodSolr();
/*		Long id = 25l;
		goodSolr.addGoodSolr(id);
		String[] fg = new String[2];
		fg[0]="updateTime:[2014-07-24T00:00:00.000Z TO *] ";
		fg[1]="salesVolume:[0 TO 200] ";
		List<SortClause> sorts = new ArrayList<SortClause>();
		sorts.add(new SortClause("id", "asc"));
		sorts.add(new SortClause("shopTypeIds", "desc"));		
		SolrDocumentList list = goodSolr.SearchGoodSolr(0,10, "*:*",
				fg,sorts);//模糊搜索
		List<String> lists = goodSolr.ListSolrDocument(list,"goodName");
		if (0 < lists.size()){
			for(String list1 : lists){
				System.out.println("list1:"+list1);
			}
		}
		
		lists=goodSolr.ListSolrDocument(list);
		if (0 < lists.size()){
			for(String list1 : lists){
				System.out.println(list1);
			}				
		}
		goodSolr.updateGoodSolr(id);
		list = goodSolr.SearchGoodSolr(0,10, "goodName:多少",
				fg,sorts);//模糊搜索
		lists = goodSolr.ListSolrDocument(list,"goodName");
		if (0 < lists.size()){
			for(String list1 : lists){
				System.out.println("list1:"+list1);
			}				
		}

		goodSolr.delGoodSolr(id);
		
		List<Long> ids = new ArrayList<Long>();
		ids.add(25l);
		ids.add(26l);
		goodSolr.addGoodsSolr(ids);
		list = goodSolr.SearchGoodSolr(0,10, "goodName:多少",
				fg,sorts);//模糊搜索
		lists = goodSolr.ListSolrDocument(list,"goodName");
		if (0 < lists.size()){
			for(String list1 : lists){
				System.out.println("goodName:"+list1);
			}				
		}
		
		lists = goodSolr.ListSolrDocument(list);
		if (0 < lists.size()){
			for(String list1 : lists){
				System.out.println(list1);
			}				
		}*/
		
		

/*		List<String> solrList = new ArrayList<String>();
		solrList.add("{id=25, shopNo=HZJZ0001140724232mHd, classificattonId=2, goodName=测试其它类商品, 
		marketPrice=123, salesPrice=123, status=1, inventory=10, salesVolume=0, browerTime=0, 
		detailWapUrl=, picUrl=, isOffline=1, listTime=Tue Mar 17 14:34:14 CST 2015, createTime=Tue Mar 17 14:34:14 CST 2015, 
		updateTime=Tue Mar 17 14:34:14 CST 2015, shopTypeIds=, _version_=1496500031106056192}");
		solrList.add("{id=26, shopNo=HZJZ0001140724232mHd, classificattonId=2, goodName=测试其它类商品, 
		marketPrice=123, salesPrice=123, status=1, inventory=10, salesVolume=0, browerTime=0, 
		detailWapUrl=, picUrl=, isOffline=1, listTime=Tue Mar 17 14:34:40 CST 2015, createTime=Tue Mar 17 14:34:40 CST 2015, 
		updateTime=Tue Mar 17 14:34:40 CST 2015, shopTypeIds=, _version_=1496424917805039616}");*/
		String[] fg = new String[1];
		fg[0]="";
/*		fg[0]="updateTime:[2014-07-24T00:00:00.000Z TO *] ";*/
/*		fg[1]="salesVolume:[0 TO 200] ";*/
		List<SortClause> sorts = new ArrayList<SortClause>();
		sorts.add(new SortClause("id", "asc"));
/*		sorts.add(new SortClause("updateTime", "desc"));*/	

		List<Good> goods = goodSolr.SearchGoodSolr(new Good(),0,10,"*:*",fg, sorts);
		for(Good good2 :goods){
			System.out.println(good2.getId());
			System.out.println(good2.getShopNo());
			System.out.println(good2.getUpdateTime());
		}
/*		StringReader sr=new StringReader(text);  
        IKSegmenter ik=new IKSegmenter(sr, true);  */
/*		goodSolr.updateGoodSolr(id);
		list = goodSolr.SearchGoodSolr(0,10, "id:"+id);
		lists = goodSolr.ListSolrDocument(list,"");
		goodSolr.delGoodSolr(id);
		list = goodSolr.SearchGoodSolr(0,10, "id:"+id);
		lists = goodSolr.ListSolrDocument(list,"");*/
	}

}
