package com.zjlp.face.web.constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.util.Assert;

import com.zjlp.face.util.date.DateStyle;
import com.zjlp.face.util.date.DateUtil;


public class GenerateCode {
	
	/**
	 * 商品编码生成： 商品编码的格式：-店铺编号前4位 + 8位UUID
	 * 1.shopNo 2.UUID
	 * @Title: getCommodityBarcode
	 * @Description: (生成订商品编码)
	 * @return
	 * @date 2014年3月28日 下午2:39:39 
	 * @author ah
	 */
	public static String getCommodityBarcode(String shopNo) {
		shopNo = shopNo.substring(0,4);
		StringBuffer sb = new StringBuffer();
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.substring(0,8);
		sb.append(shopNo).append(uuid);
		return sb.toString();
	}
	
	
	
	private static final String COMPANY_TITLE= "S";
	
	private static final String COMPANY_TITLE_VIRTUAL= "V";
	
	private static final String COMPANY_TITLE_RESERVE= "R";
	
	private static final String COMPANY_TITLE_APPOINTMENT = "A";
	
	private static final String COMPANY_TITLE_RECHARGE = "M";
	
	
    /**
     * 
     * @Title: checkOrderType 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @param orderNoList
     * @return 0未知 1实物订单 2虚拟订单
     * @date 2014年8月21日 下午8:39:36  
     * @author dzq
     */
    public static Integer checkOrderType(List<String> orderNoList){
    	Assert.notEmpty(orderNoList, "订单为空,无法判断订单类型");
		String orderNo = orderNoList.get(0);
		if (orderNo.startsWith(COMPANY_TITLE, 0)) {
			return 1;
		}else if(orderNo.startsWith(COMPANY_TITLE_VIRTUAL, 0)){
			return 2;
		}else if(orderNo.startsWith(COMPANY_TITLE_RESERVE, 0)){
			return 3;
		}else if(orderNo.startsWith(COMPANY_TITLE_APPOINTMENT, 0)){
			return 4;
		}
		return 0;
    }
	
	/**
	 * 订单号生成：
	 * 		订单号的格式：S+yyyyMMddHHmmss-merIdx-userId-五位随机数
	 *          0.jzwgj:订单统一前缀
	 * 			1.yyyyMMdd：由 4 位年 2 位月 2 位日组成。  如：20090303
	 * 			3.merIdx 商户ID
	 * 			4.userId 用户ID
	 * 			5.五位随机数
	 * @Title: generate 
	 * @Description: (生成订单号) 
	 * @return
	 * @date 2014年3月18日 下午2:08:30  
	 * @author dzq
	 */
	public static  String getOderNo(String shopNo,String userId){
		StringBuffer sb = new StringBuffer();
	    String currentDate = DateUtil.DateToString(new java.util.Date(), DateStyle.YYMMDDHHMMSS);
	    currentDate = currentDate.substring(2, 14);
	    int t = new Random().nextInt(99999);
	    if(t < 10000) t+=10000;
	    sb.append(COMPANY_TITLE.toUpperCase()).append(currentDate).append(t);
		return sb.toString();
	}

	/**
	 * 充值订单号生成：
	 * 		订单号的格式：S+yyyyMMddHHmmss-merIdx-userId-五位随机数
	 *          0.jzwgj:订单统一前缀
	 * 			1.yyyyMMdd：由 4 位年 2 位月 2 位日组成。  如：20090303
	 * 			3.merIdx 商户ID
	 * 			4.userId 用户ID
	 * 			5.五位随机数
	 * @Title: generate 
	 * @Description: (生成充值订单号) 
	 * @return
	 * @date 2014年3月18日 下午2:08:30  
	 * @author ah
	 */
	public static  String getRechargeNo(String shopNo,String userId){
		StringBuffer sb = new StringBuffer();
	    String currentDate = DateUtil.DateToString(new java.util.Date(), DateStyle.YYMMDDHHMMSS);
	    currentDate = currentDate.substring(2, 14);
	    int t = new Random().nextInt(99999);
	    if(t < 10000) t+=10000;
	    sb.append(COMPANY_TITLE_RECHARGE.toUpperCase()).append(currentDate).append(t);
		return sb.toString();
	}
	
