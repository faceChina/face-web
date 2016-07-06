package com.zjlp.face.web.util;



public class AssConstantsUtil {
	
    /**
     * 类型： 2、借记卡 3、信用卡
     */
    /** 2:借记卡 */
    public static final Integer BANKCARD_TYPE_DEBIT = Integer.valueOf(2);

    /** 3:信用卡 */
    public static final Integer BANKCARD_TYPE_CREDITED = Integer.valueOf(3);
    /**官网 */
    public static final Integer SHOP_GW = 1;
    /**商城 */
    public static final Integer SHOP_SC = 2;
    /**脸谱 */
    public static final Integer SHOP_LP = 3;
	/** 请求成功*/
    public static final int REQUEST_OK_CODE = 0;
    /**免费店铺**/
    public static final int ROLE_FREE_SHOP = 0;
    /**内部供应商**/
    public static final int ROLE_SUPPLIER_INTERNAL = 1;
    /**外部供应商**/
    public static final int ROLE_SUPPLIER_EXTERIOR = 2;
    /**内部分销商**/
    public static final int ROLE_DISTRIBUTOR_INTERNAL = 3;
    /**外部分销商**/
    public static final int ROLE_DISTRIBUTOR_EXTERIOR = 4;
    
    /**待付款**/
    public static final int ORDER_STATE_1 = 1;
    /**已付款**/
    public static final int ORDER_STATE_2 = 2;
    /**已发货 **/
    public static final int ORDER_STATE_3 = 3;
    /**已收货**/
    public static final int ORDER_STATE_4 = 4;
    
    
    
    /**
     * 系统通用错误编码(错误编码段1000-1099)
    * @ClassName: System
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author wxn
    * @date 2015年5月8日 下午4:01:07
    *
     */
    public static final class System{
        /** 错误的sessionID */
        public static final int SESSIONID_ERROR_CODE = 1000;
        /** session过期 */
        public static final int SESSION_INVALID_CODE = 1001;
        
        /** 错误的类型 */
        public static final int TYPE_ERROR_CODE = 1002;
        /**数据错误 */
        public static final int DATA_ERROR_CODE = 1003;
        /**服务器内部错误 */
        public static final int SERVER_ERROR_CODE = 1004;
        /**验证码错误 */
        public static final int AUTH_CODE_ERROR_CODE = 1005;
        /**操作失败**/
        public static final int  REQUEST_FAILED= 1006;  
        /**接口关闭**/
		public static final int INTERFACE_CLOSE = 9999;
		/** 登录号与绑定号不匹配 **/
		public static final int PHONE_MISMATCHES_CLOSE = 1007;
		/** 设置支付密码失败 **/
		public static final int STT_PAY_PWD_FAIL_CLOSE = 1008;
		/** 不支持的Emoji表情 **/
		public static final int UNSUPPORTED_EMOJI_ERROR = 1010;
    }
	/**
	 * 用户管理模块(错误编码段1100-1199)
	* @ClassName: UserManager
	* @Description: TODO(这里用一句话描述这个类的作用)
	* @author wxn
	* @date 2015年5月8日 下午3:35:26
	 */
	public static final class UserCode{
		/** 用户名或密码错误 */
	    public static final int LOGIN_ERROR_CODE = 1100;
	    /** 用户名不存在 */
	    public static final int USERNAME_ERROR_CODE = 1101;
	    /** 手机号码不存在 */
	    public static final int  PHOEN_ERROR_CODE= 1102;
	     
	    /**注册用户名已存在**/
	    public static final int LOGIN_ACCOUNT_EXIST_CODE = 1103;
	     
	    /**用户名密码相同**/
	    public static final int LOGIN_ACCOUNT_PWD_SAME_CODE = 1104;
	    
	    /**手机号没有被绑定 */
	    public static final int PHONE_NO_BINDING_CODE = 1105;
	    
	    /**帐号与手机号码错误 */
	    public static final int PHONE_OR_ACCOUNT_ERROR_CODE = 1106;

	    /**账号不存在 */
	    public static final int ACCOUNT_ERROR_CODE = 1107;
	    
	    /**该用户的pushuserId已存在*/
	    public static final int METAQ_PUSHID_EXIST_CODE = 1108;
	     
