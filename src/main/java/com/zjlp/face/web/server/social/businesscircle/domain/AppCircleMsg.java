package com.zjlp.face.web.server.social.businesscircle.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 生意圈消息
* @ClassName: Circle
* @Description: TODO(这里用一句话描述这个类的作用)
* @author wxn
* @date 2015年5月7日 下午1:48:40
*
 */
public class AppCircleMsg implements Serializable{

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -7028901636750098447L;
	
	private Long id;
	/**用户ID**/
	private Long userId;
	/**发布的文本内容**/
	private String content;
	/**分享的url链接**/
	private String url;
	/**图片路径**/
	private String img1;
	private String img2;
	private String img3;
	private String img4;
	private String img5;
	private String img6;
	private String img7;
	private String img8;
	private String img9;
	/**创建时间**/
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getImg1() {
		return img1;
	}
	public void setImg1(String img1) {
		this.img1 = img1;
	}
	public String getImg2() {
		return img2;
	}
	public void setImg2(String img2) {
		this.img2 = img2;
	}
	public String getImg3() {
		return img3;
	}
	public void setImg3(String img3) {
		this.img3 = img3;
	}
	public String getImg4() {
		return img4;
	}
	public void setImg4(String img4) {
		this.img4 = img4;
	}
	public String getImg5() {
		return img5;
	}
	public void setImg5(String img5) {
		this.img5 = img5;
	}
	public String getImg6() {
		return img6;
	}
	public void setImg6(String img6) {
		this.img6 = img6;
	}
	public String getImg7() {
		return img7;
	}
	public void setImg7(String img7) {
		this.img7 = img7;
	}
	public String getImg8() {
		return img8;
	}
	public void setImg8(String img8) {
		this.img8 = img8;
	}
	public String getImg9() {
		return img9;
	}
	public void setImg9(String img9) {
		this.img9 = img9;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "AppCircleMsg [id=" + id + ", userId=" + userId + ", content="
				+ content + ", url=" + url + ", img1=" + img1 + ", img2="
				+ img2 + ", img3=" + img3 + ", img4=" + img4 + ", img5=" + img5
				+ ", img6=" + img6 + ", img7=" + img7 + ", img8=" + img8
				+ ", img9=" + img9 + ", createTime=" + createTime + "]";
	}
	
}
