package com.qs.modulemain.bean;

import java.io.Serializable;

/**
 * 作者： MirsFang on 2018/12/19 23:15
 * 邮箱： mirsfang@163.com
 * 类描述：
 */
public class PayRequestBean implements Serializable {


    /**
     * from : address
     * to : address
     * number : asset
     * memo : string
     */

    private String from;
    private String to;
    private String number;
    private String memo;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
