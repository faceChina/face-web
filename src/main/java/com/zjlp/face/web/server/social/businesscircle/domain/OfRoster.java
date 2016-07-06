package com.zjlp.face.web.server.social.businesscircle.domain;

import java.io.Serializable;

/**
 * openfire的用户好友类
* @ClassName: OfRoster
* @Description: TODO(这里用一句话描述这个类的作用)
* @author wxn
* @date 2015年5月11日 上午11:43:15
*
 */
public class OfRoster implements Serializable{

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -2361202130437434870L;
	/**名册ID**/
	private Long rosterID;
	/**用户名称**/
	private String userName;
	/**名册条目JID**/
	private String jid;
	/**条目订阅状态**/
	private Integer sub;
	/**条目申请状态**/
	private Integer ask;
	/**申请被接受的标记**/
	private Integer recv;
	/**名册条目分配的妮称**/
	private String nick;
	public Long getRosterID() {
		return rosterID;
	}
	public void setRosterID(Long rosterID) {
		this.rosterID = rosterID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getJid() {
		return jid;
	}
	public void setJid(String jid) {
		this.jid = jid;
	}
	public Integer getSub() {
		return sub;
	}
	public void setSub(Integer sub) {
		this.sub = sub;
	}
	public Integer getAsk() {
		return ask;
	}
	public void setAsk(Integer ask) {
		this.ask = ask;
	}
	public Integer getRecv() {
		return recv;
	}
	public void setRecv(Integer recv) {
		this.recv = recv;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}

}
