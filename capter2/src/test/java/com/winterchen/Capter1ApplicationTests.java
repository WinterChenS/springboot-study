package com.winterchen;

import com.winterchen.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Capter1ApplicationTests {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate<String, User> redisTemplate;

	@Test
	public void test() throws Exception{
		stringRedisTemplate.opsForValue().set("aaa","111");
		Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
	}


	@Test
	public void test2() throws Exception{

		User user = new User("superMan", 22);
		redisTemplate.opsForValue().set(user.getUsername(), user);
		user = new User("spritMan", 23);
		redisTemplate.opsForValue().set(user.getUsername(), user);
		user = new User("jjMan", 18);
		redisTemplate.opsForValue().set(user.getUsername(), user);

		Assert.assertEquals(22, redisTemplate.opsForValue().get("superMan").getAge().longValue());
		Assert.assertEquals(23, redisTemplate.opsForValue().get("spritMan").getAge().longValue());
		Assert.assertEquals(18, redisTemplate.opsForValue().get("jjMan").getAge().longValue());

	}

}
