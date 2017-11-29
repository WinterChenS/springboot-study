package com.winterchen.domain;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("FROM UserEntity u where u.userName=:userName")
    UserEntity findByUserName(@Param("userName") String userName);

}
