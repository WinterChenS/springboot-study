package com.winterchen.service.user.impl;

import com.winterchen.domain.UserEntity;
import com.winterchen.domain.UserRepository;
import com.winterchen.exception.ArgumentIsNullException;
import com.winterchen.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户服务层
 * Created by winterchen on 2017/11/29.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity findUserByName(String userName) {
        if(StringUtils.isEmpty(userName))
            throw new ArgumentIsNullException("账号为空");
        UserEntity uu = userRepository.findByUserName(userName);
        return uu;
    }
}
