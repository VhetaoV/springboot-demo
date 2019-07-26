package com.hetao.demo.dao;

import com.hetao.demo.entity.WechatPayRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @description: 支付记录
 * @author: chenliang
 * @create: 2019-07-23 11:30
 **/
@Component
@Mapper
public interface WechatPayRecordDao {


    @Select("SELECT `status` FROM wechat_payRecord WHERE phoneNum=#{phoneNum} AND productType=#{productType} AND dayOrder=#{dayOrder} AND orderType=#{orderType}")
    public Integer getPayStatus(String phoneNum, String productType, Integer dayOrder, Integer orderType);



    @Insert("INSERT INTO wechat_payRecord(orderNo,phoneNum,productType,dayOrder,createTime,`status`,orderType,price) " +
            "VALUES (#{wpr.orderNo},#{wpr.phoneNum},#{wpr.productType},#{wpr.dayOrder},#{wpr.createTime},#{wpr.status},#{wpr.orderType},#{wpr.price})")
    public void insertOrder(WechatPayRecord wpr);


//    @Update("UPDATE wechat_payRecord SET `status`=1,payTime=now() WHERE phoneNum=#{phoneNum} AND productType=#{productType} AND dayOrder=#{dayOrder} AND orderType=#{orderType}")
//    public int updateOrderStatus(String phoneNum,String productType,Integer dayOrder,Integer orderType);


    @Update("UPDATE wechat_payRecord SET `status`=1,payTime=now(),totalFee=#{totalFee} WHERE orderNo=#{orderNo}")
    public int updateOrderStatus(String orderNo, double totalFee);

}
