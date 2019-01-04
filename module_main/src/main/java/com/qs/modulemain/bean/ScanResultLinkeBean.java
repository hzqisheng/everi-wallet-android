package com.qs.modulemain.bean;

import java.util.List;

/**
 * 作者： MirsFang on 2018/12/23 19:20
 * 邮箱： mirsfang@163.com
 * 类描述：
 */
public class ScanResultLinkeBean {


    /**
     * flag : 5 2 收款  4 付款
     * segments : [{"typeKey":42,"value":1545563911},{"typeKey":43,"value":1000000},{"typeKey":44,"value":1},{"typeKey":156,"value":{"type":"Buffer","data":[221,161,247,211,133,192,5,87,65,97,255,214,54,48,220,134]}}]
     * publicKeys : ["EVT5tnrA4FqBvkKgxFeYa9YKe2bTUF1UaxQR1cqtiasF7RUDXP5v7"]
     * signatures : ["SIG_K1_KgA8YM3k6c3XdrsW8bGQtcXfYWPkFrG6LD6U4N1eou8ATwELC42c6esQWZ9YeEiHsY7zstSxLna2ZKTnTdfynaQBLsmA67"]
     */

    private int flag;
    private List<SegmentsBean> segments;
    private List<String> publicKeys;
    private List<String> signatures;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<SegmentsBean> getSegments() {
        return segments;
    }

    public void setSegments(List<SegmentsBean> segments) {
        this.segments = segments;
    }

    public List<String> getPublicKeys() {
        return publicKeys;
    }

    public void setPublicKeys(List<String> publicKeys) {
        this.publicKeys = publicKeys;
    }

    public List<String> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<String> signatures) {
        this.signatures = signatures;
    }

    public static class SegmentsBean {
        /**
         * typeKey : 42
         * value : 1545563911
         */

        private int typeKey;
        private Object value;

        public int getTypeKey() {
            return typeKey;
        }

        public void setTypeKey(int typeKey) {
            this.typeKey = typeKey;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
