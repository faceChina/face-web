package com.zjlp.face.web.server.user.shop.factory.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.ShopException;
import com.zjlp.face.web.server.operation.marketing.producer.MarketingProducer;
import com.zjlp.face.web.server.operation.member.producer.MemberProducer;
import com.zjlp.face.web.server.product.material.producer.AlbumProducer;
import com.zjlp.face.web.server.product.template.producer.TemplateProducer;
import com.zjlp.face.web.server.trade.account.producer.AccountProducer;
import com.zjlp.face.web.server.user.security.producer.RoleProducer;
import com.zjlp.face.web.server.user.security.service.PermissionService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDto;
import com.zjlp.face.web.server.user.shop.factory.GenerateCode;
import com.zjlp.face.web.server.user.shop.factory.ShopFactory;
import com.zjlp.face.web.server.user.shop.service.ShopService;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.service.UserService;
import com.zjlp.face.web.server.user.weixin.domain.TemplateMessageSetting;
import com.zjlp.face.web.server.user.weixin.domain.ToolSetting;
import com.zjlp.face.web.server.user.weixin.service.TemplateMessageService;
import com.zjlp.face.web.server.user.weixin.service.ToolSettingService;

/**
 * 普通店铺生产
 * @ClassName: CommonShopFactory
 * @Description: (普通店铺生产)
 * @author zyl
 * @date 2015年3月20日 下午2:29:48
 */
@Component("commonShopFactory")
public class CommonShopFactory implements ShopFactory {
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountProducer accountProducer;
	
	@Autowired
	private TemplateProducer templateProducer;
	
	@Autowired
	private RoleProducer roleProducer;
	
	@Autowired
	private ToolSettingService toolSettingService;
	
	@Autowired
	private AlbumProducer albumProducer;
	@Autowired
	private MarketingProducer marketingProducer;
	@Autowired
	private MemberProducer memberProducer;
	@Autowired
	private TemplateMessageService templateMessageService;
	@Autowired
	private PermissionService permissionService;

	private Log log = LogFactory.getLog(getClass());
	
	private final Lock lock = new ReentrantLock();
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "ShopException" })
	public Shop addShop(ShopDto shopDto) throws ShopException{
		Integer type = shopDto.getType();
		try{
			// 1.查询注册用户
			log.info("新增普通店铺开始！");
			User user = userService.getUserByName(shopDto.getLoginAccount());
			log.info("用户的登陆账号：" + shopDto.getLoginAccount());
			AssertUtil.notNull(user, "用户不存在！");
			// 2.邀请码验证
			if(StringUtils.isNotBlank(shopDto.getOnInvitationCode())){
				Shop s = shopService.getShopByInvitationCode(shopDto.getOnInvitationCode());
				log.info("店铺的上家邀请码：" + shopDto.getOnInvitationCode());
				AssertUtil.notNull(s, "请输入正确的来源信息！");
			}
			
			// 3.生成产品编号
			String no = GenerateCode.generateShopNo(type);
			Shop shop = null;
			
			/**
			 * 验证用户下是否已生成官网
			 */
			List<ShopDto> list = shopService.findShopList(user.getId(),null,null);
			if(null==list || 0==list.size()){
				shopDto.setTwoDimensionalCode(shopService.generateTwoDimensionalCode(user.getId(), no));
				shop = Shop._generateShop(no, user, shopDto);
				log.info("店铺详情：" + shop);
				shopService.addShop(shop);
			}else{
				log.error("用户id:" + user.getId() + ",已拥有官网");
				throw new ShopException("用户id:" + user.getId() + ",已拥有官网");
			}
			
			if(Constants.SHOP_GW_TYPE==type.intValue()){
				albumProducer.createDefaultAlbumByShopNo(no);
			}
			
			//4.添加权限
			roleProducer.addShopRoleByUserId(user.getId(), type);
//			permissionService.addUserRoleRelation(user.getId(),Constants.STRING_ROLE_GW);
			
			//5.新增店铺钱包
			accountProducer.addShopAccount(shop.getNo(), shop.getCell(), shop.getInvitationCode(), null, null);
			
			//6.微信自动回复设置数据初始化
			ToolSetting toolSetting = ToolSetting.initToolSetting(shop.getNo(), new Date());
			toolSettingService.addToolSetting(toolSetting);
			
			//7.初始化模板
			templateProducer.initTemplate(no, type);
			//8.初始化营销工具
			//查询店铺个数  如果只有一个,则初始化营销工具,如果有多个,则不进行初始化
			List<Shop> shopList = shopService.findShopListByUserId(user.getId());
			if (null != shopList && shopList.size() == 1) {
				marketingProducer.initMarketingTool(user.getId());
				memberProducer.initMemberEnactment(user.getId());
			}
			// 9.微信模板消息设置数据初始化
			TemplateMessageSetting templateMessageSetting = TemplateMessageSetting.init(shop.getNo(), new Date());
			templateMessageService.addTemplateMessageSetting(templateMessageSetting);
			
			log.info("新增普通店铺结束！");
			return shop;
		}catch(ShopException e){
			log.error(e.getMessage(), e);
			throw e;
		}catch(Exception e){
			log.error(e.getMessage(), e);
			throw new ShopException(e.getMessage(),e);
		}
	}
	

	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "ShopException" })
	public Shop addShopLock(ShopDto shopDto) throws ShopException{
		//上锁
        lock.lock();
        try {
            //保证线程安全操作代码
        	return this.addShop(shopDto);
        } catch(Exception e) {
        	throw new ShopException(e.getMessage(),e);
        } finally {
            lock.unlock();//释放锁
        }
	}
}
