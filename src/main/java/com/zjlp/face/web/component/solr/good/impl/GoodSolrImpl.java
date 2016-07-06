package com.zjlp.face.web.component.solr.good.impl;

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
import com.zjlp.face.web.component.solr.good.GoodSolr;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodShopTypeRelation;
import com.zjlp.face.web.server.product.good.service.GoodService;
import com.zjlp.face.web.server.product.good.service.ShopTypeService;

@Service("goodSolr")
public class GoodSolrImpl implements GoodSolr{
	
	private Logger _logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private SolrService solrService;

	
	@Autowired
	private GoodService goodService;
	
	@Autowired
	private ShopTypeService shopTypeService;



	@Override
	public void addGoodSolr(Long id)  throws Exception{
		AssertUtil.notNull(id,"ids不能为空");
		AssertUtil.isTrue((0 < id.longValue()),"id不能小于0");
		Good good = goodService.getGoodById(id);
		AssertUtil.notNull(good,"id为:"+ id +"的商品不存在，请核对！！！");
		Date solrTime = good.getSolrTime();
		Date updateTime = good.getUpdateTime();		
		if(null==solrTime || (0 < updateTime.compareTo(solrTime))){	//以后添条件 	
			SolrInputDocument doc = _getDoc(good);
			_setGoodHttpSolrServer();
			solrService.addObjectSolr(doc);
			_chanageSorlTime(id,true);
			_logger.debug("id为"+id+"solrTime更新成功");
		}else{
			_logger.info("id为"+id+"的索引已存在！");
		}		
	}

	@Override
	public void delGoodSolr(Long id) throws Exception{
		AssertUtil.notNull(id,"ids不能为空");
		AssertUtil.isTrue((0 < id.longValue()),"id不能小于0");
		_chanageSorlTime(id,false);
		_logger.debug("id为"+id+"solrTime更新成功");
		_setGoodHttpSolrServer();
		_logger.info("id为"+id+"的索引删除成功！");		
	}

	@Override
	public void clearAllGoodSolr()  throws Exception{
		_setGoodHttpSolrServer();
		solrService.clearAll();
		_logger.info("索引已全部清空！");
		
	}

	@Override
	public void addGoodsSolr(List<Long> ids)  throws Exception{
		AssertUtil.notNull(ids,"ids不能为空");
		_setGoodHttpSolrServer();
		for(Long id :ids){
			addGoodSolr(id);										
		}
		_logger.info("批量添加索引成功！");
	}

	@Override
	public void updateGoodSolr(Long id)  throws Exception{
		addGoodSolr(id);
		_logger.info("id为"+id+"的索引更新成功！");
	}

	@Override
	@Deprecated
	public SolrDocumentList SearchGoodSolr(Integer start, Integer rows,
			String queryStrings,String[] fqStrings,List<SortClause> sorts)  throws Exception{
		_setGoodHttpSolrServer();
		SolrDocumentList getGoodList= solrService.Search(start, rows, queryStrings,fqStrings,sorts);
		_logger.info("从"+start+"开始的"+rows+"条，数据查询成功！");
		return getGoodList;

	}

	@Override
	@Deprecated
	public List<String> ListSolrDocument(SolrDocumentList solrDocumentList,
			String serchObject) {
		_setGoodHttpSolrServer();
		return solrService.ListSolrDocument(solrDocumentList, serchObject);
	}
	
	@Override
	@Deprecated
	public List<String> ListSolrDocument(SolrDocumentList solrDocumentList) {
		_setGoodHttpSolrServer();
		return solrService.ListSolrDocument(solrDocumentList);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T>  List<T> SearchGoodSolr(T t,Integer start,Integer rows,String queryStrings,String[] fqStrings,List<SortClause> sorts)  throws Exception{
		_setGoodHttpSolrServer();
		List<Object> objs = solrService.Search(t,start,rows,queryStrings,fqStrings,sorts);
		_logger.info("从"+start+"开始的"+rows+"条，数据查询成功！");
		List<T> goods = new ArrayList<T>();
		for (Object obj:objs){
			goods.add((T) obj);
		}
		return goods;
	}
	
	//设置商品solr的地址
	private void _setGoodHttpSolrServer(){
		solrService.setHttpSolrServer(0);
	}
	
	//更新商品solr时间
	private void _chanageSorlTime(Long id,Boolean isAdd){
		AssertUtil.notNull(id,"id不能为空");
		AssertUtil.notNull(isAdd,"isAdd不能为空");
		Good good = new Good();
		good.setId(id);
		if(isAdd){
			good.setSolrTime(new Date());
		}else{
			good.setSolrTime(new Date(0));
		}
		goodService.edit(good);
	}
	
	//商品solr数据整合
	private SolrInputDocument _getDoc(Good good){
		AssertUtil.notNull(good,"_getDoc方法:good不能为空");
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", good.getId());
		doc.addField("shopNo", good.getShopNo());
		doc.addField("classificattonId", good.getClassificationId());
		doc.addField("goodName", good.getName());
		doc.addField("marketPrice", good.getMarketPrice());		 
		doc.addField("salesPrice", good.getSalesPrice());
		doc.addField("status", good.getStatus());
		doc.addField("inventory", good.getInventory());		 
		doc.addField("salesVolume", good.getSalesVolume());
		doc.addField("browerTime", good.getBrowerTime());		 
		doc.addField("detailWapUrl", good.getDetailWapUrl());
		doc.addField("picUrl", good.getPicUrl());		 		
		doc.addField("isOffline", good.getIsOffline());		 
		doc.addField("listTime", good.getListTime());		 
		doc.addField("delistTime", good.getDelistTime());		 
		doc.addField("createTime", good.getCreateTime());		 
		doc.addField("updateTime", good.getUpdateTime());
	
		List<GoodShopTypeRelation> goodShopTypeRelations = shopTypeService.findGoodShopTypeRelationByGoodId(good.getId());
		StringBuffer shopTypeIds = new StringBuffer();
		int count = goodShopTypeRelations.size();
		if (0<count){
			for(GoodShopTypeRelation goodShopTypeRelation:goodShopTypeRelations){
				shopTypeIds.append(goodShopTypeRelation.getShopTypeId());
				if(1!=count){
					shopTypeIds.append(",");
					count--;	
				}		
			}
			_logger.debug(shopTypeIds);
		}
		doc.addField("shopTypeIds", shopTypeIds);
		return doc;
	}
	
}
