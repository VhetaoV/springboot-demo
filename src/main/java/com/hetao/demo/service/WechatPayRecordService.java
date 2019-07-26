package com.hetao.demo.service;

import com.hetao.demo.dao.WechatPayRecordDao;
import com.hetao.demo.entity.WechatPayRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description: 支付记录
 * @author: chenliang
 * @create: 2019-07-23 14:18
 **/
@Service
public class WechatPayRecordService {

    @Resource
    private WechatPayRecordDao wechatPayRecordDao;


    public Integer getPayStatus(String phoneNum,String productType,Integer dayOrder,Integer orderType){
        return this.wechatPayRecordDao.getPayStatus(phoneNum, productType, dayOrder, orderType);
    }


    public void insertOrder(WechatPayRecord wpr){
        this.wechatPayRecordDao.insertOrder(wpr);
    }

//    public int updateOrderStatus(String phoneNum,String productType,Integer dayOrder,Integer orderType){
//       return  this.wechatPayRecordDao.updateOrderStatus(phoneNum, productType, dayOrder,orderType);
//    }

    public int updateOrderStatus(String orderNo,double totalFee){
        return this.wechatPayRecordDao.updateOrderStatus(orderNo,totalFee);
    }
}
