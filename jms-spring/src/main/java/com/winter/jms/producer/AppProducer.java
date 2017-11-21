package com.winter.jms.producer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 生产者服务的启动类
 * Created by Administrator on 2017/11/2.
 */
public class AppProducer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("producer.xml");
        ProducerService service = context.getBean(ProducerService.class);

        for (int i = 0; i < 100; i++) {
            service.sendMessage("test" + i);
        }
        context.close();
    }
}