	    /**修改密码 ：新密码与确认密码不一致**/
	    public static final int ACCOUNT_ERROR_PAYMENTCODE_CODE = 1109;
	    /**没有用户gis信息**/
	    public static final int NO_USERGIS_CODE = 1120;
		/** 用户对象为空 **/
		public static final int NULL_USER_CODE = 1121;
		/** 密码不能为为空 **/
		public static final int NULL_PASSWORD_CODE = 1122;
		/** 官网赋予权限出错 **/
		public static final int ADD_GUANWANG_CODE = 1123;
		/** 初始化钱包出错 **/
		public static final int INIT_WALLET_CODE = 1124;
		/**4.8assistant*/
		/** 发送验证码失败 **/
		public static final int SEND_MOBILECODE_FAIL = 1125;
		
		/** 发送验证码次数频繁 **/
		public static final int SEND_MOBILECODE_MUTIL = 1126;
		
		/** 明天再试 **/
		public static final int SEND_MOBILECODE_MUTIL_2 = 1127;
		/**4.8assistant*/
		/**脸谱号已存在*/
		public static final int EXIST_LP_NO = 1128;
		/**已修改过脸谱号**/
		public static final int UPDATED_LP_NO = 1129;
		/** 联系人为空 **/
		public static final int NULL_CONTACTS_CODE = 1130;
		/** 请输入正确的邀请码*/
		public static final int INVITATION_CODE_ERROR = 1131;
	}
	/**
	 * 商品管理模块(错误编码段1200-1299)
	* @ClassName: GoodManager
	* @Description: TODO(这里用一句话描述这个类的作用)
	* @author wxn
	* @date 2015年5月8日 下午3:35:36
	 */
	public static final class GoodCode {
		/** 商品上架失败 **/
		public static final int ERROR_UP_SHELVES = 1200;
		/** 商品库存为0不能上架 **/
		public static final int DENIE_UP_SHELVES = 1201;
		/** 分店ID为空 **/
		public static final int NULL_SUB_ID_CODE = 1202;

	}
	
	/**
	 * 订单管理模块(错误编码段1300-1399)
	* @ClassName: OrderManager
	* @Description: TODO(这里用一句话描述这个类的作用)
	* @author wxn
	* @date 2015年5月8日 下午3:35:48
	 */
	public static final class OrderCode{
	    /** 订单状态错误 */
	    public static final int STATUS_ERROR_CODE = 1300;
	    /**订单ID错误 */
	    public static final int ORDERID_ERROR_CODE = 1301;
	    /**手机号码格式不正确*/
	    public static final int FORMAT_PHOEN_ERROR_CODE = 1302;
	    /**快递单号格式不正确*/
	    public static final int FORMAT_LOGISTICS_ERROR_CODE = 1303;
		/** 订单已经支付，不能修改价格 */
		public static final int MODIFY_PRICE_DENIE_CODE = 1304;
		/** 订单已不存在 */
		public static final int ORDER_NOT_EXIST_CODE = 1305;
		/**商品库存不足*/
		public static final int NOT_ENOUGH_GOOD_CODE = 1306;
	}
	
	/**
	 * 店铺管理模块(错误编码段1400-1499)
	* @ClassName: ShopManager
	* @Description: TODO(这里用一句话描述这个类的作用)
	* @author wxn
	* @date 2015年5月8日 下午3:36:01
	*
	 */
	public static final class ShopCode {
		/** 店铺号为空 */
		public static final int NULL_SHOP_NO_CODE = 1400;
	}
	
	/**
	 * 用户钱包管理模块(错误编码段1500-1599)
	* @ClassName: AccountManager
	* @Description: TODO(这里用一句话描述这个类的作用)
	* @author wxn
	* @date 2015年5月8日 下午3:37:10
	*
	 */
	public static final class AccountCode{
	    /**我的钱包*/
	    /**未设置支付密码**/
	    public static final int ACCOUNT_NO_PAYMENT_PASSWORD_CODE = 1500;
	    /**提现次数已满**/
	    public static final int ACCOUNT_NO_WITHDRAWAL_CODE = 1501;
	    /**支付密码错误**/
	    public static final int PASSWORD_CODE = 1502;
	    /**身份证错误**/
	    public static final int IDENTITY_ERROR_CODE = 1503;
	    /**旧密码错误 **/
	    public static final int OLD_PASSWORD_CODE = 1504;
	    /**添加银行卡失败 */
	    public static final int ADD_CARD_ERROR_CODE = 1505;
	    /**平台不支持该银行卡**/
	    public static final int CARD_PLATFORM_NOT_SUPPORT = 1506;
	    /**参数accountOperation的ID未传*/
	    public static final int NO_PARAM_ID = 1507;
	}
	
