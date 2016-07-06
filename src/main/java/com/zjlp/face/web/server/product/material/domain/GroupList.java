package com.zjlp.face.web.server.product.material.domain;

import java.io.Serializable;

public class GroupList implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7030103109276753382L;

	private Long id;

    private Long groupId;

    private Long partId;

    private String associationTable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getPartId() {
        return partId;
    }

    public void setPartId(Long partId) {
        this.partId = partId;
    }

    public String getAssociationTable() {
        return associationTable;
    }

    public void setAssociationTable(String associationTable) {
        this.associationTable = associationTable == null ? null : associationTable.trim();
    }
}