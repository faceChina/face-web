package com.zjlp.face.web.server.user.businesscard.domain;

import java.util.Date;

public class CardCase {
    private Long id;

    private Long userId;

    private String userPingyin;

    private Long cardId;

    private Date createTime;
    
    public CardCase() {
	}
    
    public CardCase(Long userId, Long cardId) {
    	this.userId = userId;
    	this.cardId = cardId;
	}

	public static boolean checkInput(CardCase cardCase){
    	if (null != cardCase.getUserId()) {
    		return true;
    	}
    	if (null != cardCase.getCardId()) {
    		return true;
    	}
    	return false;
    }

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

    public String getUserPingyin() {
        return userPingyin;
    }

    public void setUserPingyin(String userPingyin) {
        this.userPingyin = userPingyin == null ? null : userPingyin.trim();
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}