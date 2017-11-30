package com.winterchen.service.user;


import com.winterchen.domain.UserEntity;
import org.springframework.security.core.userdetails.User;

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

    /**
     * 判断QQ用户是否已经是第一次授权
     * 如果是，进行数据库的添加，如果不是，进行资料的更新
     * @param openId
     * @return
     */
    UserEntity findUserByQQOpenId(String openId);
}
