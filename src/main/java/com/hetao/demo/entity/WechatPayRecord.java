package com.hetao.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 支付记录
 * @author: chenliang
 * @create: 2019-07-23 12:09
 **/
@Data
@ApiModel(description = "支付记录")
public class WechatPayRecord implements Serializable {

    private static final long serialVersionUID = 284253152952118966L;
    @ApiModelProperty(value = "自增ID",example = "123")
    private Integer Id;
    @ApiModelProperty("订单编号")
    private String orderNo;
    @ApiModelProperty("手机号")
    private String phoneNum;
    @ApiModelProperty("专业")
    private String productType;
    @ApiModelProperty("哪一天")
    private Integer dayOrder;
    @ApiModelProperty("订单创建时间")
    private Date createTime;
    @ApiModelProperty("支付成功时间")
    private Date payTime;
    @ApiModelProperty("支付状态1：未支付，2：已支付")
    private Integer status;
    @ApiModelProperty("订单类别1：知识点，2：练习题'")
    private Integer orderType;
    @ApiModelProperty("订单金额（元）")
    private Double price;
    @ApiModelProperty("已支付金额（元）")
    private Double totalFee;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getDayOrder() {
        return dayOrder;
    }

    public void setDayOrder(Integer dayOrder) {
        this.dayOrder = dayOrder;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Double getPrice() { return price; }

    public void setPrice(Double price) { this.price = price; }

    public Double getTotalFee() { return totalFee; }

    public void setTotalFee(Double totalFee) { this.totalFee = totalFee; }
}
