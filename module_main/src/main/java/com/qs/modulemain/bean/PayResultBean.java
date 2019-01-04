package com.qs.modulemain.bean;

import java.util.List;

/**
 * 作者： MirsFang on 2018/12/21 22:49
 * 邮箱： mirsfang@163.com
 * 类描述：
 */
public class PayResultBean {


    /**
     * flag : 17
     * segments : [{"typeKey":45,"value":1},{"typeKey":95,"value":"EVT6Gx3o9P4BAmcjxGenBgmAQ82bgfvS8GHgrFPUPzYUn6uxWdLFW"}]
     * publicKeys : []
     * signatures : []
     */

    private int flag;
    private List<SegmentsBean> segments;
    private List<?> publicKeys;
    private List<?> signatures;

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

    public List<?> getPublicKeys() {
        return publicKeys;
    }

    public void setPublicKeys(List<?> publicKeys) {
        this.publicKeys = publicKeys;
    }

    public List<?> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<?> signatures) {
        this.signatures = signatures;
    }

    public static class SegmentsBean {
        /**
         * typeKey : 45
         * value : 1
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