	public static  String getAppointmentOderNo(String shopNo,String userId){
		StringBuffer sb = new StringBuffer();
	    String currentDate = DateUtil.DateToString(new java.util.Date(), DateStyle.YYMMDDHHMMSS);
	    currentDate = currentDate.substring(2, 14);
	    int t = new Random().nextInt(99999);
	    if(t < 10000) t+=10000;
	    sb.append(COMPANY_TITLE_APPOINTMENT.toUpperCase()).append(currentDate).append(t);
		return sb.toString();
	}

	
	/**交易流水号
	 * 10 + yyMMddHH + 4位用户ID（向前补0）+ 6位随机数 (加两位系统标示码) ——20位
	 * @param userId用户ID
	 * @date 2014/04/10 15:27:00
	 * @author lys
	 * @return
	 */
	
	private static String _userCode(String userId){
		if(userId.length() > 4){
			userId = userId.substring(0,4);
		}else if(userId.length() < 4){
			for(int i = 0; i < 3; i++ ){
				if(userId.length() == 4){
				break;
				}else{
					userId = "0" + userId;
					}
			}
		}
		return userId;
	}
	
	private static String _currentDate(){
		String currentDate = DateUtil.DateToString(new java.util.Date(), DateStyle.YYMMDDHHMMSS);
		currentDate = currentDate.substring(2, 10);
		return currentDate;
	}
	
	public static String getSN(String userId) {
		StringBuilder sn = new StringBuilder();
		userId = _userCode(userId);
		String currentDate = _currentDate();
		
		int t = new Random().nextInt(999999);
		if(t < 100000) t+=100000;
		
		sn.append("10").append(userId).append(currentDate).append(t);
		return sn.toString();
	}
	
	public static String getPhoneSN(String userId){
		StringBuilder sn = new StringBuilder();
		userId = _userCode(userId);
		String currentDate = _currentDate();
		int t = new Random().nextInt(999999);
		if(t < 100000) t+=100000;
		sn.append("21").append(userId).append(currentDate).append(t);
		return sn.toString();
	}
	
	public static String getLotterySN(String userId){
		StringBuilder sn = new StringBuilder();
		userId = _userCode(userId);
		String currentDate = _currentDate();
		int t = new Random().nextInt(999999);
		if(t < 100000) t+=100000;
		sn.append("41").append(userId).append(currentDate).append(t);
		return sn.toString();
	}
	
	public static String getCommissionSN(String userId){
		StringBuilder sn = new StringBuilder();
		userId = _userCode(userId);
		String currentDate = _currentDate();
		int t = new Random().nextInt(999999);
		if(t < 100000) t+=100000;
		sn.append("51").append(userId).append(currentDate).append(t);
		return sn.toString();
	}
	
	public static String getVirtualOrderNo(){
		StringBuffer sb = new StringBuffer();
	    String currentDate = DateUtil.DateToString(new java.util.Date(), DateStyle.YYMMDDHHMMSS);
	    currentDate = currentDate.substring(2, 14);
	    int t = new Random().nextInt(99999);
	    if(t < 10000) t+=10000;
	    sb.append(COMPANY_TITLE_VIRTUAL.toUpperCase()).append(currentDate).append(t);
		return sb.toString();
	}
	
	public static String getReserveOrderNo(){
		StringBuffer sb = new StringBuffer();
	    String currentDate = DateUtil.DateToString(new java.util.Date(), DateStyle.YYMMDDHHMMSS);
	    currentDate = currentDate.substring(2, 14);
	    int t = new Random().nextInt(99999);
	    if(t < 10000) t+=10000;
	    sb.append(COMPANY_TITLE_RESERVE.toUpperCase()).append(currentDate).append(t);
		return sb.toString();
	}
	
	/**
	 * 	中奖序列号生成：
	 * 		格式：4位店铺编码　＋　shopNo-七位随机数
	 * @Title: generate 
	 * @Description: (生成兑奖序列号) 
	 * @return
	 * @date 2014年4月3日 下午10:08:30  
	 * @author fjx
	 */
	public static String getSerialNumber(String shopNo){
		StringBuffer sb = new StringBuffer();
		if(4 <= shopNo.length()){
			shopNo = shopNo.substring(0,4);
		}
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.substring(0,13);
		sb.append(shopNo).append(uuid);
		return sb.toString();
	}
	
