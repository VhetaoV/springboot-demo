package com.hetao.demo.service;

import com.hetao.demo.dao.UserInfoDao;
import com.hetao.demo.entity.UserInfo;
import com.hetao.demo.entity.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 添加用户接口
 * Created by wangjian on 2018/4/28.
 */
@Service
@Transactional
public class UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;


    public UserInfoDto selectByUsername(String username) {
        return userInfoDao.selectByUsername(username);
    }

    public boolean addUserInfo(UserInfo userInfo){
        return userInfoDao.addUserInfo(userInfo);
    }

    /**
     * 保存用户 和 Belonger 的关系
     * @param username
     * @param belonger
     * @param string
     */
    public void addBelonger(String username, String belonger, String string){
        this.userInfoDao.addBelonger(username, belonger, string);
    }


}
