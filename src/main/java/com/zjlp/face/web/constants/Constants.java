package com.zjlp.face.web.constants;

public final class Constants {

	private Constants(){}
	
	
	/** 最小密码长度 */
	public static final Integer MIN_PASSWD_LEN = 6;
	
	
	/**
	 * 内部来源：1WAP 
	 */
	public static final Integer FROM_INNER_WAP =1;
	
	
	/**
	 * 订单支付方式：1 在线支付 2 线下支付 
	 */
	public static final Integer PAY_WAY_ONLINE = 1;
	
	public static final Integer PAY_WAY_UNDERLINE = 2;
	
	
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
	 * 
	 * 通用状态 -1 删除 0 未激活、未开通、无效、冻结 1 正常、开通、激活、有效
	 * 
	 * */
	
	/** 有效 */
	public static final Integer VALID = 1;
	
	/** 无效 */
	public static final Integer UNVALID = -1;
	
	/** 冻结 */
	public static final Integer FREEZE = 0;
	
	/** 默认 */
	public static final Integer ISDEFAULT = 1;
	
	/** 非默认 */
	public static final Integer NOTDEFAULT = 0;
	
	
	
	/**
	 * 订单状态（0 生成 01、待付款 02、已付款 03、已发货 04 已收货 05、交易成功 10、已取消 11、已删除 20、退订 30
	 * 超时关闭）
	 */
	/** 0.生成 */
    public static final Integer STATUS_GENARETE = 0;

	/** 01、待付款 */
    public static final Integer STATUS_WAIT = 1;

	/** 02.已付款 */
    public static final Integer STATUS_PAY = 2;

	/** 03.已发货 */
    public static final Integer STATUS_SEND = 3;

	/** 04.已收货 */
    public static final Integer STATUS_RECEIVE = 4;

	/** 05.交易成功 */
    public static final Integer STATUS_COMPILE = 5;

	/** 10.已取消 */
    public static final Integer STATUS_CANCEL = 10;

	/** 11.已删除 */
    public static final Integer STATUS_DELETE = 11;

	/** 20.退订 */
    public static final Integer STATUS_REFUND = 20;

	/** 30.超时关闭 */
    public static final Integer STATUS_CLOSE = 30;

	/**
	 * 预约订单的状态： 40 待确认 41 卖家已确认 42 卖家已拒绝 43 用户已取消
	 */
	public static final Integer BOOKORDER_STATUS_WAIT = 40;

	public static final Integer BOOKORDER_STATUS_CONFIRM = 41;

	public static final Integer BOOKORDER_STATUS_REFUSE = 42;

	public static final Integer BOOKORDER_STATUS_CANCEL = 43;
    
    /**
     * 充值订单状态（1 生成/未支付 2 正在充值/支付中 3 充值成功）
     */
    /** 1. 生成/未支付 */
    public static final Integer RECHARGE_STATUS_WAIT = 1;
    /** 2. 正在充值/支付中 */
    public static final Integer RECHARGE_STATUS_PAYING = 2;
    /** 3. 充值成功 */
    public static final Integer RECHARGE_STATUS_COMPILE = 3;
    /** 4. 充值失败 */
    public static final Integer RECHARGE_STATUS_FAILED = 4;
    
    /**
	 * 商品状态 -1：删除，1：正常 2.冻结 3 下架
	 */
    public static final Integer GOOD_STATUS_DELETE = -1;

    public static final Integer GOOD_STATUS_INIT = 0;

    public static final Integer GOOD_STATUS_DEFAULT = 1;

    public static final Integer GOOD_STATUS_FROZEN = 2;

    public static final Integer GOOD_STATUS_UNDERCARRIAGE = 3;
	
	/** 普通商品 */
	public static final Integer GOOD_CLASSIFICATION_COMMON = 1;
	
	/** 协议商品 */
	public static final Integer GOOD_CLASSIFICATION_PROTOCOL = 2;
	
