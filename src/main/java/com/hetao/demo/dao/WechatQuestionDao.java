package com.hetao.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @description: 微信小程序题目dao
 * @author: chenliang
 * @create: 2019-07-16 16:51
 **/
@Component
@Mapper
public interface WechatQuestionDao {

    @Select("select Id as id,QuestionId,QuestionSubject,AnswerA,AnswerB,AnswerC,AnswerD,AnswerE,QuestionAnswer,Analysis from wechat_questions " +
            "WHERE dayOrder=#{dayOrder} AND productType=#{productType}")
    public List<Map<String,String>> findWechatQuestion(@Param("productType") String productType, @Param("dayOrder") Integer dayOrder);



    @Select("SELECT dayOrder FROM wechat_questions GROUP BY dayOrder")
    public List<String> findDays();


    @Select("SELECT dayOrder,COUNT(*) as count,PayCategory FROM wechat_questions WHERE productType=#{productType} GROUP BY dayOrder")
    public List<Map<String,Integer>> findQuestionCount(String productType);

}