	/**
	 * 聊天管理模块(错误编码段1600-1699)
	* @ClassName: ImManager
	* @Description: TODO(这里用一句话描述这个类的作用)
	* @author wxn
	* @date 2015年5月8日 下午3:36:26
	*
	 */
	public static final class ImCode{
		/**聊天记录保存失败*/
	    public static final int IM_SAVE_ERROR_CODE = 1600;
	    /**IM查询好友失败*/
	    public static final int IM_FIND_FRIENDS_ERROR_CODE = 1601;
	    /**查询聊天记录失败*/
	    public static final int IM_QUERY_MESSAGE_ERROR_CODE = 1602;
	    /**查询好友信息--好友不存在**/
	    public static final int IM_NO_FIRENDINFO_ERROR_CODE = 1603;
	}
	/**
	 * 生意圈管理模块(错误编码段1700-1799)
	* @ClassName: CircleManager
	* @Description: TODO(这里用一句话描述这个类的作用)
	* @author wxn
	* @date 2015年5月8日 下午3:36:38
	*
	 */
	public static final class CircleCode{}
    
	/**
	 * 群聊模块
	 * 
	 * @author Baojiang Yang
	 *
	 */
	public static final class MulChatCode {
		/** 更新二维码失败 */
		public static final int UPDATE_QRCODE_ERROR_CODE = 1800;
		/** 插入二维码失败 */
		public static final int INSERT_QRCODE_ERROR_CODE = 1801;
		/** 无效的二维码或者不存在 */
		public static final int INVALID_QRCODE_ERROR_CODE = 1802;
		/** 二维码已失效 */
		public static final int OVERDUE_QRCODE_ERROR_CODE = 1803;

	}

	/**
	 * 总分点模块
	 * 
	 * @author Baojiang Yang
	 * @date 2015年6月23日 上午10:58:45
	 *
	 */
	public static final class SubbeanchCode {
		/** role不能为空 */
		public static final int NULL_ROLE_ERROR_CODE = 1900;
		/** role为官网时shopNo不能为空 */
		public static final int NULL_SHOP_NO_ERROR_CODE = 1901;
		/** role为分店时subbranchId不能为空 */
		public static final int NULL_SUB_ID_ERROR_CODE = 1902;
		/** 店铺名不能为空 */
		public static final int NULL_SHOP_NAME_ERROR_CODE = 1903;
		/** 分店信息没有变化或者参数提交不正确 */
		public static final int NO_CHANGE_ERROR_CODE = 1904;
		/** 账号下有多个实体店铺，脏数据，操作失败！ */
		public static final Long MUL_SHOP_ERROR_CODE = -1L;
		/** 账号下下有多个分店铺,脏数据,操作失败！ */
		public static final Long MUL_SUB_ERROR_CODE = -2L;
		/** 用户不存在 */
		public static final Long NULL_USER_ERROR_CODE = 1905L;
		/** 总店不存在*/
		public static final Long NULL_SHOP_ERROR_CODE = 1906L;
		/** 已经是总店的分店 */
		public static final Long EXIST_SHOP_ERROR_CODE = 1907L;
		/** 已经是分店的分店 */
		public static final Long EXIST_SUBBRANCH_ERROR_CODE = 1908L;
		/** 分店不存在 */
		public static final Long NULL_SUBBRANCH_ERROR_CODE = 1909L;
		/** 账号不存在 */
		public static final int NULL_PHONE_NO_ERROR_CODE = 1910;
		/** 更新店铺图片失败 */
		public static final int UPDATE_LOGO_ERROR_CODE = 1913;
		/** 操作者ID不能为空 */
		public static final int NULL_USER_ID_ERROR_CODE = 1914;
		/** 无权限操作 */
		public static final Long NO_PER_ERROR_CODE = 1916L;
		/** 解除分店的id不存在 */
		public static final int NO_UNBINDID_ERROR_CODE = 1917;
		/** 没有解除分店的权限 */
		public static final int NO_RIGHT_UNBINDID_ERROR_CODE = 1918;
		/** 解除分店失败 */
		public static final int NO_UNBINDID_FAIL_ERROR_CODE = 1919;
		/** 多个挂网数据或者官网不存在 */
		public static final int DITY_DATA_ERROR_CODE = 1920;
		/** 佣金比例小于0或者大于100 */
		public static final int INVALID_PROFIT_ERROR_CODE = 1921;
		/** 无效的status */
		public static final int INVALID_STATUS_ERROR_CODE = 1922;
	}
	
	/**
	 * 经营数据模块
	 * @ClassName: OperateData 
	 * @Description: (这里用一句话描述这个类的作用) 
	 * @author lys
	 * @date 2015年7月22日 上午10:01:13
	 */
	public static final class OperateData {
		/** 没有是否为刷新的参数 */
		public static final int NULL_PARAM_ISREFRESH = 2001;
		public static final int NULL_PARAM_SUBBRANCHID = 2002;
	}

