package com.mq.demo;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author : JCccc
 * @CreateTime : 2019/9/3
 * @Description :
 **/
@RestController
public class SendMessageController {

    //使用RabbitTemplate,这提供了接收/发送等等方法
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${mock.exchange}")
    String exchange;
    @Value("${mock.routingKey}")
    String routingKey;
    String body;

    @Autowired
    AmqpAdmin amqpAdmin;
    @GetMapping("/create")
    public String create() {
        amqpAdmin.declareExchange(new DirectExchange("TestDirectExchange"));
        amqpAdmin.declareQueue(new Queue("TestDirectQueue",true));
        //创建绑定规则
        amqpAdmin.declareBinding(new Binding("TestDirectQueue", Binding.DestinationType.QUEUE,"TestDirectExchange","TestDirectRouting",null));
        System.out.println("创建完成");
        return "ok";
    }


    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        MessageProperties properties = new MessageProperties();
        properties.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
        properties.setContentType("text/plain");
        properties.setContentEncoding("UTF-8");
        Message message = rabbitTemplate.getMessageConverter().toMessage(body, properties);
        rabbitTemplate.convertAndSend(exchange, routingKey, body);
        return "ok";
    }


}
