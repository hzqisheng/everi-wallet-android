package com.qs.modulemain.bean;

public class TransferLogBean {

    /**
     * trx_id : 718834bf98a101cde3f0cf751a733784f6e5c76a701488a1bf43e491690cb6b9
     * name : transfer
     * domain : 599
     * key : wiwijee
     * data :
     * {
     * "to": [
     * "EVT8mFhLBK1J49Lv1kUD1CgvqCQN6nbTHzJ99v5BZ8T6A5CzFCczx"
     * ],
     * "memo": "",
     * "name": "wiwijee",
     * "domain": "599"
     * }
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

    public static class DataBean {

        private String[] to;
        private String memo;
        private String name;
        private String domain;

        public String[] getTo() {
            return to;
        }

        public void setTo(String[] to) {
            this.to = to;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
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
    }
}