	/** 享受会员折扣 */
	public static final Integer PRICE_PREFERENTIAL = 1;
	
	/** 不享受会员折扣 */
	public static final Integer PRICE_NO_PREFERENTIAL = 0;
	
	/**
	 * 模版状态 -1：删除，0：停用，1：激活
	 */
	/** -1.删除 */
    public static final Integer OWTEMPLATE_STATUS_DELETE = -1;
	
	/** 0.停用 */
    public static final Integer OWTEMPLATE_STATUS_STOP = 0;
	
	/** 1.激活 */
    public static final Integer OWTEMPLATE_STATUS_START = 1;
    
    /**
	 * 模版类型 1：官网模板，2：商城模板，4：商城分类页模板
	 */
	/** 1.官网首页模版 */
    public static final Integer OWTEMPLATE_TYPE_GW_HP = 1;
	
	/** 2.商城首页模版 */
    public static final Integer OWTEMPLATE_TYPE_SC_HP = 2;
	
	/** 4.商城分类页模版 */
    public static final Integer OWTEMPLATE_TYPE_SC_GT = 4;
    

	/** 微信支付appid **/
	public final static String WXPAY_APP_ID = "WXPAY_APP_ID";
	
	/** 微信支付appsecret **/
	public final static String WXPAY_APP_SECRET = "WXPAY_APP_SECRET";
	
	/** 微信支付商户号ID **/
	public final static String WXPAY_MCH_ID = "WXPAY_MCH_ID";
	
	/** 微信支付商户秘钥 **/
	public final static String WXPAY_SN = "WXPAY_SN";
	
	/** 微管家路径 **/
	public final static String WGJ_URL = "WGJ_URL";
	
	
	/** 模块图片路径 */
	public static final String MODULAR_FILE = "modularFile";
	
	/** 首页轮播图图片路径 */
	public static final String CAROUSEL_HOME_PAGE_FILE = "carouselHomePageFile";
	
	/** 商品分类轮播图图片路径 */
	public static final String CAROUSEL_GOOD_TYPE_FILE="carouselGoodTypeFile";
	
	/** 商品分类图片 */
	public static final String GOOD_TYPE_FILE = "goodTypeFile";
	
	/** 微官网首页模板code */
	public static final String WGW_HP_TEMPLATE_CODE = "HP_WGW";
	
	/** 官网微商城首页模板code */
	public static final String GW_WSC_HP_TEMPLATE_CODE = "HP_GW_WSC";
	
	/** 微商城首页模板code */
	public static final String WSC_HP_TEMPLATE_CODE = "HP_WSC";
	
	/** 官网微商城商品分类页 */
	public static final String GT_SC_WGW = "GT_GW_WSC";
	
	/** 微商城商品分类页 */
	public static final String GT_WSC = "GT_WSC";
	
	/** 模板类型为1 */
	public static final Integer TEMPALTE_TYPE = 1;
	
	/** 基本模块类型为2 */
	public static final Integer BASE_MODULAR_TYPE = 2;
	
	/** 微官网code */
	public static final String WGW_CODE = "WGW";
	
	/** 微官网商城code */
	public static final String GW_WSC_CODE = "GW_WSC";
	
	/** 微商城code */
	public static final String WSC_CODE = "WSC";
	
	/** 基本路径 */
    public final static String BASE_PATH = "/img/";
	
	/** 店招图片大小 */
    public final static String SHOP_STROKES_SIZE = "640_230/";
	
	/** 店招文件路径 */
    public final static String SHOP_STROKES_FILE = "/shopStrokesFile/";
    
	/**
	 * 模块链接类型
	 */
	/**newsList*/
	public static final String MODULAR_NEWSLIST = "newsList";
	/**goodType*/
	public static final String MODULAR_SHOPTYPE = "shopType";
	
