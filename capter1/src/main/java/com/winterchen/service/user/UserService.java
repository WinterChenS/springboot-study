package com.winterchen.service.user;


import com.winterchen.domain.UserEntity;

/**
 * 用户服务层
 * Created by winterchen on 2017/11/29.
 */
public interface UserService {

    /**
     * 根据用户的账号查找用户
     * @param userName
     * @return
     */
    UserEntity findUserByName(String userName);
}
