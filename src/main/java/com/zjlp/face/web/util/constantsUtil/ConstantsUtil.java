package com.zjlp.face.web.util.constantsUtil;


public class ConstantsUtil {
	/** 商品最低价格 */
	public static final Long GOOD_MIN_PIRCE_PANNY = 10L;

	public static final String INIT_PASSWORD = "6224894";

	/** SUCCESS */
	public static final String SUCCESS = "SUCCESS";

	/** 订单生成最小价格定义 单位(分) */
	public static final Long ORDER_MINIMUM_PRICE_PENY = 10L;

	/**
	 * 支付类型( 0、在线支付 1、货到付款 )
	 */
	/** 0 在线支付 */
	public static final Integer PAYMENT_ONLINE = 0;

	/** 0 货到付款 */
	public static final Integer PAYMENT_LINE = 1;

	/**
	 * 支付方式( 0.银行卡支付 1.钱包支付)
	 */
	/** 1.银行卡支付 */
	public static final Integer PAYMENT_BANKCARD = 1;

	/** 2.钱包支付 */
	public static final Integer PAYMENT_WALLET = 2;

	/** 3.微信支付 */
	public static final Integer PAYMENT_WECHAT = 3;

	/**
	 * 银行卡支付回调地址配置
	 */
	/** HOST */
	public static final String SWITCH_UNIONPAY_HOST = "SWITCH_UNIONPAY_HOST";

	/** 实物订单异步回调地址 */
	public static final String SWITCH_UNIONPAY_NOTIFYURL = "SWITCH_UNIONPAY_NOTIFYURL";

	/** 实物订单同步回调地址 */
	public static final String SWITCH_UNIONPAY_RETURNURL = "SWITCH_UNIONPAY_RETURNURL";

	/** 虚拟订单异步回调地址 */
	public static final String SWITCH_UNIONPAY_VIRTUAL_NOTIFYURL = "SWITCH_UNIONPAY_VIRTUAL_NOTIFYURL";

	/** 虚拟订单同步回调地址 */
	public static final String SWITCH_UNIONPAY_VIRTUAL_RETURNURL = "SWITCH_UNIONPAY_VIRTUAL_RETURNURL";

	/** 预定订单订单异步回调地址 */
	public static final String SWITCH_UNIONPAY_RESERVE_NOTIFYURL = "SWITCH_UNIONPAY_RESERVE_NOTIFYURL";

	/** 虚拟订单同步回调地址 */
	public static final String SWITCH_UNIONPAY_RESERVE_RETURNURL = "SWITCH_UNIONPAY_RESERVE_RETURNURL";

	/** 购买方式 (1会员购 2 直接购物) */
	public static final Integer ORDER_SHOPING_WAY_MEMBER = 1;

	public static final Integer ORDER_SHOPING_WAY_QUICK = 2;

	/**
	 * 订单状态（0 生成 01、待付款 02、已付款 03、已发货 04 已收货 05、交易成功 10、已取消 11、已删除 20、退订 30
	 * 超时关闭）
	 */
	/** 0.生成 */
	public static final Integer STARTS_GENARETE = 0;

	/** 01、待付款 */
	public static final Integer STATES_WAIT = 1;

	/** 02.已付款 */
	public static final Integer STATES_PAY = 2;

	/** 03.已发货 */
	public static final Integer STATES_SEND = 3;

	/** 04.已收货 */
	public static final Integer STATES_RECEIVE = 4;

	/** 05.交易成功 */
	public static final Integer STATES_COMPILE = 5;

	/** 10.已取消 */
	public static final Integer STATES_CANCEL = 10;

	/** 11.已删除 */
	public static final Integer STATES_DELETE = 11;

	/** 20.退订 */
	public static final Integer STATES_REFUND = 20;

	/** 30.超时关闭 */
	public static final Integer STATES_CLOSE = 30;

	/**
	 * 虚拟订单状态（0 生成 01、待付款 02、已付款 03、已发货 04 交易成功 05、交易失败 10、已取消 11、已删除 20、退订 30
	 * 超时关闭）
	 * 
	 */
	public static final Integer VIR_STARTS_GENARETE = 0;

	public static final Integer VIR_STATES_WAIT = 1;

	public static final Integer VIR_STATES_PAY = 2;

	public static final Integer VIR_STATES_SENDING = 3;

	public static final Integer VIR_STATES_COMPILE = 4;

