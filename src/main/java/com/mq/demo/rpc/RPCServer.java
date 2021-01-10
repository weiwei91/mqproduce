package com.mq.demo.rpc;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.mq.demo.config.RabbitConfig.QUEUE_SYNC_RPC;


@Component
@RabbitListener(queues = QUEUE_SYNC_RPC)
public class RPCServer {

    @RabbitHandler
    public String process(String message) {
        int millis = (int) (Math.random() * 2 * 1000);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
        return message + "同步rpc sleep for " + millis + " ms";
    }

}
