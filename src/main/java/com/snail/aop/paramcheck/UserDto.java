package com.snail.aop.paramcheck;

import java.io.Serializable;

import com.snail.aop.annotaion.Check;
import lombok.Data;

@SuppressWarnings("serial")
@Data
public class UserDto implements Serializable{
    /**
     * 编码
     */
    @Check(type=CheckType.REQUEST_NO,message="编码格式错误")
    private String requestNo;

    @Check(type=CheckType.TOOLOOG_DATE,message="时间戳格式不正确，格式：yyyyMMddHHmmssSSS")
    private String timestamp;

    public UserDto(String requestNo, String timestamp) {
        this.requestNo = requestNo;
        this.timestamp = timestamp;
    }
}
