package com.zjlp.face.web.server.operation.member.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 积分操作记录表
 * @ClassName: IntegralRecode 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年4月15日 下午5:20:09
 */
public class IntegralRecode implements Serializable{
	
	private static final long serialVersionUID = 5965473964403798016L;
	
	 //操作类型(1,积分抵扣商品价格  2 消费送积分 3 签到送积分,4店家赠送积分)
	public static final Integer TYPE_SYSTEM = 0;
	
	public static final Integer TYPE_DEDUCTION = 1;
	
	public static final Integer TYPE_CONSUMER = 2;
	
	public static final Integer TYPE_SIGNIN = 3;
	
	public static final Integer TYPE_SEND = 4;
	
    //操作方式 1:获得积分 2:抵扣积分 3  积分冻结 4 积分解冻
	public static final Integer WAY_ADD = 1;
	
	public static final Integer WAY_DEDUCTION = 2;
	
	public static final Integer WAY_FROZEN = 3;
	
	public static final Integer WAY_UNFROZEN = 4;
	
	
	//主键
	private Long id;
	//会员卡ID
    private Long memberCardId;
    //操作类型(1,积分抵扣商品价格  2 消费送积分 3 签到送积分 4 店家赠送积分)
    private Integer type;
    //操作方式 1:获得积分 2:抵扣积分 3  积分冻结 4 积分解冻
    private Integer way;
    //操作积分数(单位：个)
    private Long integral;
    //操作后可用积分额度(单位：个)
    private Long availableIntegral;
    //操作后冻结积分额度(单位：个)
    private Long frozenIntegral;
    //操作年份（如 2014）
    private String operYear;
    //操作月份（如 05）
    private String operMonth;
    //创建时间
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberCardId() {
        return memberCardId;
    }

    public void setMemberCardId(Long memberCardId) {
        this.memberCardId = memberCardId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    public Long getIntegral() {
        return integral;
    }

    public void setIntegral(Long integral) {
        this.integral = integral;
    }

    public Long getAvailableIntegral() {
        return availableIntegral;
    }

    public void setAvailableIntegral(Long availableIntegral) {
        this.availableIntegral = availableIntegral;
    }

    public Long getFrozenIntegral() {
        return frozenIntegral;
    }

    public void setFrozenIntegral(Long frozenIntegral) {
        this.frozenIntegral = frozenIntegral;
    }

    public String getOperYear() {
        return operYear;
    }

    public void setOperYear(String operYear) {
        this.operYear = operYear == null ? null : operYear.trim();
    }

    public String getOperMonth() {
        return operMonth;
    }

    public void setOperMonth(String operMonth) {
        this.operMonth = operMonth == null ? null : operMonth.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}