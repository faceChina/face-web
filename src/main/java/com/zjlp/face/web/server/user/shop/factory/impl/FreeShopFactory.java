package com.zjlp.face.web.server.user.shop.factory.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.shop.util.GenerateCode;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.ShopException;
import com.zjlp.face.web.server.operation.marketing.producer.MarketingProducer;
import com.zjlp.face.web.server.operation.member.producer.MemberProducer;
import com.zjlp.face.web.server.product.material.producer.AlbumProducer;
import com.zjlp.face.web.server.product.template.producer.TemplateProducer;
import com.zjlp.face.web.server.trade.account.producer.AccountProducer;
import com.zjlp.face.web.server.user.security.producer.RoleProducer;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDto;
import com.zjlp.face.web.server.user.shop.factory.ShopFactory;
import com.zjlp.face.web.server.user.shop.service.ShopService;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.service.UserService;
import com.zjlp.face.web.server.user.weixin.domain.TemplateMessageSetting;
import com.zjlp.face.web.server.user.weixin.domain.ToolSetting;
import com.zjlp.face.web.server.user.weixin.service.TemplateMessageService;
import com.zjlp.face.web.server.user.weixin.service.ToolSettingService;

/**
 * 免费店铺生产
 * @ClassName: FreeShopFactory
 * @Description: (免费店铺生产)
 * @author zyl
 * @date 2015年3月20日 下午2:29:10
 */
@Component("freeShopFactory")
public class FreeShopFactory implements ShopFactory {
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountProducer accountProducer;
	@Autowired
	private ToolSettingService toolSettingService;
	@Autowired
	private MarketingProducer marketingProducer;
	@Autowired
	private MemberProducer memberProducer;
	@Autowired
	private AlbumProducer albumProducer;
	@Autowired
	private RoleProducer roleProducer;
	@Autowired
	private ImageService imageService;
	@Autowired
	private TemplateProducer templateProducer;
	@Autowired
	private TemplateMessageService templateMessageService;
	
	private Log log = LogFactory.getLog(getClass());
	
	private final Lock lock = new ReentrantLock();

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Shop addShop(ShopDto shopDto) throws ShopException {
		// 店铺类型
		Integer type = shopDto.getType();
		try {
			// 1.查询注册用户
			log.info("新增免费店铺开始！");
			User user = userService.getById(shopDto.getUserId());
			// 2.生成店铺
	        String no = GenerateCode.generateShopNo(type);
			// 生成二维码
	        shopDto.setTwoDimensionalCode(shopService.generateTwoDimensionalCode(shopDto.getUserId(), no));
	        Shop shop = Shop._generateShop(no, user, shopDto);
	        log.info("店铺详情：" + shop);
	        shopService.addShop(shop);
			// 3.产品钱包
			accountProducer.addShopAccount(shop.getNo(), shop.getCell(), shop.getInvitationCode(), null, null);
			// 4.微信自动回复设置数据初始化
			ToolSetting toolSetting = ToolSetting.initToolSetting(shop.getNo(), new Date());
			toolSettingService.addToolSetting(toolSetting);
			// 5.生成默认相册
			if(Constants.SHOP_GW_TYPE==type.intValue()){
				albumProducer.createDefaultAlbumByShopNo(no);
			}
			// 6.添加权限
			roleProducer.addShopRoleByUserId(user.getId(), Constants.SHOP_FREE_TYPE);
			// 7.查询店铺个数  如果只有一个,则初始化营销工具,如果有多个,则不进行初始化
			List<Shop> shopList = shopService.findShopListByUserId(user.getId());
			if (null != shopList && shopList.size() == 1) {
				marketingProducer.initMarketingTool(user.getId());
				memberProducer.initMemberEnactment(user.getId());
			}

			// 8.微信模板消息设置数据初始化
			TemplateMessageSetting templateMessageSetting = TemplateMessageSetting.init(shop.getNo(), new Date());
			templateMessageService.addTemplateMessageSetting(templateMessageSetting);

			// 9.初始化模板
			templateProducer.initTemplate(no, Constants.SHOP_SC_TYPE);

			log.info("新增免费店铺结束！");
            return shop;
        } catch(Exception e){
			log.error("", e);
            throw new ShopException(e);
        }
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Shop addShopLock(ShopDto dto) throws ShopException {
		//上锁
        lock.lock();
        try {
            //保证线程安全操作代码
        	return this.addShop(dto);
        } catch(Exception e) {
        	throw new ShopException(e.getMessage(),e);
        } finally {
            lock.unlock();//释放锁
        }
	}

}
