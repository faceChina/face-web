package com.zjlp.face.web.server.product.good.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.jredis.annotation.client.RedisClient;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.component.solr.good.GoodSolr;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.server.product.good.dao.GoodDao;
import com.zjlp.face.web.server.product.good.dao.GoodPropertyDao;
import com.zjlp.face.web.server.product.good.dao.GoodSkuDao;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodProperty;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.domain.vo.GoodProfitVo;
import com.zjlp.face.web.server.product.good.domain.vo.GoodVo;
import com.zjlp.face.web.server.product.good.domain.vo.RecommendGoodVo;
import com.zjlp.face.web.server.product.good.service.GoodService;
import com.zjlp.face.web.util.Logs;
@Service
public class GoodServiceImpl implements GoodService {
	
	@Autowired
	private GoodDao goodDao;
	@Autowired
	private GoodSolr goodSolr;
	@Autowired
	private GoodSkuDao goodSkuDao ;
	@Autowired
	private GoodPropertyDao goodPropertyDao;

	@Override
	public void editGood(Good good) {
		Good g=goodDao.getById(good.getId());
		good.setShopNo(g.getShopNo());
		goodDao.editGood(good);
	}
	
	@Override
	public void edit(Good good) {
		goodDao.edit(good);
	}
	
	@Override
	public Good getGoodById(Long goodId) {
		return goodDao.getById(goodId);
	}	