	public static final Integer VIR_STATES_FAILURE = 5;

	public static final Integer VIR_STATES_CANCEL = 10;

	public static final Integer VIR_STATES_DELETE = 11;

	/**
	 * 预定订单状态 (0、 生成 01、待付款 02、已付款 03、已发货 04 已收货 05、交易成功 10、已取消 11、已删除 20、退订 30
	 * 超时关闭)
	 */
	/** 0.生成 */
	public static final Integer RSV_STARTS_GENARETE = 0;

	/** 01、待付款 */
	public static final Integer RSV_STATES_WAIT = 1;

	/** 02.已付款 */
	public static final Integer RSV_STATES_PAY = 2;

	/** 03.已发货 */
	public static final Integer RSV_STATES_SEND = 3;

	/** 04.已收货 */
	public static final Integer RSV_STATES_RECEIVE = 4;

	/** 05.交易成功 */
	public static final Integer RSV_STATES_COMPILE = 5;

	/** 10.已取消 */
	public static final Integer RSV_STATES_CANCEL = 10;

	/** 11.已删除 */
	public static final Integer RSV_STATES_DELETE = 11;

	/** 20.退订 */
	public static final Integer RSV_STATES_REFUND = 20;

	/** 30.超时关闭 */
	public static final Integer RSV_STATES_CLOSE = 30;

	/** 推荐商品上架 */
	public static final Integer GOOD_RECOMMEND_YES = 1;

	/** 推荐商品下架 */
	public static final Integer GOOD_RECOMMEND_NO = 2;

	/**
	 * 商品状态 -1：删除，1：正常 2.冻结 3 下架
	 */
	public static final Integer GOOD_STATE_DELETE = -1;

	public static final Integer GOOD_STATE_INIT = 0;

	public static final Integer GOOD_STATE_DEFAULT = 1;

	public static final Integer GOOD_STATE_FROZEN = 2;

	public static final Integer GOOD_STATE_UNDERCARRIAGE = 3;

	/** 商品代理价格状态 -1 删除 1 可用 */

	public static final Integer GOOD_PROXY_STATE_DELETE = -1;

	public static final Integer GOOD_PROXY_STATE_DEFAULT = 1;

	/**
	 * 1.普通商品订单 2.预约订单 3.预定促销打折订单
	 */
	public static final int SALES_ORDER_TYPE_GOOD = 1;

	public static final int SALES_ORDER_TYPE_SUBSCRIBE = 2;

	public static final int SALES_ORDER_TYPE_RESERVE = 3;

	/**
	 * 商品发布者 1 自主商品发布 2 平台商品发布
	 */
	public static final Integer GOOD_PUBLICSHER_SELF = 1;

	public static final Integer GOOD_PUBLICSHER_PLATFORM = 2;

	/** 商品优惠策略（0 无优惠 1 会员优惠） */
	public static final Integer GOOD_PREFERENTIAL_POLICY_DEFAULT = 0;

	public static final Integer GOOD_PREFERENTIAL_POLICY_MEMBER = 1;

	/**
	 * 商品归属类型（0 自有商品 1 推广入驻 2 代理商品 3 分销商品 4全民推广）
	 */
	public static final Integer GOOD_BY_MYSELF = 0;

	public static final Integer GOOD_BY_PROMOTION_IN = 1;


	public static final Integer GOOD_BY_NATIONAL_PROMOTION = 4;

	/**
	 * 订单是否优惠(0 无 1有)
	 */
	public static final Integer ORDER_PREFERENTIAL_FALSE = 0;

	public static final Integer ORDER_PREFERENTIAL_TRUE = 1;

	/**
	 * 商品版本：
	 */
	public static final String INIT_GOOD_VIERSION = "v_0.010";

	/**
	 * 虚拟商品销售：101001、实物商品销售：109001、外部账户充值：108001、账户结转：102001、
	 * 账户提现：202000、商户付款：107001
	 */
	/** 101001.虚拟商品销售 */
	public static final String BUSI_PARTNER_VIRTUAL = "101001";

	/** 109001.实物商品销售 */
	public static final String BUSI_PARTNER_PHYSICAL = "109001";

	/** 108001.外部账户充值 */
	public static final String BUSI_PARTNER_EXTERNAL = "108001";

	/** 102001.账户结转 */
	public static final String BUSI_PARTNER_ACCOUNT_FORWARD = "102001";