	public static final class AppoinmentOrderCode {
		public static final int NULL_PARAM_SHOPNO = 2101;
		public static final int NULL_PARAM_ORDERNO = 2102;
	}

	/**
	 * 客户管理模块
	 * 
	 * @author Baojiang Yang
	 * @date 2015年7月28日 下午2:08:58
	 *
	 */
	public static final class CustomerCode {
		/** 店铺类型为空 */
		public static final int NULL_SHOP_TYPE_CODE = 2200;
		/** 总店或者分店ID为空 */
		public static final int NULL_SUB_SHOP_ID_CODE = 2201;
		/** 客户ID为空 */
		public static final int NULL_COSTOMER_ID_CODE = 2202;
		/** 用户名下有多个店铺 */
		public static final int DIRTY_SHOP_ERROR_CODE = 2203;
		/** 客户不从存在 */
		public static final int NO_COSTOMER_ERROR_CODE = 2204;
		/** 当前分组不包含目标分组 */
		public static final int NO_CONTAIN_ERROR_CODE = 2205;
		/** 类型type不能为空 */
		public static final int NULL_TYPE_ERROR_CODE = 2206;
		/** 组名不能为空 */
		public static final int NULL_GROUP_NAME_CODE = 2207;
		/** 分组不存在 */
		public static final int NULL_GROUP_ERROR_CODE = 2208;
		/** 不能删除自己分组 */
		public static final int NO_PERSION_ERROR_CODE = 2209;
		/** 分组权重不能为空 */
		public static final int NULL_SORT_ERROR_CODE = 2210;
		/** 分组已经存在 */
		public static final int EXIST_GROUP_ERROR_CODE = 2211;
		/** 分组名为空 */
		public static final int NULL_GROUP_NAME_ERROR_CODE = 2212;
		/** 分组ID为空 */
		public static final int NULL_GROUP_ID_ERROR_CODE = 2213;

	}
	
	/**
	 * 商品分类
	 * @author 熊斌
	 * 2015年8月18日下午2:30:21
	 * @version 1.0
	 */
	public static final class ShopType{
		/** 店铺No为空 */
		public static final int NULL_SHOP_NO_ERROR_CODE = 2300;
		/** 分类名称为空 */
		public static final int NULL_SHOP_TYPE_NAME_ERROR_CODE = 2301;
		
	}
	
	/**
	 * 会员类
	 * @ClassName: Member 
	 * @Description: (这里用一句话描述这个类的作用) 
	 * @author lys
	 * @date 2015年9月8日 下午2:12:03
	 */
	public static final class Member{
		/** 用户id为空 */
		public static final int NULL_USERID_CODE = 1001;
		/** 店铺赠送用户积分为空 */
		public static final int NULL_INTEGRAL_CODE = 1002;
		/** 店铺赠送用户积分时，发生异常 */
		public static final int SEND_SYSERR_CODE = 1003;
		/** 查找赠送积分列表异常 */
		public static final int LIST_SEND_SYSERR_CODE = 1004;
		/** 查找赠送积分列表，参数用户ID为空*/
		public static final int NULL_USERID_SEND_SYSERR_CODE = 1005;
	}

	/**
	 * 活动相关
	 * 
	 * @author Baojiang Yang
	 * @date 2015年9月7日 下午5:37:58
	 *
	 */
	public static final class Activity {
		/** 设备号为空 */
		public static final int NULL_DEVICE_INFO_ERROR_CODE = 2400;

	}
	public static final class Pay {
		/** 支付密码为空 */
		public static final int PAYMENTCODE_EMPTY = 2500;
		/** 支付密码错误 */
		public static final int PAYMENTCODE_ERROR = 2501;
		/** 余额不足 */
		public static final int AMOUNT_INSUFFICIENT = 2502;
		/** 订单已支付 */
		public static final int ORDER_PAID = 2503;
	}
	public static final class BusinessCard{
		/**该名片不存在*/
		public static final int BUSSINESS_CARD_ERROR = 1011;
		/** 该名片已收藏 */
		public static final int CARDCASE_ALREADY_EXISTS = 1012;
	}
	
	/**
	 * 红包类
	 * @ClassName: Redenvelope 
	 * @Description: (这里用一句话描述这个类的作用) 
	 * @author lys
	 * @date 2015年10月14日 下午3:47:50
	 */
	public static final class Redenvelope {
		/** 红包ID为空 */
		public static final int REDENVELOPE_ID_NULL = 3001;
	}
}
