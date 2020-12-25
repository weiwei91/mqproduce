package com.mq.demo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    //依赖注入RabbitTemplate
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void sendMsgs() {
        rabbitTemplate.convertAndSend("xalarm_aps_exchange_forward_to_component","aive","你大爷还是你大爷");
    }

}
