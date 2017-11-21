package com.winterchen;

import com.winterchen.domain.s.Message;
import com.winterchen.domain.s.MessageRepository;
import com.winterchen.domain.p.User;
import com.winterchen.domain.p.UserRepository;
import com.winterchen.web.HelloController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Capter1ApplicationTests {

	private MockMvc mvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MessageRepository messageRepository;

	@Before
	public void setUp() throws Exception{
		mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
	}

	@Test
	public void getHello() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(content().string(equalTo("hello word")));
	}

	@Test
	public void addData() throws Exception{
		userRepository.save(new User("AAA", 10));
		userRepository.save(new User("BBB", 20));
		userRepository.save(new User("CCC", 30));
		userRepository.save(new User("EEE", 40));
		userRepository.save(new User("FFF", 50));
		userRepository.save(new User("GGG", 60));
		userRepository.save(new User("HHH", 70));
		userRepository.save(new User("III", 80));
		userRepository.save(new User("JJJ", 90));
		userRepository.save(new User("HHH", 100));

		Assert.assertEquals(10, userRepository.findAll().size());
		Assert.assertEquals(60, userRepository.findByName("GGG").getAge().longValue());
		Assert.assertEquals(60, userRepository.findUser("GGG").getAge().longValue());
		Assert.assertEquals("FFF", userRepository.findByNameAndAge("FFF", 50).getName());

		userRepository.delete(userRepository.findByName("AAA"));

		Assert.assertEquals(9, userRepository.findAll().size());

		messageRepository.save(new Message("o1", "aaaaaaa"));
		messageRepository.save(new Message("o2", "bbbbbbb"));
		messageRepository.save(new Message("o3", "ccccccc"));

		Assert.assertEquals(3, messageRepository.findAll().size());

	}


	@Test
	public void contextLoads() {
	}

}
