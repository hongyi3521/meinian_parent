package com.hongyi.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Result implements Serializable {

    private Integer code;
    private boolean flag; // 执行结果，true为执行成功 false为执行失败
    private String message;// 返回结果信息
    private Map<String, Object> data = new HashMap<>();//返回数据

    // 如果执行成功调用方法，设置默认数据
    public static Result ok() {
        Result result = new Result();
        result.setFlag(true);
        result.setMessage("成功");
        return result;
    }

    //如果出现情况失败调用方法
    public static Result error() {
        Result result = new Result();
        result.setFlag(false);
        result.setMessage("失败");
        return result;
    }

    // 设置成功与否，并返回结果
    public Result flag(boolean flag) {
        this.flag = flag;
        return this;
    }

    // 设置消息，并返回结果
    public Result message(String message) {
        this.message = message;
        return this;
    }
    // 设置消息，并返回结果
    public Result code(Integer code) {
        this.code = code;
        return this;
    }

    // 设置返回的结果数据,方法1，在外面newmap集合，直接设置data
    public Result data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    // 设置返回的结果数据，方法2，传入键值对，一个个添加put
    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Result() {
    }

    public Result(boolean flag, String message, Map<String, Object> data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
