package com.hetao.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @description: 微信小程序知识点dao
 * @author: chenliang
 * @create: 2019-07-17 15:27
 **/
@Component
@Mapper
public interface WechatKnowledgeDao {

    /**
     * @Description: 获取知识点每天的知识数量
     * @Param:
     * @param productType
     * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.Integer>>
     * @Author: chenliang
     * @Date: 2019/7/18
     */
    @Select("SELECT dayOrder,COUNT(*) as count,PayCategory,price FROM wechat_knowledges WHERE productType=#{productType} GROUP BY dayOrder")
    public List<Map<String,Integer>> findDayCount(String productType);


    /**
     * @Description: 获取每天的知识点
     * @Param:
     * @param dayOrder
     * @param productType
     * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     * @Author: chenliang
     * @Date: 2019/7/18
     */
    @Select("SELECT Id as id,knowledgePoint FROM wechat_knowledges WHERE productType=#{productType} AND dayOrder=#{dayOrder}")
    public List<Map<String,String>> findKnowledges(@Param("dayOrder") Integer dayOrder, @Param("productType") String productType);
}