	/** goodTypes */
	public static final String MODULAR_SHOPTYPES = "shopTypes";
	/**good*/
	public static final String MODULAR_GOOD = "good";
	/**articleCategory*/
	public static final String MODULAR_ARTICLECATEGORY = "articleCategory";
	/**aricleColumn*/
	public static final String MODULAR_ARICLECOLUMN = "aricleColumn";
	/**album*/
	public static final String MODULAR_ALBUM = "album";
	/**photoAlbum*/
	public static final String MODULAR_PHOTOALBUM = "photoAlbum";
	/** link url */
	public static final String LINKURL = "linkUrl";
	/** appointment */
	public static final String MODULAR_APPOINTMENT = "appointment";
	/** https */
	public static final String HTTPS = "https://";
	/** http */
	public static final String HTTP = "http://";
    
	/**
	 * 模块链接地址
	 */
	//TODO 待每个模块出来后根据具体地址修改
	// 单个
	/** 新闻链接地址 */
	public static final String MODULAR_URL_NEWSLIST = "/wap/{shopNo}/any/article/news.htm?id=";
	/**articleCategory 地址*/
	public static final String MODULAR_URL_ARTICLECATEGORY = "/wap/{shopNo}/any/article/category.htm?id=";
	/**aricleColumn 地址*/
	public static final String MODULAR_URL_ARICLECOLUMN = "/wap/{shopNo}/any/article/column.htm?id=";
	/**单个商品分类地址*/
	public static final String MODULAR_URL_SHOPTYPE = "/wap/{shopNo}/any/list.htm?shopTypeId=";
	/**album 地址*/
	public static final String MODULAR_URL_ALBUM = "/wap/{shopNo}/any/album/photolist.htm?albumId=";
	/**photoAlbum*/
	public static final String MODULAR_URL_PHOTOALBUM = "/wap/{shopNo}/any/album/albumlist.htm?photoAlbumId=";
	/** appointment 地址*/
	public static final String MODULAR_URL_APPOINTMENT = "/wap/{shopNo}/any/appoint/{id}.htm";
	
	//多选
	/** 商品分类地址 */
	public static final String MODULAR_URL_SHOPTYPE_GWSC = "/wap/{shopNo}/any/shopType/list.htm?groupId=";
	/** 商品地址 */
	public static final String MODULAR_URL_GOOD = "/wap/{shopNo}/any/list.htm?groupId=";
	/** 个人二维码分享url */
	public static final String INDIVIDUAL_QRC_URL = "/any/code/qrc/{userId}/myQRCode.htm";
	/** 群组二维码分享url */
	public static final String MUL_CHAT_QRC_URL = "/any/code/qrc/{mulChatQrc}/mulQRCode.htm";

	
	//url的前后缀
	/** url前缀 */
	public static final String URL_PREFIX = "/wap";
	
	/** url:any */
	public static final String URL_ANY = "/any";
	
	/** url后缀 */
	public static final String URL_SUFIX = ".htm";
	/** url:shopNo */
	public static final String URL_SHOPNO = "{shopNo}";
	/** url:id */
	public static final String URL_ID = "{id}";
	/** user:id */
	public static final String USER_ID = "{userId}";
	/** 群组 加密二维码*/
	public static final String MUL_CHAT_QRC = "{mulChatQrc}";
	/**本地默认图片*/
	public static final String BASE_PIC_URL = "/base/img";
    
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
    
    
	/** 文章类型：1：图文 2：链接 */
    public static final Integer ARTICLE_TYPE_LINK = 2;

	public static final Long GOOD_MIN_PIRCE_PANNY = 1L;
	
	/**
	 * 服务类型
	 */
	/** 同行转账 */
	public static final Integer SERVICE_PEER_WD = 1;
	
	/** 跨行转账 */
	public static final Integer SERVICE_OUTER_WD = 2;
	
	/** 提现结果查询 */
	public static final Integer SERVICE_QUERY_WD = 3;
	
	/** 短信验证规则 **/
	public static final String MESSAGE_PHONE_RULE="^(12|13|14|15|16|17|18|19)\\d{9}$";
	
    /**
	 * 每天验证码发送次数
	 */
    public static final Integer PHONE_CODE_COUNT = 6;
    