	/** 202000.账户提现 */
	public static final String BUSI_PARTNER_WITHDRAWAL = "202000";

	/** 107001.商户付款 */
	public static final String BUSI_PARTNER_PAYMENT = "107001";

	/**
	 * 账户类型：0超级管理员钱包 1普通用户钱包 2产品收益钱包 3平台钱包 4连连钱包
	 */
	public static final Integer ACCOUNT_TYPE_SUPER = Integer.valueOf(0);

	public static final Integer ACCOUNT_TYPE_ORDINARY = Integer.valueOf(1);

	public static final Integer ACCOUNT_TYPE_PRODUCTS = Integer.valueOf(2);

	public static final Integer ACCOUNT_TYPE_PLATFORM = Integer.valueOf(3);

	public static final Integer ACCOUNT_TYPE_LIANLIAN = Integer.valueOf(4);

	/**
	 * 付款方式：1.银行卡付款 2.钱包付款
	 */
	public static final Integer PAY_TYPE_CARD = Integer.valueOf(1);

	public static final Integer PAY_TYPE_WALLET = Integer.valueOf(2);

	/**
	 * 账户金额流转操作类型 1银行卡支付 2钱包支付 3余额提现 4佣金提现 11商户收益 12手续费收益 13佣金收益 14彩票中奖
	 */
	/** 银行卡支付 */
	public static final Integer OPERATION_CARD_PYA = Integer.valueOf(1);

	/** 钱包支付 */
	public static final Integer OPERATION_BALANCE_PAY = Integer.valueOf(2);

	/** 余额提现 */
	public static final Integer OPERATION_BALANCE_THAW = Integer.valueOf(3);

	/** 佣金提现 */
	public static final Integer OPERATION_COMMISSION_THAW = Integer.valueOf(4);

	/** 商户收益 */
	public static final Integer OPERATION_INCOME = Integer.valueOf(11);

	/** 手续费收益 */
	public static final Integer OPERATION_FEE = Integer.valueOf(12);

	/** 佣金收益 */
	public static final Integer OPERATION_COMMISSION_INCOME = Integer.valueOf(13);

	/** 彩票中奖 */
	public static final Integer OPERATION_LOTTERY = Integer.valueOf(14);

	/** 15一键余额提取 */
	public static final Integer ACCOUNT_OPERATION_BALANCE_EXTRACT = 15;

	/**
	 * 商品归属类型（0、普通商品 1 推广入驻）
	 */
	/** 普通商品 */
	public static final Integer OWNERSHIP_TYPE_ORDINARY = Integer.valueOf(0);
	/** 推广入驻 */
	public static final Integer OWNERSHIP_TYPE_RECOMMEND = Integer.valueOf(1);
	/** 代理商品 */
	public static final Integer OWNERSHIP_TYPE_PROXY = Integer.valueOf(2);
	/** 分销商品 */
	public static final Integer OWNERSHIP_TYPE_DISTRIBUTION = Integer.valueOf(3);
	/** 全民推广 */
	public static final Integer OWNERSHIP_TYPE_NATIONAL_PROMOTION = Integer.valueOf(4);

	/**
	 * 权限管理 1：普通用户，2：超级用户，3：管理用户，4：微官网，5：微商城，6：微脸谱，7：首页，8:买家 ,9:匿名,10:全局,11:微平台
	 */
	public static final String ROLE_USER = "ROLE_USER";

	public static final String ROLE_ADMIN = "ROLE_ADMIN";

	public static final String ROLE_MANAGER = "ROLE_MANAGER";

	public static final String ROLE_GW = "ROLE_GW";

	public static final String ROLE_SC = "ROLE_SC";

	public static final String ROLE_LP = "ROLE_LP";

	public static final String ROlE_PF = "ROLE_PF";

	public static final String ROLE_INDEX = "ROLE_INDEX";

	public static final String ROLE_PAY = "ROLE_PAY";

	public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

	public static final String ROLE_ALL = "ROLE_ALL";

	/** 管家助手 */
	public static final String ROLE_HAA = "ROLE_HAA";

    /**
	 * 用户类型 1：普通用户，2：超级用户，3：管理用户，9：平台用户
	 */
	/** 1：普通用户 */
	public static final Integer U_USER = 1;
	/** 2：超级用户 */
	public static final Integer U_ADMIN = 2;
	/** 3：管理用户 */
	public static final Integer U_MANAGER = 3;
	/** 9：平台用户 */
	public static final Integer U_PLATFORM = 9;

