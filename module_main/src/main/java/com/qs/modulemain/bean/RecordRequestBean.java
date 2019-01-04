package com.qs.modulemain.bean;

import java.util.List;

/**
 * 作者： MirsFang on 2018/12/20 13:21
 * 邮箱： mirsfang@163.com
 * 类描述：
 */
public class RecordRequestBean {


    /**
     * domain : .fungible
     * names : []
     * key : 5522
     * skip : 0
     * take : 2
     */

    private String domain;
    private int key;
    private int skip;
    private int take;
    private List<?> names;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getTake() {
        return take;
    }

    public void setTake(int take) {
        this.take = take;
    }

    public List<?> getNames() {
        return names;
    }

    public void setNames(List<?> names) {
        this.names = names;
    }
}