    /**
	 * 验证码时效 180L=3分钟
	 */
    public static final Long PHONE_TIME_VALID = 180L;
    
    /**
	 * 手机验证码 类型
	 */
	/** 注册 */
    public static final Integer PHONE_CODE_ZC = 1;

	/** 找回密码 */
    public static final Integer PHONE_CODE_ZH = 2;

	/**
	 * 订单类型 1.实物订单 2.话费充值 3.彩票购买 6.红包
	 */
	/** 1.实物订单 */
    public static final Integer ORDER_TYPE_SW = 1;
    
    /** 5.会员卡充值订单*/
    public static final Integer ORDER_TYPE_RECHARGE = 5;
    
    /** 6.红包 */
    public static final Integer ORDER_TYPE_REDENVELOPE = 6;
    /***
	 * 付款方式1.银行卡付款，2.钱包付款，3.微信付款，4.支付宝付款
	 */
	/** 1.银行卡支付 */
    public static final Integer PAYMENT_BANKCARD = 1;

	/** 2.钱包支付 */
    public static final Integer PAYMENT_WALLET = 2;
    
	/** 3.微信支付 */
    public static final Integer PAYMENT_WECHAT = 3;
    
    /** 4.会员卡支付*/
    public static final Integer PAYMENT_MEMBER_CARD = 4;
    
    /** 5.支付宝支付*/
    public static final Integer PAYMENT_ALIPAY = 5;
    
    /** 6.拉卡拉支付*/
    public static final Integer PAYMENT_LAKALA = 6;
    
    /**
	 * 银行卡支付回调地址配置
	 */
    /** HOST */
    public static final String SWITCH_UNIONPAY_HOST = "SWITCH_UNIONPAY_HOST";

	/** 实物订单异步回调地址 */
    public static final String SWITCH_UNIONPAY_NOTIFYURL = "SWITCH_UNIONPAY_NOTIFYURL";

	/** 实物订单同步回调地址 */
    public static final String SWITCH_UNIONPAY_RETURNURL = "SWITCH_UNIONPAY_RETURNURL";
    
    
    /**
	 * 权限标识符
	 */
	/** 普通用户角色 */
    public static final String STRING_ROLE_U = "U";
    /** 官网 */
    public static final String STRING_ROLE_GW = "GW";
    /** 商城 */
    public static final String STRING_ROLE_SC = "SC";
    /** 免费商城 */
    public static final String STRING_ROLE_FREE = "FREE";
    
    /**
     * 消息事件
     */
    public static final Integer ENVNT_TYPE_ATTENTION = 1;
    
    public static final Integer ENVNT_TYPE_REPLY = 2;
    
    public static final Integer ENVNT_TYPE_KEYWORD_RECOVERY = 3;
    /**
     * 消息回复模式 1：文本 2：单图文 3：多图文
     */
    public static final Integer MODE_TEXT = 1;

    public static final Integer MODE_SINGLE = 2;

    public static final Integer MODE_MULTI = 3;

    public static final String PATH = "$jzwgj$/wap/";

    public static final String IMAGE_PATH = "$jzwgj$";
    
    public static final String WECHAT_PIC_URL = "$picUrl$";

    public static final String TOUSERNAME = "$ToUserName$";

    public static final String FROMUSERNAME = "$FromUserName$";

    public static final String HOMEPATH = "/any/index.htm";

    public static final String CONTENT_PATH = "/any/wechat/detail.htm?id=$MessageContentId$";

    /**
     * 信息类型 1：消息库 2：素材库
     */
    public static final Integer INFORMATION_TYPE_MESSAGE = 1;

    public static final Integer INFORMATION_TYPE_FODDER = 2;
    
    /**
     * 店铺类型
     */
    /**官网*/
    public static final Integer SHOP_GW_TYPE = 1;
    /**商城*/
    public static final Integer SHOP_SC_TYPE = 2;
    /**免费店铺*/
    public static final Integer SHOP_FREE_TYPE = 4;
    
