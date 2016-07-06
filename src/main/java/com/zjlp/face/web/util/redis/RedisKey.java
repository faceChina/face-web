package com.zjlp.face.web.util.redis;
/**
 * 缓存KEY生成规则如下 :
 * 格式 ：
 * 		模块名_功能名_方法名_参数1_参数N
 * 示例： 如商品类目缓存
 * 		  模块名                      功能名                                             方法名			   参数
 * 		product_Classification_getClassificationById_3308
 * @ClassName: RedisKey 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年3月20日 下午1:25:56
 */
public class RedisKey{
	
	private static final String WGJ_KEY = "wgj";
	private static final String PRODUCT_KEY= "Product";
	
	/** 查询商品分类图片数据缓存*/
	public static final String ShopType_getCachedValue_key = WGJ_KEY+PRODUCT_KEY+"_ShopType_getCachedValue_key";
	/** 查询商品类目是否拥有销售属性*/
	public static final String Classification_hasSalesProp_key = WGJ_KEY+PRODUCT_KEY+"_Classification_hasSalesProp_";
	
	/** 查询商品类目是否为协议类目属性*/
	public static final String Classification_hasProtocolClassification_key = WGJ_KEY+PRODUCT_KEY+"_Classification_hasProtocolClassification_key_";

	/** 查询店铺最近一次使用的商品类目*/
	public static final String Classification_getLatestClassification_key = WGJ_KEY+PRODUCT_KEY+"_Classification_getLatestClassification_";
	
	/** 查询商品类目信息缓存*/
	public static final String Classification_getClassificationById_key = WGJ_KEY+PRODUCT_KEY+"_Classification_getClassificationById_";
	/** 查询商品类目信息缓存*/
	public static final String Classification_findClassificationByPid_key = WGJ_KEY+PRODUCT_KEY+"_Classification_findClassificationByPid_";
	/** 根据类目查询商品标准属性信息缓存*/
	public static final String Classification_findPropsByClassificationId_key = WGJ_KEY+PRODUCT_KEY+"_Classification_findPropsByClassificationId_";
	/** 根据属性查询属性值信息缓存*/
	public static final String Classification_findPropValuesByPropId_key = WGJ_KEY+PRODUCT_KEY+"_Classification_findPropValuesByPropId_";
	/** 查询官网首页热门模版信息缓存*/
	public static final String Template_findWgwHpHotOwTemplate_key = WGJ_KEY+PRODUCT_KEY+"_Template_findWgwHpHotOwTemplate";
	/** 官网首页其他模版信息缓存*/
	public static final String Template_findWgwHpOtherOwTemplate_key = WGJ_KEY+PRODUCT_KEY+"_Template_findWgwHpOtherOwTemplate";
	/** 官网商城首页热门模版信息缓存*/
	public static final String Template_findWgwScHpHotOwTemplate_key = WGJ_KEY+PRODUCT_KEY+"_Template_findWgwScHpHotOwTemplate";
	/** 官网商城首页其他模版信息缓存*/
	public static final String Template_findWgwScHpOtherOwTemplate_key = WGJ_KEY+PRODUCT_KEY+"_Template_findWgwScHpOtherOwTemplate";
	/** 商城首页热门模版信息缓存*/
	public static final String Template_findWscHpHotOwTemplate_key = WGJ_KEY+PRODUCT_KEY+"_Template_findWscHpHotOwTemplate";
	/** 商城首页其他模版信息缓存*/
	public static final String Template_findWscHpOtherOwTemplate_key = WGJ_KEY+PRODUCT_KEY+"_Template_findWscHpOtherOwTemplate";
	/** 三阶段所有模板初始化数据信息缓存 */
	public static final String Template_findAllInitOwTemplate_key = WGJ_KEY+PRODUCT_KEY+"_Template_findAllInitOwTemplate";
	/** 商品分类页模板初始化数据信息缓存 */
	public static final String Template_findGtOwTemplate_key = WGJ_KEY+PRODUCT_KEY+"_Template_findGtOwTemplate";
	/** 三阶段所有模块初始化数据信息缓存 */
	public static final String Modular_findAllInitMudular_key = WGJ_KEY+PRODUCT_KEY+"_Modular_findAllInitMudular";
	/** 三阶段基础模块初始化数据信息缓存 */
	public static final String Modular_findBaseMudular_key = WGJ_KEY+PRODUCT_KEY+"_Modular_findBaseMudular";
	/** 根据ID和Type查找设备信息缓存 */
	public static final String PhoneDevice_findPhoneDevice_key = PRODUCT_KEY+"_Phone_findPhoneDevice";
	/** 新增设备信息缓存 */
	public static final String PhoneDevice_addPhoneDevice_key = PRODUCT_KEY+"_Phone_addPhoneDevice";

	private static final String APP_WGJ_KEY = "app";
	private static final String APP_PRODUCT_KEY= "Product";
	/** 生意圈消息分发缓存 */
	public static final String SOCIAL_ADDAPPCIRCLEMSG_KEY_ = APP_WGJ_KEY+APP_PRODUCT_KEY+"_social_addappcirclemsg_key_";


}
