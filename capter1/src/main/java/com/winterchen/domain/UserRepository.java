package com.winterchen.domain;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * Created by Administrator on 2017/11/21.
 */
@CacheConfig(cacheNames = "users")
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /*@Cacheable(key = "#p0")
    UserEntity findByName(String name);*/

    //UserEntity findByNameAndAge(String name, Integer age);

    /*@Query("from User u where u.name=:name")
    UserEntity findUser(@Param("name") String name);*/

    /*@CachePut(key = "#p0.name")
    UserEntity save(UserEntity userEntity);*/

    /*@Cacheable(key = "#p0")
    UserEntity findByPhone(String phone);*/

    /**
     * 通过账号查找用户
     * @param userName
     * @return
     */
    @Query("FROM UserEntity u where u.userName=:userName")
    UserEntity findByUserName(@Param("userName") String userName);


    /**
     * 通过QQ的唯一标示OpenId查找用户
     * @param openId
     * @return
     */
    @Query("FROM UserEntity u where u.QQOpenId=:openId")
    UserEntity findByQQOPenId(@Param("openId") String openId);

    /**
     * 更新QQ用户的资料
     * @param user
     * @param changeTime
     * @return
     */
    @Query("UPDATE UserEntity u SET u.avatar=:user.avatar,u.nickname=:user.nikename,u.lastChangeTime=:changeTime WHERE u.QQOpenId=:user.openId")
    UserEntity updateByQQOpenId(@Param("user") QQUser user, @Param("changeTime")Date changeTime);

    /**
     * 插入QQ用户信息
     * @param qqUser
     * @param userEntity
     * @return
     */
    @Query("INSERT INTO UserEntity(roles,status,avatar,createTime,nikeName,QQOpenId) values(:u.roles,:u.status,:qquser.avatar,:u.createTime,:qquser.nikeName,:qquser.QQOpenId)")
    UserEntity saveByQQUser(@Param("qquser") QQUser qqUser, @Param("u") UserEntity userEntity);

}
