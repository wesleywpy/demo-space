package com.wesley.study;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by Wesley on 2018/6/25.
 */
@RestController
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    MQSender sender;

//	@RequestMapping("/mq/header")
//    public Result<String> header() {
//		sender.sendHeader("hello,imooc");
//        return Result.success("Hello，world");
//    }
//
//	@RequestMapping("/mq/fanout")
//    public Result<String> fanout() {
//		sender.sendFanout("hello,imooc");
//        return Result.success("Hello，world");
//    }

	@RequestMapping("/mq/topic")
    public Result<String> topic() {
		sender.sendTopic("hello,imooc");
        return Result.success("Hello，world");
    }

	@RequestMapping("/mq")
    public Result<String> mq() {
		sender.send("hello,imooc");
        return Result.success("Hello，world");
    }

}
