package com.zjlp.face.web.server.operation.appoint.domain;

import java.util.Date;

public class AppointmentSkuRelation {
    private Long id;

    private Long appointmentId;

    private Long goodId;

    private Long goodSkuId;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Long getGoodSkuId() {
        return goodSkuId;
    }

    public void setGoodSkuId(Long goodSkuId) {
        this.goodSkuId = goodSkuId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}