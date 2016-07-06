package com.zjlp.face.web.server.operation.member.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员微信关系
 * @ClassName: MemberWechatRelation 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author ah
 * @date 2015年4月21日 上午10:42:29
 */
@Deprecated   //会员卡和微信不关联
public class MemberWechatRelation implements Serializable{
    
	private static final long serialVersionUID = 1830842795633584568L;

	private Long id;

    private String openId;

    private String fakeId;

    private Long sellerId;

    private String shopNo;

    private Long memberCardId;

    private Long userId;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getFakeId() {
        return fakeId;
    }

    public void setFakeId(String fakeId) {
        this.fakeId = fakeId == null ? null : fakeId.trim();
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo == null ? null : shopNo.trim();
    }

    public Long getMemberCardId() {
        return memberCardId;
    }

    public void setMemberCardId(Long memberCardId) {
        this.memberCardId = memberCardId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}