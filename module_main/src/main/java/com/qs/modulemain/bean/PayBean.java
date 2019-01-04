package com.qs.modulemain.bean;

import java.util.List;

/**
 * 作者： MirsFang on 2018/12/18 22:49
 * 邮箱： mirsfang@163.com
 * 类描述：
 */
public class PayBean {

    /**
     * keyProvider : ["5JrNgyyNDqz2pikijgdJwUktV8xkS7JPPSURr2YwxkhKPzm2eRi"]
     * symbol : 1
     * maxAmount : 111
     * linkId : ss
     */

    private int symbol;
    private float maxAmount;
    private String linkId;
    private List<String> keyProvider;

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    public float getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(float maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public List<String> getKeyProvider() {
        return keyProvider;
    }

    public void setKeyProvider(List<String> keyProvider) {
        this.keyProvider = keyProvider;
    }
}