	/**
	 * 产品类型 1：微官网，2：微商城，3：微脸谱 ，4：微小店，5：免费店铺，9：平台店铺
	 */
	public static final Integer SHOP_GW = 1;

	public static final Integer SHOP_SC = 2;

	public static final Integer SHOP_LP = 3;

	public static final Integer SHOP_XD = 4;

	public static final Integer SHOP_MF = 5;

	public static final Integer SHOP_PF = 9;

	/**
	 * 订单类型 1.实物订单 2.话费充值 3.彩票购买
	 */
	/** 1.实物订单 */
	public static final Integer ORDER_TYPE_SW = 1;

	/** 2.话费充值 */
	public static final Integer ORDER_TYPE_PHONE = 2;

	/** 3.彩票购买 */
	public static final Integer ORDER_TYPE_LOTTERY = 3;
	/** 4.预订 */
	public static final Integer ORDER_TYPE_RO = 4;

    /**
	 * 提现状态
	 */
	/** 提取中 */
	public static final Integer DRAWAL_STATE_ING = Integer.valueOf(5);

	/** 提取失败 */
	public static final Integer DRAWAL_STATE_FAIL = Integer.valueOf(-1);

	/** 提取成功 */
	public static final Integer DRAWAL_STATE_SUCC = Integer.valueOf(0);

    /**
	 * 银行卡状态
	 */
	/** 失效 */
	public static final Integer BANKCARD_STATES_INVALID = Integer.valueOf(0);

	/** 正常 */
	public static final Integer BANKCARD_STATES_VALID = Integer.valueOf(1);

	/** 未激活 */
	public static final Integer BANKCARD_STATES_INACTIVE = Integer.valueOf(2);

	/**
	 * 用户来源
	 */
	/** 微信 */
	public static final Integer SOURCE_WEIXIN = 1;

	/** 平台 */
	public static final Integer SOURCE_PT = 2;

	/**
	 * 用户语言
	 */
	public static final String LANGUAGE_CN = "cn";

    /**
	 * 模板CODE
	 */
	/** 微官网首页轮播图模板 */
	public static final String HP_WGW_CARL = "HP_WGW_CARL";
	/** 微官网首页模板 */
	public static final String HP_WGW = "HP_WGW";

	/** 微官网首页背景图模板 */
	public static final String HP_WGW_BG = "HP_WGW_BG";

	/** 微官网商城首页轮播图模板 */
	public static final String HP_SC_WGW_CARL = "HP_SC_WGW_CARL";
	/** 微官网商城首页模板 */
	public static final String HP_SC_WGW = "HP_SC_WGW";

	/** 微官网商城首页背景图模板 */
	public static final String HP_SC_WGW_BG = "HP_SC_WGW_BG";

	/** 微官网商城分类模板 */
	public static final String GT_SC_WGW = "GT_SC_WGW";

	/** 微商城首页轮播图模板 */
	public static final String HP_WSC_CARL = "HP_WSC_CARL";
	/** 微商城首页模板 */
	public static final String HP_WSC = "HP_WSC";

	/** 微商城背景轮播图模板 */
	public static final String HP_WSC_BG = "HP_WSC_BG";

	/** 微商城分类页模板 */
	public static final String GT_WSC = "GT_WSC";

	/** 微脸谱首页轮播图模板 */
	public static final String HP_WLP_CARL = "HP_WLP_CARL";
	/** 微脸谱首页模板 */
	public static final String HP_WLP = "HP_WLP";

	/** 微脸谱首页背景图模板 */
	public static final String HP_WLP_BG = "HP_WLP_BG";

	/** 微脸谱分类页模板 */
	public static final String GT_WLP = "GT_WLP";

	/** 微脸谱分类页模板 */
	public static final String GT_PF = "GT_PF";

	/**
	 * 银行卡用途类型
	 */
	/** 银行卡用途类型 1：支付 */
	public static final Integer BANK_USE_TYPE_PAY = Integer.valueOf(1);

	/** 银行卡用途类型 2：结算 */
	public static final Integer BANK_USE_TYPE_SETTLEMENT = Integer.valueOf(2);

	/**
	 * 银行卡的默认类型
	 */
	/** 默认 */
	public static final Integer BANKCARD_IS_DEFAULT = Integer.valueOf(1);

