package com.mq.demo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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
    @Autowired
    AmqpAdmin amqpAdmin;

    @Test
    public void sendMsgs() {
        rabbitTemplate.convertAndSend("xalarm_aps_exchange_forward_to_component","aive","你大爷还是你大爷");
    }

    @Test
    public void creatTopic(){
        amqpAdmin.declareExchange(new DirectExchange("TestDirectExchange"));
        amqpAdmin.declareQueue(new Queue("TestDirectQueue",true));
        //创建绑定规则
        amqpAdmin.declareBinding(new Binding("TestDirectQueue", Binding.DestinationType.QUEUE,"TestDirectExchange","TestDirectRouting",null));
        System.out.println("创建完成");
    }

}
