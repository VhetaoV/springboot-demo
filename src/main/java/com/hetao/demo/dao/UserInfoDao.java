package com.hetao.demo.dao;


import com.hetao.demo.entity.UserInfo;
import com.hetao.demo.entity.UserInfoDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;


@Component
@Mapper
public interface UserInfoDao {


    @Select(" SELECT u.username AS userName,u.ProductType AS productType,u.password as password,p.product_cname as productName FROM " +
            " everstar.UserInfo u LEFT JOIN esweb.cms_product_relation p ON u.ProductType = p.product_name WHERE  u.username = #{username}")
    public UserInfoDto selectByUsername(String username);


    @Insert("INSERT INTO everstar.UserInfo (username, password, RegisterForTime, LastLoginForTime, PhoneNum) " +
            "VALUES(#{userInfo.userName}, #{userInfo.password}, #{userInfo.registerForTime}, #{userInfo.lastLoginForTime}, #{userInfo.phoneNum})")
    public boolean addUserInfo(@Param("userInfo") UserInfo userInfo);


    @Insert("insert into  esweb.cms_protection_belonger(phone,belonger,source) values (#{phone},#{belonger},#{string})")
    public void addBelonger(String phone, String belonger, String string);

}