	/** 非默认 */
	public static final Integer BANKCARD_NOT_DEFAULT = Integer.valueOf(0);

	/**
	 * 2.快捷支付（借记卡）3.快捷支付（信用卡）
	 */
	/** 2:快捷支付（借记卡） */
	public static final Integer PAYTYPE_DEBIT = Integer.valueOf(2);

	/** 3:快捷支付（信用卡） */
	public static final Integer PAYTYPE_CREDITED = Integer.valueOf(3);

	/**
	 * 类型： 2、借记卡 3、信用卡
	 */
	/** 2:借记卡 */
	public static final Integer BANKCARD_TYPE_DEBIT = Integer.valueOf(2);

	/** 3:信用卡 */
	public static final Integer BANKCARD_TYPE_CREDITED = Integer.valueOf(3);

	/**
	 * 模块链接类型
	 */
	/** newsList */
	public static final String MODULAR_NEWSLIST = "newsList";

	/** goodType */
	public static final String MODULAR_SHOPTYPE = "shopType";

	/** goodTypes */
	public static final String MODULAR_SHOPTYPES = "shopTypes";
	/** good */
	public static final String MODULAR_GOOD = "good";

	/***
	 * 背景图，轮播图
	 */
	/** BG */
	public static final String BG = "BG";

	/** CARL */
	public static final String CARL = "CARL";

	/**
	 * 首页，商品分类页
	 */
	/** 首页 */
	public static final String HP = "HP";

	/** 分类页 */
	public static final String GT = "GT";

	/** 文章类型：1：图文 2：链接 */
	public static final Integer ARTICLE_TYPE_LINK = 2;

	/**
	 * 会员卡状态 状态 -1：删除，0：冻结，1：正常
	 */
	public static final Integer MembershipCard_STATE_DELETE = -1;

	public static final Integer MEMBERSHIPCARD_STATE_FREEZE = 0;

	public static final Integer MEMBERSHIPCARD_STATE_DEFAULT = 1;

	/**
	 * 积分规则类型
	 */
	public static final Integer INTEGRALRULE_TYPE_DEFAULT = 0;

	public static final Integer INTEGRALRULE_TYPE_HIERARCHY = 1;

	/**
	 * 手机验证码 类型
	 */
	/** 注册 */
	public static final Integer PHONE_CODE_ZC = 1;

	/** 找回密码 */
	public static final Integer PHONE_CODE_ZH = 2;

	/** 手机绑定 */
	public static final Integer PHONE_CODE_SJBD = 3;

	/** 用户支付密码 */
	public static final Integer PHONE_CODE_YHZF = 4;

	/** 子帐号支付密码修改 */
	public static final Integer PHONE_CODE_ZZHZF = 5;

	/** 钱包 */
	public static final Integer PHONE_CODE_QB = 6;

	/** 提现 */
	public static final Integer PHONE_CODE_TX = 7;

	/** 子帐号手机号码解绑 */
	public static final Integer PHONE_CODE_ZZHSJJB = 8;

	/** 子帐号手机号码绑定 */
	public static final Integer PHONE_CODE_ZZHSJBD = 9;

	/** 银行卡 */
	public static final Integer PHONE_CODE_BANKCARD = 10;

	/**
	 * 虚拟订单业务类型 2.话费充值 3彩票订购
	 */
	public static final Integer BUSINESS_TYPE_PHONE = 2;

	public static final Integer BUSINESS_TYPE_LOTTERY = 3;

	/**
	 * 状态 -1：删除，0：冻结，1：正常
	 */
	public static final Integer STATUS_DELETE = -1;

	public static final Integer STATUS_FREEZE = 0;

	public static final Integer STATUS_DEFAULT = 1;

	/**
	 * 消息回复模式 1：文本 2：单图文 3：多图文
	 */
	public static final Integer MODE_TEXT = 1;

	public static final Integer MODE_SINGLE = 2;

	public static final Integer MODE_MULTI = 3;

	public static final String PATH = "$jzwgj$/app/";

	public static final String IMAGE_PATH = "$jzwgj$";

	public static final String TOUSERNAME = "$ToUserName$";

	public static final String FROMUSERNAME = "$FromUserName$";

	public static final String HOMEPATH = "/index.htm";

	public static final String CONTENT_PATH = "/wechat/detail.htm?id=$MessageContentId$";

