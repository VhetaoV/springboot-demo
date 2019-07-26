package com.hetao.demo.service;

import com.hetao.demo.dao.WechatQuestionDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @description: 微信小程序题目service、
 * @author: chenliang
 * @create: 2019-07-16 17:02
 **/
@Service
public class WechatQuestionService {

    @Resource
    private WechatQuestionDao wechatQuestionDao;

    public List<Map<String,String>> findQuestionList(String productType,Integer dayOrder){
           return this.wechatQuestionDao.findWechatQuestion(productType,dayOrder);
    }


    public List<String> findDays(){
        return this.wechatQuestionDao.findDays();
    }


    public List<Map<String,Integer>> findQuestionCount(String productType){
        return this.wechatQuestionDao.findQuestionCount(productType);
    }
}
