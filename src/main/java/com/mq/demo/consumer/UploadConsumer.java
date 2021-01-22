package com.mq.demo.consumer;

import com.google.gson.Gson;
import com.mq.demo.dto.UploadDTO;
import com.mq.demo.util.Constants;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName MqConsumer
 * @Author WeiWei10
 * @Date 2020/11/23 13:56
 * @Version 1.0
 */

@Component
public class UploadConsumer {
    private Gson gson =  new Gson();
    private static final Logger logger = LoggerFactory.getLogger(UploadConsumer.class);

    @RabbitListener(queues = Constants.QUEUE_KEY_UPLOAD_PICTURE)
    public void handleOfUploadPicture(Message message, Channel channel)  {
        UploadDTO uploadDTO = gson.fromJson(new String(message.getBody()), UploadDTO.class);
        try{
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            logger.error("手动确认消息出现异常");
        }
        logger.info("手动确认消息");
    }

}
