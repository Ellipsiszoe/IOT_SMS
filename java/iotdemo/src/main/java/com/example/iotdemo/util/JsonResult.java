package com.example.iotdemo.util;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JsonResult<E> implements Serializable {
    // 状态码
    private Integer state;
    // 状态描述信息
    private String message;
    // Token
    private String token;
    // 数据
    private E data;

    public JsonResult() {
        super();
    }

    public JsonResult(Integer state) {
        super();
        this.state = state;
    }

    /**
     * 出现异常时调用
     */
    public JsonResult(Throwable e) {
        super();
        // 获取异常对象中的异常信息
        this.message = e.getMessage();
    }

    public JsonResult(Integer state, E data) {
        super();
        this.state = state;
        this.data = data;
    }

}