	/**
	 * 信息类型 1：消息库 2：素材库
	 */
	public static final Integer INFORMATION_TYPE_MESSAGE = 1;

	public static final Integer INFORMATION_TYPE_FODDER = 2;

	/**
	 * 1:注册，2：找回密码，3：手机绑定，4：用户支付密码，5：子帐号支付密码修改，6：用户钱包
	 */
	public static final Integer PHONE_CODE_REGISTER = 1;

	public static final Integer PHONE_CODE_FINDPWD = 2;

	public static final Integer PHONE_CODE_BINDPHONE = 3;

	public static final Integer PHONE_CODE_USERPAY = 4;

	public static final Integer PHONE_CODE_MGPAY = 5;

	public static final Integer PHONE_CODE_USERMR = 6;

	/**
	 * 运营商类型
	 */
	public static final String OPERATOR_YIDONG = "中国移动";

	public static final String OPERATOR_LIANTONG = "中国联通";

	public static final String OPERATOR_DIANXIN = "中国电信";

	/**
	 * 订单类型 1,实物订单 2，虚拟订单 3,预订订单
	 */

	public static final Integer ORDER_TYPE_PHYSICAL = 1;

	public static final Integer ORDER_TYPE_VIRTUAL = 2;

	public static final Integer ORDER_TYPE_RESERVE = 3;

	public static final Integer ORDER_TYPE_APPOINTMENT = 4;

	/**
	 * 购买产品类型,1:订购,2:续费,3:推广
	 */
	public static final Integer APPLY_ORDER = 1;

	public static final Integer APPLY_RENEW = 2;

	public static final Integer APPLY_PUPULARIZE = 3;

	/**
	 * 金额自定义：10L： 1毛钱
	 */
	public static final Long MEMVALUE = 10L;

	public static final String LP = "lp";

	/**
	 * 每天验证码发送次数
	 */
	public static final Integer PHONE_CODE_COUNT = 10;
	/**
	 * 验证码时效 180L=3分钟
	 */
	public static final Long PHONE_TIME_VALID = 180L;

    /** 抽奖记录状态:正常抽奖 */
	public static final Integer LOTTERY_RECORD_TYPE_OK = 1;

	/** 抽奖记录状态:抽奖次数已满 */
	public static final Integer LOTTERY_RECORD_TYPE_FULL = 2;

	/** 抽奖记录状态:已中奖 */
	public static final Integer LOTTERY_RECORD_TYPE_WINNED = 3;

	/** 抽奖状态:中奖 */
	public static final Integer LOTTERY_TYPE_WIN = 1;

	/** 抽奖状态:未中奖 */
	public static final Integer LOTTERY_TYPE_UNWIN = 2;

	/** 抽奖状态:抽奖次数已满 */
	public static final Integer LOTTERY_TYPE_FULL = 4;

	/** 抽奖状态:以前中过奖 */
	public static final Integer LOTTERY_TYPE_WINNED = 5;

	/**
	 * 提现状态 0 提现成功 -1 提现失败 5提现处理中
	 */
	/** 提现成功 */
	public static final Integer WD_STATE_SUCC = 0;
	/** 提现失败 */
	public static final Integer WD_STATE_FAIL = -1;
	/** 提现处理中 */
	public static final Integer WD_STATE_DEAL = 5;

	/**
	 * 服务类型
	 */
	/** 同行转账 */
	public static final Integer SERVICE_PEER_WD = 1;
	/** 跨行转账 */
	public static final Integer SERVICE_OUTER_WD = 2;
	/** 提现结果查询 */
	public static final Integer SERVICE_QUERY_WD = 3;

	/** 赠送金额开关 1开 2关 */
	public static final String IS_SEND_AMOUNT = "IS_SEND_AMOUNT";
	/** 赠送金额 */
	public static final String PLATFROM_SEND_AMOUNT = "PLATFROM_SEND_AMOUNT";

	/**
	 * 活动状态 -1:已删除 0:未开始 1:活动中 2:已关闭
	 */
	/** -1：已删除 */
	public static final Integer ACTIVITY_DEL = -1;
	/** 0：未开始 */
	public static final Integer ACTIVITY_START = 0;
	/** 1：活动中 */
	public static final Integer ACTIVITY_ING = 1;
	/** 2：已关闭 */
	public static final Integer ACTIVITY_STOP = 2;

