package com.hetao.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @description: 小程序知识点
 * @author: chenliang
 * @create: 2019-07-18 10:29
 **/
@ApiModel(description = "知识点列表")
public class wechatKnowledge implements Serializable {

    private static final long serialVersionUID = 8213757621076102727L;

    @ApiModelProperty("自增ID")
    private Integer Id;
    @ApiModelProperty("哪一天")
    private Integer dayOrder;
    @ApiModelProperty("知识点ID")
    private Integer knowledgeId;
    @ApiModelProperty("知识点内容")
    private String knowledgePoint;
    @ApiModelProperty("专业")
    private String productType;
    @ApiModelProperty("题目类别：1，免费，2，分享，3，付费")
    private Integer payCategory;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getDayOrder() {
        return dayOrder;
    }

    public void setDayOrder(Integer dayOrder) {
        this.dayOrder = dayOrder;
    }

    public Integer getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(Integer knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public String getKnowledgePoint() {
        return knowledgePoint;
    }

    public void setKnowledgePoint(String knowledgePoint) {
        this.knowledgePoint = knowledgePoint;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getPayCategory() {
        return payCategory;
    }

    public void setPayCategory(Integer payCategory) {
        this.payCategory = payCategory;
    }


}
