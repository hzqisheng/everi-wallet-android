package com.qs.modulemain.bean;

import java.util.List;

/**
 * 作者： MirsFang on 2018/12/20 22:58
 * 邮箱： mirsfang@163.com
 * 类描述：
 */
public class RecordDetailBean {


    /**
     * trx_id : 718834bf98a101cde3f0cf751a733784f6e5c76a701488a1bf43e491690cb6b9
     * name : transferft
     * domain : .fungible
     * key : 521521521
     * data : {"to":"EVT6Jkvov6UMEnB3n1bELKtdg4FCUUAHdC8V3DWsuaAm8wuA9LJFY","from":"EVT6Gx3o9P4BAmcjxGenBgmAQ82bgfvS8GHgrFPUPzYUn6uxWdLFW","memo":"哈","number":"10.00000 S#521521521"}
     * timestamp : 2018-12-20 05:45:58+00
     */

    private String trx_id;
    private String name;
    private String domain;
    private String key;
    private DataBean data;
    private String timestamp;

    public String getTrx_id() {
        return trx_id;
    }

    public void setTrx_id(String trx_id) {
        this.trx_id = trx_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public static class LinkBean{

        /**
         * keys : ["EVT8Um4idufDFtyk6z3HLg3vKNyJyYTCiTstWDshVsumGZvXyQdb9"]
         * header : 5
         * segments : [{"key":42,"value":1546766728},{"key":43,"value":10000},{"key":44,"value":655312434},{"key":156,"value":"fd94ac14e8ae71ae56fdfbc01f0de5c8"}]
         * signatures : ["SIG_K1_Kay6rcUvC7auuF9vfyxdgAFUSFXMm9vtKLnVm2txaR3vdeVndDMDDKQeuCwYCRgmor4ou6fJ2V6JVVra2GMsw5DZLREPn9"]
         */

        private List<String> keys;



        public List<String> getKeys() {
            return keys;
        }

        public void setKeys(List<String> keys) {
            this.keys = keys;
        }
    }

    public static class DataBean {
        /**
         * to : EVT6Jkvov6UMEnB3n1bELKtdg4FCUUAHdC8V3DWsuaAm8wuA9LJFY
         * from : EVT6Gx3o9P4BAmcjxGenBgmAQ82bgfvS8GHgrFPUPzYUn6uxWdLFW
         * memo : 哈
         * number : 10.00000 S#521521521
         */

        private String to;
        private String from;
        private String memo;
        private String number;
        private String payee;
        private LinkBean link;

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPayee() {
            return payee;
        }

        public void setPayee(String payee) {
            this.payee = payee;
        }

        public LinkBean getLink() {
            return link;
        }

        public void setLink(LinkBean link) {
            this.link = link;
        }
    }
}
