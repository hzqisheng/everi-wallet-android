package com.qs.modulemain.bean;

/**
 * 作者： MirsFang on 2018/12/18 21:21
 * 邮箱： mirsfang@163.com
 * 类描述：扫码收款实体
 */
public class ReceScanBean {


    public static ReceScanBean createBean(String scanLink,String publicKey,String num,String ftsId){
        ReceScanBean scanBean = new ReceScanBean();
        scanBean.link = scanLink;
        scanBean.payee = publicKey;
        scanBean.number = num+" S#"+ftsId;
        return scanBean;
    }
    /**
     * link : 0UKDWC$9XLNQUQPR+5V4HC*B$:JH3Q40BIYD-U2G6X6A417ML
     * payee : EVT5qn48E8eZKJb5yM24bgC1m8MdRFg5eBU76cQfDXBGXr3UYjLvY
     * number : 10 S#155110011
     */

    private String link;
    private String payee;
    private String number;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
