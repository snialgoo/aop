package com.snail.aop.controller;

import com.snail.aop.annotaion.OpLog;
import com.snail.aop.operation.OpType;
import com.snail.aop.paramcheck.UserDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/demo")
public class DemoController {

    private String acb = "";

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    /**
     * aop参数校验
     * @param user
     * @return
     */
    @PostMapping("/check")
    public Object test(@RequestBody @Valid UserDto user) {
        return "";
    }

    /**
     * aop 请求操作记录
     * @param id
     * @return
     */
    @GetMapping("/log")
    @OpLog(opType = OpType.ADD,opItem = "为了啥啥而新增",OpItemIdExpression = "#userDto.requestNo")
    public Object oplog(String id) {
        return new UserDto("no","data");
    }
}