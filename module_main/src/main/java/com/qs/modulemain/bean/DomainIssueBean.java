package com.qs.modulemain.bean;

import java.util.List;

/**
 * 作者： MirsFang on 2018/12/16 17:19
 * 邮箱： mirsfang@163.com
 * 类描述：
 */
public class DomainIssueBean {


    /**
     * domain : test
     * names : ["t1","t2","t3"]
     * owner : ["EVT8MGU4aKiVzqMtWi9zLpu8KuTHZWjQQrX475ycSxEkLd6aBpraX"]
     */

    private String domain;
    private List<String> names;
    private List<String> owner;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getOwner() {
        return owner;
    }

    public void setOwner(List<String> owner) {
        this.owner = owner;
    }
}
