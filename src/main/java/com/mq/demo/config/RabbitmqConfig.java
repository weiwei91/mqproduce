package com.mq.demo.config;

import com.mq.demo.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
public class RabbitmqConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitmqConfig.class);

    @Value("${component.id}")
    String componentId;

    @Bean
    public Queue uploadPicture () {
        return new Queue(Constants.QUEUE_KEY_UPLOAD_PICTURE);
    }
    @Bean
    public Queue uploadObject () {
        return new Queue(Constants.QUEUE_KEY_UPLOAD_OBJECT);
    }
    @Bean
    public Queue importDevice () {
        return new Queue(Constants.QUEUE_KEY_IMPORT_DEVICE);
    }
    @Bean
    public DirectExchange exchangeOfSection (){
        return new DirectExchange(componentId);
    }
    @Bean
    public Binding bindingUploadPicture(){
        return BindingBuilder.bind(uploadPicture()).to(exchangeOfSection()).with(Constants.QUEUE_KEY_UPLOAD_PICTURE);
    }
    @Bean
    public Binding bindingUploadObject(){
        return BindingBuilder.bind(uploadObject()).to(exchangeOfSection()).with(Constants.QUEUE_KEY_UPLOAD_OBJECT);
    }
    @Bean
    public Binding bindingImportDevice(){
        return BindingBuilder.bind(importDevice()).to(exchangeOfSection()).with(Constants.QUEUE_KEY_IMPORT_DEVICE);
    }


    //Topic 测试
//    @Bean
//    public Queue chinanews () {
//        return new Queue("chinanews");
//    }
//    @Bean
//    public Queue chinaweather () {
//        return new Queue("chinaweather");
//    }
//    @Bean
//    public TopicExchange exchangeOfTopic (){
//        return new TopicExchange("testtopic");
//
//    }
//    @Bean
//    public Binding binding1(){
//        return BindingBuilder.bind(chinanews()).to(exchangeOfTopic()).with("china.#");
//    }
//    @Bean
//    public Binding binding2(){
//        return BindingBuilder.bind(chinaweather()).to(exchangeOfTopic()).with("da.china.#");
//    }



    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();

        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                logger.info("ConfirmCallback:     " + "相关数据：" + correlationData);
                logger.info("ConfirmCallback:     " + "确认情况：" + ack);
                logger.info("ConfirmCallback:     " + "原因：" + cause);
                //如果消息发送不成功,需要重新发送[组件内部处理，需要拿到发送的消息]
                if (!ack){
                    //将相关信息存数据库，定期重新发送
                }else{
                    //发送成功，如果是重新发送记录，那么删除记录
                }
            }
        });
        //如果正确达到消息队列就不执行
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                logger.info("ReturnCallback:     " + "消息：" + message);
                logger.info("ReturnCallback:     " + "回应码：" + replyCode);
                logger.info("ReturnCallback:     " + "回应信息：" + replyText);
                logger.info("ReturnCallback:     " + "交换机：" + exchange);
                logger.info("ReturnCallback:     " + "路由键：" + routingKey);
            }
        });

        return rabbitTemplate;
    }

    /**
     * 消费者设置手动确认模式
     */
    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

}
