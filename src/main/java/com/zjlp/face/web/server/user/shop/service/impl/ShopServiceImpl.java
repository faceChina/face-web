package com.zjlp.face.web.server.user.shop.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.Assert;
import com.zjlp.face.file.dto.FileBizParamDto;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.server.user.shop.dao.ShopDao;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.dto.FoundShopDto;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDto;
import com.zjlp.face.web.server.user.shop.domain.vo.RecommendShopVo;
import com.zjlp.face.web.server.user.shop.service.ShopService;
import com.zjlp.face.web.util.card.QRCodeUtil;

@Service
public class ShopServiceImpl implements ShopService {
	
	@Autowired
	private ShopDao shopDao;
	@Autowired
	private ImageService imageService;
	
	private Log log = LogFactory.getLog(getClass());
	
	@Override
	public List<Shop> findShopListByUserId(Long userId){
		return shopDao.findShopListByUserId(userId);
	}
	
	@Override
	public void editShop(Shop shop){
		shopDao.editShop(shop);
	}
	
	@Override
	public Shop getShopByNo(String no){
		return shopDao.getShopByNo(no);
	}

	@Override
	public Shop getShopByInvitationCode(String invitationCode){
		return shopDao.getShopByInvitationCode(invitationCode);
	}
	
	@Override
	public List<ShopDto> findShopList(Long userId, String shopNo,String shopName) {
		Assert.notNull(userId, "参数[userId]不能为空");
		ShopDto shop = new ShopDto();
		shop.setUserId(userId);
		shop.setNo(shopNo);
		shop.setName(shopName);
		return shopDao.findShopList(shop);
	}
	
	@Override
	public void addShop(Shop shop){
		shopDao.addShop(shop);
	}
	
	@Override
	public Long findSellerIdByShopNo(String shopNo) {
		return shopDao.findSellerIdByShopNo(shopNo);
	}

	@Override
	public List<Shop> findShopListByMemberCardId(Long memberCardId) {
		return shopDao.findShopListByMemberCardId(memberCardId);
	}
	@Override
	public Pagination<Shop> getFoundShop(FoundShopDto shopCriteria,
			Pagination<Shop> pagination) {
		Integer totalRows = shopDao.getPageCount(shopCriteria);
		List<Shop> datas = shopDao.getFoundShop(shopCriteria, pagination);
		pagination.setTotalRow(totalRows);
		pagination.setDatas(datas);
		return pagination;
	}

	@Override
	public Shop getShopByAutoCode(String code) {
		return shopDao.getByAuthCode(code);
	}

	@Override
	public Pagination<Shop> findRecruitShop(Pagination<Shop> pagination, String keyword) {
		Integer totalRows = this.shopDao.getRecruitShopCount(keyword);
		List<Shop> recruitShopList = this.shopDao.findRecruitShop(pagination.getStart(), pagination.getPageSize(), keyword);
		pagination.setTotalRow(totalRows);
		pagination.setDatas(recruitShopList);
		return pagination;
	}

	@Override
	public Shop getShopByUserId(Long userId) {
		return shopDao.getShopByUserId(userId);
	}

	@SuppressWarnings({ "unchecked", "static-access", "deprecation" })
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public String generateTwoDimensionalCode(Long userId, String shopNo) {
		List<FileBizParamDto> qrCordList = new ArrayList<FileBizParamDto>();
        String qrCordZoomSizes = PropertiesUtil.getContexrtParam("shopQRCordFile");
        FileBizParamDto qrCordBizParamDto = new FileBizParamDto(qrCordZoomSizes, userId, shopNo, "SHOP", shopNo, ImageConstants.SHOP_QRCORD_FILE, 1);
        qrCordBizParamDto.setImgData(_generateShopQRCodePath(shopNo));
        qrCordList.add(qrCordBizParamDto);
		String qrCordflag = imageService.addOrEdit(qrCordList);
        JSONObject qrCordjsonObject = JSONObject.fromObject(qrCordflag);
        AssertUtil.isTrue("SUCCESS".equals(qrCordjsonObject.getString("flag")), "上传图片失败:"+qrCordflag);
        String qrCordDataJson = qrCordjsonObject.getString("data");
        JSONArray qrCordJsonArray = JSONArray.fromObject(qrCordDataJson);
        List<FileBizParamDto> qrCordfbpDto = qrCordJsonArray.toList(qrCordJsonArray, FileBizParamDto.class);
        
		return qrCordfbpDto.get(0).getImgData();
	}
	
	public String _generateShopQRCodePath(String shopNo) {
		try {
			//生成二维码
			byte[] b = null;
			String wgj_url = PropertiesUtil.getContexrtParam("WGJ_URL");
			StringBuffer cardIndex = new StringBuffer();
			cardIndex = cardIndex.append(wgj_url).append("/wap/").append(shopNo).append("/any/index").append(".htm");
			byte[] qrbyte = QRCodeUtil.encode(cardIndex.toString(), b, true);
			//上传二维码
			String flag = imageService.upload(qrbyte);
			System.out.println(flag);
			JSONObject jsonObject2 = JSONObject.fromObject(flag);
			if(!"SUCCESS".equals(jsonObject2.get("flag").toString())){
				throw new Exception(jsonObject2.get("path").toString());
			}
			if(StringUtils.isBlank(jsonObject2.getString("path"))){
				throw new Exception("上传二维码失败");
			}
			return jsonObject2.getString("path");
		} catch (Exception e) {
			log.error("上传二维码失败", e);
		}
		return null;
	}

	@Override
	public List<RecommendShopVo> getRecommendShop(Long userId) {
		return shopDao.getRecommendShop(userId);
	}

	@Override
	public boolean isShopCell(String shopNo) {
		Shop shop = shopDao.getShopByNo(shopNo);
		if(shop == null){
			AssertUtil.notNull(shop, "没有该店铺");
		}
		
		if(shop.getCell()==null || "".equals(shop.getCell())){
			return false;
		}
		
		return true;
	}
}