    /**
     * 模块类型
     */
    /**标准模块*/
    public static final Integer MODULAR_STANDARD = 1;
    /**自定义模块*/
    public static final Integer MODULAR_CUSTOM = 2;

    /** 模板商品分类数量 */
	public static final int SHOP_TYPE_NUM = 20;
	
	/**
	 * 图片标识1.用户上传，2.记录备份（如订单备份的商品图片）
	 */
	public static final Integer FILE_LABEL_USER = 1;
	public static final Integer FILE_LABEL_BACK = 2;
	
	/**
	 * 协议点剖号
	 */
	public static final String PROTOCOL_GOOD_SHOPNO = "PROTOCOL_GOOD_SHOPNO";
	
	/** 类目其他*/
	public static final Long LEIMU_QITA=4l;
	/** 类目门户*/
	public static final Long LEIMU_MENHU=15l;
	
	/**
	 * 微信图片大小
	 */
	public static final String WECHAT_BIG_SIZE = "360_200";
	public static final String WECHAT_SMALL_SIZE = "200_200";
	
	/**业务ID普通类型*/
	public static final Long SERVICE_ID_COMMON = 1l;
	/**业务ID预约类型*/
	public static final Long SERVICE_ID_APPOINTMENT = 2l;
	
	/**
	 * 充值渠道（1.WAP 2.Android  3.IOS）
	 */
	/** 1.WAP */
	public static final Integer RECHARGE_CHANNEL_WAP = 1;
	/** 2.Android */
	public static final Integer RECHARGE_CHANNEL_ANDROID = 2;
	/** 3.IOS */
	public static final Integer RECHARGE_CHANNEL_IOS = 3;
	
//	/** 首页登录路径*/
////	public static final String WAP_LOGIN_URL = "/wap/login.htm";
	
	/**
	 * 用户类型 0 匿名用户 1 实名用户
	 */
	/** 0 匿名用户 **/
	public static Integer USER_TYPE_ANONYMOUS = 0;
	/** 1 实名用户 */
	public static Integer USER_TYPE_REAL = 1;

	/**
	 * 认证类型 0未认证 1已认证
	 */
	/** 0未认证 */
	public static Integer AUTHENTICATE_TYPE_NO = 0;
	/** 1已认证 */
	public static Integer AUTHENTICATE_TYPE_CERTIFIED = 1;
	
	/** 免费商品 商品分类 */
	public static final Long CLASSIFICATION_ID_FREE_GOOD = 14l;
	
	public static final Integer AGENCY_ROLE_SUPPLIER = 1;
	public static final Integer AGENCY_ROLE_PURCHASER = 2;
	
	/**
	 * 是否设置分店佣金比例	否
	 */
	public static final Integer SUBBRANCH_TYPE_NOT_HAVE = 0;
	/**
	 * 是否设置分店佣金比例	是
	 */
	public static final Integer SUBBRANCH_TYPE_HAVE = 1;
	
	/**
	 * 分店佣金比例-最小
	 */
	public static final Integer SUBBRANCH_GOOD_RATE_MIN = 0;
	/**
	 * 分店佣金比例-最大
	 */
	public static final Integer SUBBRANCH_GOOD_RATE_MAX = 100;
	
	/** 手机号码验证规则 **/
	public static final String PHONE_RULE = "^((13[0-9])|(14[^4,\\D])|(15[^4,\\D])|(17[^4,\\D])|(18[0-9]))\\d{8}$";
	
	/** 微信免登陆静默注册默认密码 **/
	public static final String WECHAT_PASSWORD = "LPzg888888";// "LPzg888888";
	
	
	/********************红包*********************/
	/**个人普通红包*/
	public static final Integer TYPE_PERSON = 1; 
	/**群手气红包*/
	public static final Integer TYPE_GROUP_LUCK = 2;
	/**群普通红包*/
	public static final Integer TYPE_GROUP_NORMAL = 3;
}