	/** 已中奖（应用于刮刮卡） */
	public static final Integer INRECORD_WIN = 2;

	/**
	 * 中奖凭证状态
	 */
	/** 0：未分配 */
	public static final Integer WINNING_PROOF_NO = 0;
	/** 1：已分配 */
	public static final Integer WINNING_PROOF_YES = 1;

	/**
	 * 是否立即注册成为会员 0 否 1 是 2已存在
	 */
	public static final Integer QUICK_IS_REGISTER_NO = 0;

	public static final Integer QUICK_IS_REGISTER_YES = 1;

	public static final Integer QUICK_IS_REGISTER_EXIST = 2;

	/**
	 * 发货方式 1.快递 2.送货上门
	 */
	/** 快递 */
	public static final Integer DELIVERY_TYPE_LOGISTICS = 1;
	/** 送货上门 */
	public static final Integer DELIVERY_TYPE_HOME = 2;

	/**
	 * 文章分类模板CODE
	 */
	/** 文章分类模板1 */
	public static final String ACT_ONE = "ACT_ONE";
	/** 文章分类模板2 */
	public static final String ACT_TWO = "ACT_TWO";
	/** 文章分类模板3 */
	public static final String ACT_THREE = "ACT_THREE";
	/** 文章分类模板4 */
	public static final String ACT_FOUR = "ACT_FOUR";
	/** 预订购买商品已调整 */
	public static final Integer RESERVE_GOOD_UPDATE = 1;
	/** 预订购买未达到起订金额 */
	public static final Integer RESERVE_UNREACH_AMOUNT = 2;
	/** 预订购买正常 */
	public static final Integer RESERVE_NORMAL = 3;
	/** 预订未到时间 */
	public static final Integer RESERVE_UNSTART = 4;
	/** 预订已结束 */
	public static final Integer RESERVE_END = 5;
	/** 预定商品折扣率 */
	public static final Integer DISCOUNT_RATE = 1000;
	/** 预定商品价格调整额度 */
	public static final Long ADJUST_AMOUNT = 0l;

	/**
	 * 预定订单手机提醒
	 */
	/** 开启 */
	public static final Integer ORDER_NOTE_OPEN = 1;
	/** 关闭 */
	public static final Integer ORDER_NOTE_NOT_OPEN = 2;

	/**
	 * 通用状态 -1:删除 1:可用
	 */

	/** 1:可用、 需要 、 允许 、是、 开启 */
	public static final Integer COMMON_STATES_ACTIVE = 1;
	/** 2:不可用 、不需要、 不允许、 否、关闭 */
	public static final Integer COMMON_STATES_NOACTIVE = 2;
	/** -1:删除 */
	public static final Integer COMMON_STATES_DEL = -1;

	/**
	 * 是否进行会员打折
	 */
	public static final Integer IS_MEMBER_DISCOUNT = 1;

	/**
	 * 代理类型： 金牌代理， 银牌代理
	 */
	/** 金牌代理 */
	public static final String PROXYNAME_GOLD = "金牌代理";
	/** 银牌代理 */
	public static final String PROXYNAME_SILVER = "银牌代理";

	/**
	 * 订单类型 ： 1 实物订单 2 预约订单 3 预定订单
	 */
	/** 1 实物订单 */
	public static final Integer ODTP_GENERAL = 1;
	/** 2 预约订单 */
	public static final Integer ODTP_SUBSCRIBE = 2;
	/** 3 预定订单 */
	public static final Integer ODTP_RESERVE = 3;

	/**
	 * 银行卡用途类型
	 */
	/** 银行卡用途类型 1：支付 */
	public static final Integer BANKTYPE_PAY = Integer.valueOf(1);

	/** 银行卡用途类型 2：提现 */
	public static final Integer BANKTYPE_WITHDRAW = Integer.valueOf(2);

	/** 预约 无库存 */
	public static final Long SUBSCRIBE_NO_STOCK = 999999l;

