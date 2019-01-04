package com.smallcat.shenhai.mvpbase.model.bean;

/**
 * 作者： MirsFang on 2018/12/15 18:44
 * 邮箱： mirsfang@163.com
 * 类描述：
 */
public class ResultBean {
    /**
     * code : 1
     * data :
     * message :
     */

    private int code;
    private Object data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
