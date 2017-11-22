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
import org.springframework.cache.CacheManager;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import java.io.File;

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

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private CacheManager cacheManager;

	@Before
	public void setUp() throws Exception{
		userRepository.save(new User("AAA", 10));
	}

	@Test
	public void selectData() throws Exception {
		User u1 = userRepository.findByName("AAA");
		System.out.println("第一次查询：" + u1.getAge());
		User u2 = userRepository.findByName("AAA");
		System.out.println("第二次查询：" + u2.getAge());
		u2.setAge(55);
		userRepository.save(u2);

		User u3 = userRepository.findByName("AAA");
		System.out.println("第三次查询：" + u3.getAge());
	}

	@Test
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
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
	public void sendSimpleMail() throws Exception{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("1085143002@qq.com");
		message.setTo("1085143002@qq.com");
		message.setSubject("主题：简单邮件");
		message.setText("测试邮件内容");
		mailSender.send(message);
	}

	@Test
	public void sendAttachmentsMail() throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom("1085143002@qq.com");
		helper.setTo("1193254946@qq.com");
		helper.setSubject("主题：有附件");
		helper.setText("有附件的邮件");
		FileSystemResource file = new FileSystemResource(new File("C:\\Users\\Administrator\\Desktop\\图片\\九邑学士府.png"));
		helper.addAttachment("附件-1.jpg", file);
		helper.addAttachment("附件-2.jpg", file);
		mailSender.send(mimeMessage);
	}

	@Test
	public void sendInlineMail() throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom("1085143002@qq.com");
		helper.setTo("1085143002@qq.com");
		helper.setSubject("主题：嵌入静态资源");
		helper.setText("<html><body><img src=\"cid:weixin\" ></body></html>", true);
		FileSystemResource file = new FileSystemResource(new File("C:\\Users\\Administrator\\Desktop\\图片\\九邑学士府.png"));
		helper.addInline("weixin", file);
		mailSender.send(mimeMessage);
	}

	@Test
	public void contextLoads() {
	}



}
