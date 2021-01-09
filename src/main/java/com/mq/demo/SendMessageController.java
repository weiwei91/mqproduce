package com.mq.demo;
import com.mq.demo.service.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Author : JCccc
 * @CreateTime : 2019/9/3
 * @Description :
 **/
@RestController
public class SendMessageController {
    @Autowired
    IndicatorService indicatorService;

    @RequestMapping(value = "/sendMessage",method = RequestMethod.GET)
    @ResponseBody
    public String getCameras(@RequestParam(value = "name",required = false) String name) {
        indicatorService.sendMessage("topic1","ceshi");
        return "ok";
    }

}