	/**
	 * 短信模板编号
	 */
	/** 预订新订单通知（未付款）: [管家国际]尊敬的老板，您发起的#arg0#预订活动，有新客户预约，具体详情请登录管家国际查看！ */
	public static final String UMS_110 = "110";
	/** 预订新订单通知（已付款）: [管家国际]尊敬的老板，您发起的#arg0#预订活动，有新客户付款啦，具体详情请登录管家国际查看！ */
	public static final String UMS_111 = "111";
	/** 预约新订单通知（未付款）: [管家国际]尊敬的老板，您发起的#arg0#预约活动，有新客户预约，具体详情请登录管家国际查看！ */
	public static final String UMS_112 = "112";
	/** 预约付完定金提示（直接购）: [管家国际]尊敬的用户，您的预约订单号为#arg0#，店家会尽快处理，请耐心等待！ */
	public static final String UMS_113 = "113";
	/** 新订单通知（未付款）: [管家国际]尊敬的老板，您店内的宝贝#arg0#已被客户拍下，具体详情请登录管家国际查看！ */
	public static final String UMS_114 = "114";
	/** 新订单通知（已付款）: [管家国际]尊敬的老板，您店内的订单#arg0#，客户已付款，具体详情请登录管家国际查看！ */
	public static final String UMS_115 = "115";
	/** 付款后（直接购） : [管家国际]尊敬的用户，您的订单号为#arg0#，店家会尽快处理，请耐心等待！ */
	public static final String UMS_116 = "116";

	/** 模板商品分类数量 */
	public static final int SHOP_TYPE_NUM = 20;

	/** 商品类目类型:普通商品 */
	public static final int GOOD_CLASSIFICATION_GENERAL = 1;

	/** 商品类目类型:协议商品 */
	public static final int GOOD_CLASSIFICATION_PROTOCOL = 2;
	public static final String PROTOCOL_GOOD_SHOPNO = "PROTOCOL_GOOD_SHOPNO";

	/** 短信验证规则 **/
	public static final String MESSAGE_PHONE_RULE = "^(13|15|16|17|18)\\d{9}$";

	/**
	 * PUSH消息类型 终端使用
	 * */
	/** 0交易消息 */
	public static final Integer PUSH_TRADE_MESSAGE = 0;
	/** 1商品消息 */
	public static final Integer PUSH_GOOD_MESSAGE = 1;
	/** 2刷脸精选 */
	public static final Integer PUSH_CHOICENESS_MESSAGE = 2;
	/** 3 预定消息 */
	public static final Integer PUSH_RESERVEE_MESSAGE = 3;
	/** 4 预约消息 */
	public static final Integer PUSH_APPOINT_MESSAGE = 4;
	/** 6生意圈新发布消息 */
	public static final Integer PUSH_BUSINESS_CICLE_MESSAGE = 6;
	/** 7点赞信息 */
	public static final Integer PUSH_PRAISE_MESSAGE = 7;
	/** 8 评论消息 */
	public static final Integer PUSH_COMMENT_MESSAGE = 8;
	/** 9系统消息 */
	public static final Integer PUSH_SYSTEM_MESSAGE = 9;
	/** 10新分店加盟 */
	public static final Integer PUSH_NEW_SUBBRANCH_MESSAGE = 10;
	/** 11总店设置佣金 */
	public static final Integer PUSH_PROFIT_MESSAGE = 11;
	/** 12 送店设置发货权限 */
	public static final Integer PUSH_DELIVER_MESSAGE = 12;
	/** 13钱包进账 */
	public static final Integer PUSH_ACCOUNT_MESSAGE = 13;
	/** 14分店冻结店铺*/
	public static final Integer PUSH_SUBBRANCH_FREEZE_SHOP_MESSAGE = 14;
	/** 15分店解冻店铺*/
	public static final Integer PUSH_SUBBRANCH_UNFREEZE_SHOP_MESSAGE = 15;

	/**
	 * PUSH消息点击跳转页面
	 * */
	/** 跳转我的店铺-分店 */
	public static final String LINK_MY_BF_SHOP = "lpprotocol://page/my_bf_shop";
	/** 跳转我的店铺-总店 */
	public static final String LINK_MY_P_SHOP = "lpprotocol://page/my_p_shop";
	/** 跳转我的钱包 */
	public static final String LINK_MY_WALLET = "lpprotocol://page/my_wallet";
	/** 总店的分店管理 */
	public static final String LINK_MY_P_CHILD_SHOP = "lpprotocol://page/my_p_child_shops";
	/** 分店的分店管理 */
	public static final String LINK_MY_BF_CHILD_SHOP = "lpprotocol://page/my_bf_child_shops";
	/** 历史分店列表 */
	public static final String LINK_MY_HIS_CHILD_SHOP = "lpprotocol://page/history_child_shops";

}
