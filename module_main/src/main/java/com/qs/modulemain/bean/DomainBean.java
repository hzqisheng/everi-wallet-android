package com.qs.modulemain.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： MirsFang on 2018/12/15 17:07
 * 邮箱： mirsfang@163.com
 * 类描述：
 */
public class DomainBean {

    /**
     * name : testingTmpData.newDomainName
     * creator : publicKey
     * issue : {"name":"issue","threshold":1,"authorizers":[{"ref":"[A]  + publicKey","weight":1}]}
     * transfer : {"name":"transfer","threshold":1,"authorizers":[{"ref":"[G] .OWNER","weight":1}]}
     * manage : {"name":"manage","threshold":1,"authorizers":[{"ref":"[A]  + publicKey","weight":1}]}
     */
    public static DomainBean build() {
        return new DomainBean();
    }

    public void setBuildName(String name) {
        this.name = name;
    }

    public void setBuildPublickKey(String publicKey) {
        this.creator = publicKey;
    }

    public void addIssueAuthorizersBean(IssueBean.AuthorizersBean authorizersBean) {
        if (issue == null) {
            issue = new IssueBean();
            issue.authorizers = new ArrayList<>();
        }

        issue.authorizers.add(authorizersBean);
        if (issue.authorizers.size() > 0) {
            issue.threshold = 1;
        }
    }

    public void removeIssueAuthorizersBean(IssueBean.AuthorizersBean authorizersBean) {
        if (issue == null || issue.authorizers == null || issue.authorizers.size() == 0) {
            return;
        }
        issue.authorizers.remove(authorizersBean);
        if (issue.authorizers.size() == 0) {
            issue.threshold = 0;
        }
    }

    public void addTransferAuthorizersBean(TransferBean.AuthorizersBeanX authorizersBeanX) {
        if (transfer == null) {
            transfer = new TransferBean();
            transfer.authorizers = new ArrayList<>();
        }

        transfer.authorizers.add(authorizersBeanX);
        if (transfer.authorizers.size() > 0) {
            transfer.threshold = 1;
        }
    }

    public void removeTransferAuthorizersBean(TransferBean.AuthorizersBeanX authorizersBeanX) {
        if (transfer == null || transfer.authorizers == null || transfer.authorizers.size() == 0) {
            return;
        }
        transfer.authorizers.remove(authorizersBeanX);
        if (transfer.authorizers.size() == 0) {
            transfer.threshold = 0;
        }
    }

    public void addManageAuthorizersBean(ManageBean.AuthorizersBeanXX authorizersBeanXX) {
        if (manage == null) {
            manage = new ManageBean();
            manage.authorizers = new ArrayList<>();
        }
        manage.authorizers.add(authorizersBeanXX);

        if (manage.authorizers.size() > 0) {
            manage.threshold = 1;
        }
    }

    public void removeManageAuthorizersBean(ManageBean.AuthorizersBeanXX authorizersBeanXX) {
        if (manage == null || manage.authorizers == null || manage.authorizers.size() == 0) {
            return;
        }
        manage.authorizers.remove(authorizersBeanXX);
        if (manage.authorizers.size() == 0) {
            manage.threshold = 0;
        }
    }


    private String name;
    private String creator;
    private IssueBean issue;
    private TransferBean transfer;
    private ManageBean manage;

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

    public static class IssueBean {
        /**
         * name : issue
         * threshold : 1
         * authorizers : [{"ref":"[A]  + publicKey","weight":1}]
         */

        private String name = "issue";
        private int threshold;
        private ArrayList<AuthorizersBean> authorizers;

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

        public ArrayList<AuthorizersBean> getAuthorizers() {
            return authorizers;
        }

        public void setAuthorizers(ArrayList<AuthorizersBean> authorizers) {
            this.authorizers = authorizers;
        }

        public static class AuthorizersBean {

            /**
             * ref : [A]  + publicKey
             * weight : 1
             */

            public AuthorizersBean() {
            }

            public AuthorizersBean(String ref, int weight) {
                this.ref = ref;
                this.weight = weight;
            }

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

    public static class TransferBean {
        /**
         * name : transfer
         * threshold : 1
         * authorizers : [{"ref":"[G] .OWNER","weight":1}]
         */

        private String name = "transfer";
        private int threshold;
        private ArrayList<AuthorizersBeanX> authorizers;

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

        public ArrayList<AuthorizersBeanX> getAuthorizers() {
            return authorizers;
        }

        public void setAuthorizers(ArrayList<AuthorizersBeanX> authorizers) {
            this.authorizers = authorizers;
        }

        public static class AuthorizersBeanX {
            /**
             * ref : [G] .OWNER
             * weight : 1
             */

            private String ref;
            private int weight;

            public AuthorizersBeanX() {
            }

            public AuthorizersBeanX(String ref, int weight) {
                this.ref = ref;
                this.weight = weight;
            }

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

    public static class ManageBean {
        /**
         * name : manage
         * threshold : 1
         * authorizers : [{"ref":"[A]  + publicKey","weight":1}]
         */

        private String name = "manage";
        private int threshold;
        private ArrayList<AuthorizersBeanXX> authorizers;

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

        public ArrayList<AuthorizersBeanXX> getAuthorizers() {
            return authorizers;
        }

        public void setAuthorizers(ArrayList<AuthorizersBeanXX> authorizers) {
            this.authorizers = authorizers;
        }

        public static class AuthorizersBeanXX {

            /**
             * ref : [A]  + publicKey
             * weight : 1
             */

            public AuthorizersBeanXX() {
            }

            public AuthorizersBeanXX(String ref, int weight) {
                this.ref = ref;
                this.weight = weight;
            }

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