	@Override
	public Pagination<GoodVo> searchPageGood(GoodVo goodVo,
			Pagination<GoodVo> pagination) throws Exception {
		String isSolrOpen = PropertiesUtil.getContexrtParam("SWITCH_SOLR_OPEN");
		if (null != isSolrOpen && "1".equals(isSolrOpen)) {
			List<SortClause> sorts = new ArrayList<SortClause>();
			sorts.add(new SortClause("id", "asc"));
			Integer totalRow = goodDao.getPageCount(goodVo);
			List<GoodVo> datas = goodSolr.SearchGoodSolr(goodVo, pagination.getStart(),  pagination.getPageSize(), "shopNo:"+goodVo.getShopNo(), null, sorts);
			pagination.setTotalRow(totalRow);
			pagination.setDatas(datas);
			return pagination;
		}else {
			Integer totalRow = goodDao.getPageCount(goodVo);
			List<GoodVo> datas = goodDao.findPageShopType(goodVo,pagination.getStart(),pagination.getPageSize());
			for(GoodVo vo:datas){
				Good g=(Good) RedisClient.getInstance().get("good_["+vo.getId()+"L]");
				if(g!=null){
					vo.setSalesVolume(g.getSalesVolume());
					vo.setInventory(g.getInventory());
				}
				if(vo.getClassificationId()!=14){
					vo.setIsPcGoods("1");
				}else{
					vo.setIsPcGoods("0");
				}
			}
			pagination.setTotalRow(totalRow);
			pagination.setDatas(datas);
			return pagination;
		}
	}
	
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Good upShelvesGood(Long goodId) throws GoodException {
		AssertUtil.notNull(goodId,"参数【goodId】为空,上架失败");
		Good good = goodDao.getById(goodId);
		AssertUtil.notNull(good,"商品不存在,上架失败");
		if(!good.getClassificationId().equals(Constants.LEIMU_MENHU)){
			AssertUtil.notTrue(0L == good.getInventory(),"库存为零，上架失败！",good.getName()+"库存为零，上架失败！");
		}
		  // 状态验证
        if(Constants.GOOD_STATUS_DEFAULT.equals(good.getStatus())){
            return good;
        }else if(Constants.GOOD_STATUS_DELETE.equals(good.getStatus())){
            throw new GoodException("已删除的订单无法修改状态");
        }
        good.setStatus(Constants.GOOD_STATUS_DEFAULT);
    	Date date = new Date();
    	//修改商品属性
    	List<GoodProperty> propertyList = goodPropertyDao.findGoodPropertyListByGoodId(goodId);
    	if (null != propertyList) {
			for (GoodProperty goodProperty : propertyList) {
				goodProperty.setStatus(Constants.GOOD_STATUS_DEFAULT);
				goodProperty.setUpdateTime(date);
	    		goodPropertyDao.edit(goodProperty);
			}
		}
        //修改商品SKU
    	List<GoodSku> skuList = goodSkuDao.findGoodSkusByGoodId(goodId);
    	//AssertUtil.notEmpty(skuList,"商品sku为空，无法下架商品");
    	GoodSku editSku = null;
    	for (GoodSku goodSku : skuList) {
    		editSku =  new GoodSku();
    		editSku.setId(goodSku.getId());
    		editSku.setStatus(Constants.GOOD_STATUS_DEFAULT);
    		editSku.setUpdateTime(date);
    		goodSkuDao.edit(editSku);
		}
    	//修改商品
    	Good editGood = new Good();
    	editGood.setId(good.getId());
    	editGood.setShopNo(good.getShopNo());
    	editGood.setStatus(Constants.GOOD_STATUS_DEFAULT);
    	editGood.setUpdateTime(date);
    	goodDao.editGood(editGood);
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Good downShelvesGood(Long goodId) throws GoodException {
        	AssertUtil.notNull(goodId,"参数【goodId】为空,下架失败");
	     	Good good = goodDao.getById(goodId);
	        AssertUtil.notNull(good,"商品不存在,下架失败");
	        // 状态验证
	        if(Constants.GOOD_STATUS_UNDERCARRIAGE.equals(good.getStatus())){
	            return good;
	        }else if(Constants.GOOD_STATUS_DELETE.equals(good.getStatus())){
				throw new GoodException("商品已删除,不能下架");
	        }else if(Constants.GOOD_STATUS_FROZEN.equals(good.getStatus())){
				throw new GoodException("商品已冻结,不能下架");
	        }else if(Constants.GOOD_STATUS_INIT.equals(good.getStatus())){
				throw new GoodException("商品异常,不能下架");
	        }
	        good.setStatus(Constants.GOOD_STATUS_UNDERCARRIAGE);
	    	Date date = new Date();
	    	//修改商品属性
	    	List<GoodProperty> propertyList = goodPropertyDao.findGoodPropertyListByGoodId(goodId);
	    	if (null != propertyList) {
				for (GoodProperty goodProperty : propertyList) {
					goodProperty.setStatus(Constants.GOOD_STATUS_UNDERCARRIAGE);
					goodProperty.setUpdateTime(date);
		    		goodPropertyDao.edit(goodProperty);
				}
			}
	    	List<GoodSku> skuList = goodSkuDao.findGoodSkusByGoodId(goodId);
	    	//AssertUtil.notEmpty(skuList,"商品sku为空，无法下架商品");
	    	GoodSku editSku = null;
	    	for (GoodSku goodSku : skuList) {
	    		editSku =  new GoodSku();
	    		editSku.setId(goodSku.getId());
	    		editSku.setStatus(Constants.GOOD_STATUS_UNDERCARRIAGE);
	    		editSku.setUpdateTime(date);
	    		goodSkuDao.edit(editSku);
			}
	    	Good editGood = new Good();
	    	editGood.setId(good.getId());
	    	editGood.setShopNo(good.getShopNo());
	    	editGood.setStatus(Constants.GOOD_STATUS_UNDERCARRIAGE);
	    	editGood.setDelistTime(date);
	    	editGood.setUpdateTime(date);
	    	goodDao.editGood(editGood);
		return good;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeGood(Long goodId) throws GoodException {
		AssertUtil.notNull(goodId,"参数【goodId】为空,删除失败");
		goodSkuDao.removeByGoodId(goodId);
		Good g=goodDao.getById(goodId);
		Good edit=new Good();
		edit.setId(goodId);
		edit.setShopNo(g.getShopNo());
		edit.setStatus(Constants.GOOD_STATUS_DELETE);
		edit.setUpdateTime(new Date());
		goodDao.editGood(edit);
	}

	@Override
	public List<Good> findGoodsList(Good good) throws GoodException {
		return goodDao.findGoodsList(good);
	}

	@Override
	public List<GoodVo> findGoodByShopTypeId(Good good, Long id, int goodNum) {
		return goodDao.findGoodByShopTypeId(good, id, goodNum);
	}

	@Override
	public GoodVo getGoodNum(GoodVo goodVo) {
		GoodVo vo = new GoodVo();
		// 查询商品总数
		Integer totalGoodNum = goodDao.getPageCount(goodVo);
		// 查询最新商品数量（近一个月）
		goodVo.setNewGoodMark(Constants.ISDEFAULT);
		Integer newGoodNum = goodDao.getPageCount(goodVo);
		vo.setTotalGoodNum(totalGoodNum);
		vo.setNewGoodNum(newGoodNum);
		return vo;
	}

	@Override
	public Integer countGoodNum(GoodVo goodVo){
		return goodDao.countGoodNum(goodVo);
	}

	@Override
	public List<Good> findGoodByIds(List<String> goodIdList) {
		return goodDao.findGoodByIds(goodIdList);
	}

	@Override
	public Pagination<GoodVo> findGoodVoPageForWap(String shopNo, GoodVo goodVo, Pagination<GoodVo> pagination) {
		return goodDao.findGoodVoPageForWap(shopNo,goodVo, pagination);
	}

	@Override
	public Integer countGoodVoNum(GoodVo goodVo) {
		return goodDao.countGoodVoNum(goodVo);
	}

	@Override
	public List<GoodVo> findGoodVoForWapByShopTypeId(Good good, Long id, int goodNum) {
		return goodDao.findGoodVoForWapByShopTypeId(good,id, goodNum);
	}

	@Override
	public Integer getCountByStatus(String shopNo, Integer status) {
		return goodDao.getCountByStatus(shopNo, status);
	}

	@Override
	public void editBrowerTimeById(Long id) {
		Good good=goodDao.getById(id);
		Good edit=new Good();
		edit.setId(id);
		edit.setBrowerTime(good.getBrowerTime()+1);
		Logs.print(edit);
		goodDao.edit(edit);
		//goodDao.editBrowerTimeById(id);
	}

	@Override
	public Pagination<RecommendGoodVo> getRecommendGood(Long userId,String goodName,
			Pagination<RecommendGoodVo> pagination) {
		return goodDao.getRecommendGood(userId,goodName, pagination);
	}
	
	@Override
	public Pagination<GoodProfitVo> findSubbranchGoods(Long subbranchId, Long userId, Integer sortBy, String searchKey,
			Pagination<GoodProfitVo> pagination) {
		Integer total = this.goodDao.countProfitGoods(subbranchId, userId, searchKey);
		pagination.setTotalRow(total);
		List<GoodProfitVo> list = this.goodDao.selectProfitGoods(subbranchId, userId, sortBy, searchKey, pagination);
		pagination.setDatas(list);
		return pagination;
	}
	
}
