package com.zjlp.face.web.server.user.shop.domain.vo;

import java.io.Serializable;
import java.text.DecimalFormat;

import com.zjlp.face.web.server.user.shop.domain.DeliveryTemplateItem;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateItemDto;
import com.zjlp.face.web.server.user.user.domain.dto.VaearTree;

public class DeliveryTemplateItemVo implements Serializable {

	private static final long serialVersionUID = 3569933499797696008L;
	// 主键
	private Long id;
	// 运费模版ID
	private Long postageTemplateId;
	// 邮费子项涉及的地区,多个地区用逗号连接数量串
	private String destination;
	// 首费标准
	private Integer startStandard;
	// 首费(单位：分)
	private Long startPostage;
	// 首费(单位：元，保留两位小数)
	private String startPostageStr;
	// 增费标准
	private Integer addStandard;
	// 续件运费(单位：分)
	private Long addPostage;
	// 续件运费(单位：元，保留两位小数)
	private String addPostageStr;
	
	public DeliveryTemplateItemVo(){}
	public DeliveryTemplateItemVo(DeliveryTemplateItem templateItem, 
			VaearTree vaearTree) {
		this.id = templateItem.getId();
		this.postageTemplateId = templateItem.getPostageTemplateId();
		this.setDestination(templateItem.getDestination(), vaearTree);
		this.startStandard = templateItem.getStartStandard();
		this.startPostage = templateItem.getStartPostage();
		this.addStandard = templateItem.getAddStandard();
		this.addPostage = templateItem.getAddPostage();
		this.setStartPostageStr();
		this.setAddPostageStr();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPostageTemplateId() {
		return postageTemplateId;
	}
	public void setPostageTemplateId(Long postageTemplateId) {
		this.postageTemplateId = postageTemplateId;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination, VaearTree vaearTree) {
		if (null == vaearTree) {
			this.destination = destination;
		} else {
			if (null == destination || "".equals(destination)) {
				return;
			}
			if (DeliveryTemplateItemDto.ALL.equals(destination)) {
//				this.destination = DeliveryTemplateItemDto.getAllDestination();
				this.destination = "全国默认地区";
				return;
			}
			String[] codeList = destination.split(",");
			StringBuilder sb = new StringBuilder();
			String varea = null;
			for (String code : codeList) {
				varea = vaearTree.getAreaByCode(code);
				if (null != varea && !"".equals(varea)) {
					sb.append(varea).append(",");
				}
			}
			if (sb.length() > 0) {
				this.destination = sb.substring(0, sb.length() - 1);
			}
		}
	}
	public Integer getStartStandard() {
		return startStandard;
	}
	public void setStartStandard(Integer startStandard) {
		this.startStandard = startStandard;
	}
	public Long getStartPostage() {
		return startPostage;
	}
	public void setStartPostage(Long startPostage) {
		this.startPostage = startPostage;
	}
	public Integer getAddStandard() {
		return addStandard;
	}
	public void setAddStandard(Integer addStandard) {
		this.addStandard = addStandard;
	}
	public Long getAddPostage() {
		return addPostage;
	}
	public void setAddPostage(Long addPostage) {
		this.addPostage = addPostage;
	}
	
	
	public String getStartPostageStr() {
		return startPostageStr;
	}
	public void setStartPostageStr() {
		double db = this.startPostage/100.0;
		DecimalFormat df = new DecimalFormat("##0.00");
		this.startPostageStr = df.format(db);
	}
	public String getAddPostageStr() {
		return addPostageStr;
	}
	public void setAddPostageStr() {
		double db = this.addPostage/100.0;
		DecimalFormat df = new DecimalFormat("0.00");
		this.addPostageStr = df.format(db);
	}
	

}
