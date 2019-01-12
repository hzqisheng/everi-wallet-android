package com.qs.modulemain.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： MirsFang on 2018/12/14 09:49
 * 邮箱： mirsfang@163.com
 * 类描述：发型币的实体
 */
public class AddFTSBean {

    public static AddFTSBean create(String symbol,String allName, String symbol_id, String publicKey, int count, int jingdu, int power,String base64Image) {
        AddFTSBean addFTSBean = new AddFTSBean();
        ManageBean manageBean = new ManageBean();
        List<ManageBean.AuthorizersBean> authorizersBeanList = new ArrayList<>();

        if(power == 0) {
            ManageBean.AuthorizersBean authorizersBean = new ManageBean.AuthorizersBean();
            authorizersBean.ref = "[A] " + publicKey;
            authorizersBeanList.add(authorizersBean);
            manageBean.threshold = 1;
        }

        manageBean.authorizers = authorizersBeanList;
        addFTSBean.manage = manageBean;

        IssueBean issueBean = new IssueBean();
        List<IssueBean.AuthorizersBeanX> beanXES = new ArrayList<>();

        IssueBean.AuthorizersBeanX beanX = new IssueBean.AuthorizersBeanX();
        beanX.ref = "[A] " + publicKey;

        beanXES.add(beanX);
        issueBean.authorizers = beanXES;
        addFTSBean.issue = issueBean;

        addFTSBean.creator = publicKey;
        addFTSBean.name = allName;
        addFTSBean.sym = jingdu + ",S#" + symbol_id;
        addFTSBean.sym_name = symbol;
        String jCount = count + ".";
        for (int i = 0; i < jingdu; i++) {
            jCount += "0";
        }
        addFTSBean.total_supply = jCount + " S#" + symbol_id;

        if(base64Image == null || !base64Image.isEmpty()) {
            List<MetasBean> metasBeans = new ArrayList<>();
            MetasBean metasBean = new MetasBean();
            metasBean.key = "symbol-icon";
            metasBean.value = base64Image;
            metasBeans.add(metasBean);
            addFTSBean.metas = metasBeans;
        }
        return addFTSBean;
    }

    private static int getNumberDecimalDigits(double number) {
        if (number == (long) number) {
            return 0;
        }
        int i = 0;
        while (true) {
            i++;
            if (number * Math.pow(10, i) % 1 == 0) {
                return i;
            }
        }
    }

    /**
     * name : symbol + .POINTS
     * sym_name : symbol
     * sym : 5, S# + symbol_id
     * creator : publicKey
     * manage : {"name":"manage","threshold":1,"authorizers":[{"ref":"[A] + publicKey","weight":1}]}
     * issue : {"name":"issue","threshold":1,"authorizers":[{"ref":"[A]  + publicKey","weight":1}]}
     * total_supply : 100000.00000 S# + symbol_id
     */

    private String name;
    private String sym_name;
    private String sym;
    private String creator;
    private ManageBean manage;
    private IssueBean issue;
    private String total_supply;
    private List<MetasBean> metas;

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

    public void setMetas(List<MetasBean> metas){
        this.metas = metas;
    }

    public List<MetasBean> getMetas(){
        return metas;
    }

    public String getSym() {
        return sym;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String publicKey) {
        this.creator = publicKey;
    }

    public ManageBean getManage() {
        return manage;
    }

    public void setManage(ManageBean manage) {
        this.manage = manage;
    }

    public IssueBean getIssue() {
        return issue;
    }

    public void setIssue(IssueBean issue) {
        this.issue = issue;
    }

    public String getTotal_supply() {
        return total_supply;
    }

    public void setTotal_supply(String total_supply) {
        this.total_supply = total_supply;
    }

    public static class ManageBean {
        /**
         * name : manage
         * threshold : 1
         * authorizers : [{"ref":"[A] + publicKey","weight":1}]
         */

        private String name = "manage";
        private int threshold = 0;
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

        public static class AuthorizersBean {
            /**
             * ref : [A] + publicKey
             * weight : 1
             */

            private String ref;
            private int weight = 1;

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

    public static class IssueBean {
        /**
         * name : issue
         * threshold : 1
         * authorizers : [{"ref":"[A]  + publicKey","weight":1}]
         */

        private String name = "issue";
        private int threshold = 1;
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

        public static class AuthorizersBeanX {
            /**
             * ref : [A]  + publicKey
             * weight : 1
             */

            private String ref;
            private int weight = 1;

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

    public static class MetasBean{

        /**
         * key : symbol-icon
         * value :
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
}
