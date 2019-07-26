package com.hetao.demo.service;

import com.hetao.demo.dao.WechatKnowledgeDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @description: 微信小程序知识点service
 * @author: chenliang
 * @create: 2019-07-17 15:28
 **/
@Service
public class WechatKnowledgeService {

    @Resource
    private WechatKnowledgeDao wechatKnowledgeDao;

    public List<Map<String,Integer>> findDayCount(String productType){
          return this.wechatKnowledgeDao.findDayCount(productType);
    }


    public List<Map<String,String>> findKnowledge(Integer dayOrder,String productType){
        return this.wechatKnowledgeDao.findKnowledges(dayOrder,productType);
    }


}
