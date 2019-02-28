package com.qs.modulemain.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者： MirsFang on 2018/12/16 15:00
 * 邮箱： mirsfang@163.com
 * 类描述：
 */
public class ChooseGetBean implements Serializable {


    /**
     * name : ishaisiahsgsh
     * sym_name : APP
     * sym : 2,S#655312434
     * creator : EVT5qn48E8eZKJb5yM24bgC1m8MdRFg5eBU76cQfDXBGXr3UYjLvY
     * create_time : 2018-11-27T09:05:52
     * issue : {"name":"issue","threshold":1,"authorizers":[{"ref":"[A] EVT5qn48E8eZKJb5yM24bgC1m8MdRFg5eBU76cQfDXBGXr3UYjLvY","weight":1}]}
     * manage : {"name":"manage","threshold":0,"authorizers":[]}
     * total_supply : 100000.00 S#655312434
     * metas : [{"key":"symbol-icon","value":"data:image/jpeg;base64,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","creator":"[A] EVT5qn48E8eZKJb5yM24bgC1m8MdRFg5eBU76cQfDXBGXr3UYjLvY"}]
     * current_supply : 5500.00 S#655312434
     * address : EVT000000EghDJj1WdPn14dVhrmrfz2JjW8ZVVfENweV8N3DdZbVZ
     */

    private String name;
    private String sym_name;
    private String sym = "#,";
    private String creator;
    private String create_time;
    private IssueBean issue;
    private ManageBean manage;
    private String total_supply = " ";
    private String current_supply = " ";
    private String address;
    private String asset;
    private String domain;
    private List<MetasBean> metas;
    private boolean isChoose = false;

    public void addIssueAuthorizersBean(ChooseGetBean.IssueBean.AuthorizersBean authorizersBean) {
        if (issue == null) {
            issue = new ChooseGetBean.IssueBean();
            issue.authorizers = new ArrayList<>();
        }

        issue.authorizers.add(authorizersBean);
        if (issue.authorizers.size() > 0) {
            issue.threshold = 1;
        }
    }

    public void removeIssueAuthorizersBean(ChooseGetBean.IssueBean.AuthorizersBean authorizersBean) {
        if (issue == null || issue.authorizers == null || issue.authorizers.size() == 0) {
            return;
        }
        issue.authorizers.remove(authorizersBean);
        if (issue.authorizers.size() == 0) {
            issue.threshold = 0;
        }
    }


    public void addManageAuthorizersBean(ChooseGetBean.ManageBean.AuthorizersBeanXX authorizersBeanXX) {
        if (manage == null) {
            manage = new ChooseGetBean.ManageBean();
            manage.authorizers = new ArrayList<>();
        }
        manage.authorizers.add(authorizersBeanXX);

        if (manage.authorizers.size() > 0) {
            manage.threshold = 1;
        }
    }

    public void removeManageAuthorizersBean(ChooseGetBean.ManageBean.AuthorizersBeanXX authorizersBeanXX) {
        if (manage == null || manage.authorizers == null || manage.authorizers.size() == 0) {
            return;
        }
        manage.authorizers.remove(authorizersBeanXX);
        if (manage.authorizers.size() == 0) {
            manage.threshold = 0;
        }
    }


    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSym_name() {
        return sym_name;
    }

    public void setSym_name(String sym_name) {
        this.sym_name = sym_name;
    }

    public String getSym() {
        //return sym;
        if (sym == null || "".equals(sym) || "#,".equals(sym)) {
            return asset;
        } else {
            return sym;
        }
    }

    public void setSym(String sym) {
        this.sym = sym;
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

    public ManageBean getManage() {
        return manage;
    }

    public void setManage(ManageBean manage) {
        this.manage = manage;
    }

    public String getTotal_supply() {
        if (total_supply == null || "".equals(total_supply) || " ".equals(total_supply)) {
            return "  ";
        } else {
            return total_supply;
        }
    }

    public void setTotal_supply(String total_supply) {
        this.total_supply = total_supply;
    }

    public String getCurrent_supply() {
        if (current_supply == null || "".equals(current_supply) || " ".equals(current_supply)) {
            return "  ";
        } else {
            return current_supply;
        }
    }

    public void setCurrent_supply(String current_supply) {
        this.current_supply = current_supply;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<MetasBean> getMetas() {
        return metas;
    }

    public void setMetas(List<MetasBean> metas) {
        this.metas = metas;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public static class IssueBean implements Serializable {
        /**
         * name : issue
         * threshold : 1
         * authorizers : [{"ref":"[A] EVT5qn48E8eZKJb5yM24bgC1m8MdRFg5eBU76cQfDXBGXr3UYjLvY","weight":1}]
         */

        private String name = "issue";
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

    public static class ManageBean implements Serializable {
        /**
         * name : manage
         * threshold : 0
         * authorizers : []
         */

        private String name = "manage";
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

    public static class MetasBean implements Serializable {
        /**
         * key : symbol-icon
         * value : data:image/jpeg;base64,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
         * creator : [A] EVT5qn48E8eZKJb5yM24bgC1m8MdRFg5eBU76cQfDXBGXr3UYjLvY
         */

        private String key;
        private String value;
        private String creator;

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

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }
    }
}