	/**
	 * 	中奖序列号生成：
	 * 		格式：4位时间戳-七位随机数
	 * @Title: generate 
	 * @Description: (生成兑奖序列号) 
	 * @return
	 * @date 2014年4月3日 下午10:08:30  
	 * @author fjx
	 */
	public static String getSerialNumber(){
		StringBuffer sb = new StringBuffer();
		String currentDate = DateUtil.DateToString(new java.util.Date(), DateStyle.YYMMDDHHMMSS);
		if(4 <= currentDate.length()){
			currentDate = currentDate.substring(2,6);
		}
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.substring(0,13);
		sb.append(currentDate).append(uuid);
		return sb.toString();
	}
	
	
	

	
	/**
	 * 生成邀请码
	 * @Title: generateInvitationCode 
	 * @Description: (邀请码：U+时间戳（HHmmss）+5位随机字符) 
	 * @return
	 * @date 2014年7月24日 下午9:13:20  
	 * @author Administrator
	 */
	public static String generateInvitationCode() {
		String product ="U";
		StringBuffer sb = new StringBuffer();
		Date nowDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("HHmmss");
		String stringDate = dateFormat.format(nowDate);
		sb.append(product).append(stringDate).append(generateWord(5));
		return sb.toString();
	}
	
	
	/**
	 * 生成站内唯一识别ID
	 * @Title: generateTokenCode 
	 * @Description: (邀请码：T+时间戳（yyyyMMddHHmmss）+5位随机字符) 
	 * @return
	 * @date 2014年7月24日 下午9:13:20  
	 * @author Administrator
	 */
	public static String generateTokenCode() {
		String product ="T";
		StringBuffer sb = new StringBuffer();
		Date nowDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String stringDate = dateFormat.format(nowDate);
		sb.append(product).append(stringDate).append(generateWord(5));
		return sb.toString();
	}
	
	
	/**
	 * 生成子帐户号
	 * @Title: generateManaged 
	 * @Description: (10位) 
	 * @return
	 * @date 2014年8月5日 下午9:55:01  
	 * @author fjx
	 */
	public static String generateManaged() {
		String product ="m";
		StringBuffer sb = new StringBuffer();
		Date nowDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("MMdd");
		String stringDate = dateFormat.format(nowDate);
		sb.append(product).append(stringDate).append(createRandom(true,5));
		return sb.toString();
	}
	
	
	/**
	 * 生成 随机字符串
	 * @Title: generate 
	 * @Description: (生成随机字符串) 
	 * @param length
	 * @return
	 * @date 2014年7月24日 下午8:50:57  
	 * @author ah
	 */
	public static String generateWord(int length) {
		final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }
	
	

	 /**
	  * 创建指定数量的随机字符串
	  * @param numberFlag 是否是数字
	  * @param length
	  * @return fjx
	  */
	 public static String createRandom(boolean numberFlag, int length){
	  String retStr = "";
	  String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
	  int len = strTable.length();
	  boolean bDone = true;
	  do {
	   retStr = "";
	   int count = 0;
	   for (int i = 0; i < length; i++) {
	    double dblR = Math.random() * len;
	    int intR = (int) Math.floor(dblR);
	    char c = strTable.charAt(intR);
	    if (('0' <= c) && (c <= '9')) {
	     count++;
	    }
	    retStr += strTable.charAt(intR);
	   }
	   if (count >= 2) {
	    bDone = false;
	   }
	  } while (bDone);
	 
	  return retStr;
	 }
	
	/**
	 *  (LP+3个随机字符)+(shopNo最后5位)+(Q+时间戳后四位)
	 * @Title: createCouponCode 
	 * @Description: (生成优惠券码) 
	 * @param shopNo
	 * @return
	 * @date 2015年9月22日 下午8:10:01  
	 * @author cbc
	 */
	public static String createCouponCode(String shopNo) {
		StringBuilder sb = new StringBuilder("LP");
		String generateWord = generateWord(3);
		
		
		String substring = shopNo.substring(shopNo.length()-5, shopNo.length());
		
		Date nowDate = new Date();
		long time = nowDate.getTime();
		String timeStr = String.valueOf(time);
		String stringDate = timeStr.substring(timeStr.length()-4, timeStr.length());
	    return sb.append(generateWord).append("-").append(substring).append("-").append("Q").append(stringDate).toString().toUpperCase();
	}
	
	public static void main(String[] args){
//		System.out.println(getCommissionSN(""));
//		System.out.println(getCommissionSN("0"));
		List<String> orderNoList = new ArrayList<String>();
		orderNoList.add("S123123");
		System.out.println(checkOrderType(orderNoList));
		
		System.out.println(createCouponCode("HZJZ1506291407PMm511"));
	}
}
