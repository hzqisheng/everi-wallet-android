package com.qs.modulemain.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者： MirsFang on 2018/12/12 22:37
 * 邮箱： mirsfang@163.com
 * 类描述：
 */
public class ChooseFTSBean implements Serializable {


    /**
     * name : Hhhh
     * creator : EVT5qn48E8eZKJb5yM24bgC1m8MdRFg5eBU76cQfDXBGXr3UYjLvY
     * create_time : 2018-11-18T05:08:49
     * issue : {"name":"issue","threshold":1,"authorizers":[{"ref":"[A] EVT5qn48E8eZKJb5yM24bgC1m8MdRFg5eBU76cQfDXBGXr3UYjLvY","weight":1}]}
     * transfer : {"name":"transfer","threshold":1,"authorizers":[{"ref":"[G] .OWNER","weight":1}]}
     * manage : {"name":"manage","threshold":1,"authorizers":[{"ref":"[A] EVT5qn48E8eZKJb5yM24bgC1m8MdRFg5eBU76cQfDXBGXr3UYjLvY","weight":1}]}
     * metas : []
     * address : EVT0000007DtHBKyx5fN6ZFyKMhiwbQKHrSL5ESFEAGR5z8zMk5W7
     */

    private String name;
    private String creator;
    private String create_time;
    private IssueBean issue;
    private TransferBean transfer;
    private ManageBean manage;
    private String address;
    private List<MetasBean> metas;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public IssueBean getIssue() {
        return issue;
    }

    public void setIssue(IssueBean issue) {
        this.issue = issue;
    }

    public TransferBean getTransfer() {
        return transfer;
    }

    public void setTransfer(TransferBean transfer) {
        this.transfer = transfer;
    }

    public ManageBean getManage() {
        return manage;
    }

    public void setManage(ManageBean manage) {
        this.manage = manage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<MetasBean> getMetas() {
        return metas;
    }

    public void setMetas(List<MetasBean> metas) {
        this.metas = metas;
    }

    public static class IssueBean implements Serializable {
        /**
         * name : issue
         * threshold : 1
         * authorizers : [{"ref":"[A] EVT5qn48E8eZKJb5yM24bgC1m8MdRFg5eBU76cQfDXBGXr3UYjLvY","weight":1}]
         */

        private String name;
        private int threshold;
        private List<AuthorizersBean> authorizers;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getThreshold() {
            return threshold;
        }

        public void setThreshold(int threshold) {
            this.threshold = threshold;
        }

        public List<AuthorizersBean> getAuthorizers() {
            return authorizers;
        }

        public void setAuthorizers(List<AuthorizersBean> authorizers) {
            this.authorizers = authorizers;
        }

        public static class AuthorizersBean implements Serializable {
            /**
             * ref : [A] EVT5qn48E8eZKJb5yM24bgC1m8MdRFg5eBU76cQfDXBGXr3UYjLvY
             * weight : 1
             */

            private String ref;
            private int weight;

            public String getRef() {
                return ref;
            }

            public void setRef(String ref) {
                this.ref = ref;
            }

            public int getWeight() {
                return weight;
            }

            public void setWeight(int weight) {
                this.weight = weight;
            }
        }
    }

    public static class MetasBean implements Serializable{

        /**
         * key : symbol-icon
         * value : da
         */

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class TransferBean implements Serializable {
        /**
         * name : transfer
         * threshold : 1
         * authorizers : [{"ref":"[G] .OWNER","weight":1}]
         */

        private String name;
        private int threshold;
        private List<AuthorizersBeanX> authorizers;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getThreshold() {
            return threshold;
        }

        public void setThreshold(int threshold) {
            this.threshold = threshold;
        }

        public List<AuthorizersBeanX> getAuthorizers() {
            return authorizers;
        }

        public void setAuthorizers(List<AuthorizersBeanX> authorizers) {
            this.authorizers = authorizers;
        }

        public static class AuthorizersBeanX implements Serializable {
            /**
             * ref : [G] .OWNER
             * weight : 1
             */

            private String ref;
            private int weight;

            public String getRef() {
                return ref;
            }

            public void setRef(String ref) {
                this.ref = ref;
            }

            public int getWeight() {
                return weight;
            }

            public void setWeight(int weight) {
                this.weight = weight;
            }
        }
    }

    public static class ManageBean implements Serializable {
        /**
         * name : manage
         * threshold : 1
         * authorizers : [{"ref":"[A] EVT5qn48E8eZKJb5yM24bgC1m8MdRFg5eBU76cQfDXBGXr3UYjLvY","weight":1}]
         */

        private String name;
        private int threshold;
        private List<AuthorizersBeanXX> authorizers;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getThreshold() {
            return threshold;
        }

        public void setThreshold(int threshold) {
            this.threshold = threshold;
        }

        public List<AuthorizersBeanXX> getAuthorizers() {
            return authorizers;
        }

        public void setAuthorizers(List<AuthorizersBeanXX> authorizers) {
            this.authorizers = authorizers;
        }

        public static class AuthorizersBeanXX implements Serializable {
            /**
             * ref : [A] EVT5qn48E8eZKJb5yM24bgC1m8MdRFg5eBU76cQfDXBGXr3UYjLvY
             * weight : 1
             */

            private String ref;
            private int weight;

            public String getRef() {
                return ref;
            }

            public void setRef(String ref) {
                this.ref = ref;
            }

            public int getWeight() {
                return weight;
            }

            public void setWeight(int weight) {
                this.weight = weight;
            }
        }
    }
}
