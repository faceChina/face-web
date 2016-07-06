package com.zjlp.face.web.constants;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

public class ImageConstants {

	/**CLASS PATH*/
	public static final String CLASS_PATH = "/WEB-INF/classes/";
	/**上传保存路径*/
	public static final String UPLOAD_IMAGE_PATH = File.separator+"resources"+File.separator+"upload"+File.separator;//用于操作文件	
	/**返回前端路径*/
	public static final String BACK_UPLOAD_IMAGE_PATH = "/resources/upload/";//用于返回前端显示
	/**会员卡初始默认图片路径*/
	public static final String MEMBER_CARD_INIT_IMAGE_PATH = File.separator+"resource"+File.separator+"base"+File.separator+"img"+File.separator+"member"+File.separator+"card.png";
	/**默认图片路径*/
	public static final String INIT_IMG_PATH = "/resource/base/img/";
	/**
	 * 尺寸
	 */
	/**640_380*/
	public static final String ZOOM_IMG_640_380 = "640_380";
	
	/**640_640*/
	public static final String ZOOM_IMG_640_640 = "640_640";
	/**600*/
	public static final String ZOOM_IMG_600 = "600";
	
	
	/**
	 * 图片源头
	 */
	/**商品图片*/
	public static final String GOOD_FILE="goodFile";
	
	public static final String GOOD_FILE_280_280 = "goodZoomFile";
	
	public static final String GOOD_PDU_FILE="goodPduFile";
	
	public static final String GOOD_SKU_FILE = "goodSkuFile";
	
	public static final String GOOD_PDU_ITEM_FILE="goodPduItemFile";
		
	public static final String GOOD_PROPERTY_FILE = "goodPropertyFile";
	/**商品分类图片*/
	public static final String GOOD_TYPE_FILE="goodTypeFile";
	/**首页轮播图*/
	public static final String CAROUSEL_HOME_PAGE_FILE="carouselHomePageFile";
	/**商品分类轮播图*/
	public static final String CAROUSEL_GOOD_TYPE_FILE="carouselGoodTypeFile";
	/**UBB 图片*/
	public static final String UBB_FILE = "ubbFile";
	/**微信图片*/
	public static final String WECHAT_FILE = "wechatFile";
	/**模块图片*/
	public static final String MODULAR_FILE="modularFile";
	/**设置会员卡图片*/
	public static final String MEMBER_FILE = "memberFile";
	/**领取会员卡图片*/
	public static final String MEMBER_CARD = "card";
	/**头像*/
	public static final String HEAD_IMG_FILE = "headImgFile";
	/**名片*/
	public static final String BUSINESS_CARD_FILE = "businessCardFile";
	/**好友圈*/
	public static final String FRIENDS_CIRCLE_FILE = "friendsCircleFile";
	/**文章*/
	public static final String ATICLE_FILE = "aticleFile";
	/**文章分类*/
	public static final String ATICLE_CATEGORY_FILE = "aticleCategoryFile";
	/**预定活动*/
	public static final String RESERVE_FILE = "reserveFile";
	/**店招*/
	public static final String SHOP_STROKES_FILE = "shopStrokesFile";
	/**预约活动*/
	public static final String APPOINTMENT_FILE = "appointmentFile";
	/**预约项目*/
	public static final String APPOINTMENT_ITEM_FILE = "appointmentItemFile";
	/** 代理商招募图片 */
	public static final String RECRUITINFO_FILE = "recruitInfoFile";
	
	/**免费体验版 用户头像文件*/
	public static final String FREE_USER_HEAD_FILE = "freeUserHeadFile";
	/**免费体验版 二维码文件*/
	public static final String FREE_USER_QRCORD_FILE = "freeUserQRCordFile";
	/**免费体验版 栏目细项图片*/
	public static final String FREE_TOPIC_DETAIL_FILE = "freeTopicDetailFile";

	public static final String SHOP_LOGO_FILE = "shopLogoFile";
	
	/** 店铺 二维码文件*/
	public static final String SHOP_QRCORD_FILE = "shopQRCordFile";
	
	/**店铺相册*/
	public static final String ALBUM_FILE = "albumFile";
	/**文件栏目*/
	public static final String ATICLE_COLUMN_FILE = "aticleColumnFile";
	/**相册专辑*/
	public static final String PHOTO_ALBUM_FILE = "photoAlbumFile";
	/**店铺相册图片*/
	public static final String PHOTO_FILE = "photoFile";
	
	/**生意圈图片**/
	public static final String CIRCLE_PHOTO_FILE = "circleFile";
	/** 生意封面图片 **/
	public static final String CIRCLE_COVER_FILE = "circleCoverFile";
	/**服务型预约图片*/
	public static final String SERVICE_APPOINTMENT_FILE = "serviceAppointmentFile";
	/**名片背景图*/
	public static final String BUSSINESS_CARD_BACKGROUND_FILE = "bussinessCardBackgroundFile";
	/**店铺招牌图片*/
	public static final String SHOP_SIGN_PIC = "shopSignPic";
	
	/**
	 * 获取云存储图片缩略图
	 * @Title: getCloudstZoomPath 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param path
	 * @param zoom
	 * @return
	 * @author Hongbo Peng
	 */
	public static String getCloudstZoomPath(String path,String zoom){
		if(StringUtils.isNotBlank(zoom) && StringUtils.isNotBlank(path) && path.indexOf(INIT_IMG_PATH) == -1){
			//图片服务器获取
			StringBuffer sb = new StringBuffer(path).append("_q").append(zoom.replace("_", "x")); 
			return sb.toString();
		} else {
			//系统默认图片
			return path;
		}
	}
}
