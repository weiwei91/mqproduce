package com.mq.demo;

import com.mq.demo.rpc.RPCClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RPCClientTest extends DemoApplicationTests {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RPCClient rpcClient;

    @Test
    public void testSend() {
        String result = rpcClient.send("hello world");
        logger.info(result);
    }

    @Test
    public void testSendAsync() throws InterruptedException, ExecutionException {
        String[] messages = { "hello", "my", "name", "is", "leijun" };
        List<Future<String>> results = new ArrayList<>();
        for (String message : messages) {
            Future<String> result = rpcClient.sendAsync(message);
            results.add(result);
        }
        for (Future<String> future : results) {
            String result = future.get();
            if (result == null) {
                logger.info("message timeout after 5 seconds");
            } else {
                logger.info(result);
            }
        }
    }

}
