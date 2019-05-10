package com.qs.modulemain.ui.widget;

import android.app.Activity;
import android.view.View;

import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;
import me.texy.treeview.base.BaseNodeViewBinder;
import me.texy.treeview.base.BaseNodeViewFactory;


/**
 * Created by zxy on 17/4/23.
 */

public class MyNodeViewFactory extends BaseNodeViewFactory {

    private TreeView mTreeView;
    private Activity mActivity;
    //是否是组详情页面
    private boolean isDetail;

    public void setmTreeView(TreeView mTreeView) {
        this.mTreeView = mTreeView;
    }

    private TreeNode mTreeNode;

    public MyNodeViewFactory(Activity activity, TreeNode treeNode, boolean isDetail) {
        this.mActivity = activity;
        this.mTreeNode = treeNode;
        this.isDetail = isDetail;
    }

    @Override
    public BaseNodeViewBinder getNodeViewBinder(View view, int level) {
        switch (level) {
            case 0:
                return new NonLeafNodeViewBinder(mActivity, mTreeView, mTreeNode, view, isDetail);
            case 1: //叶子节点
                return new LeafNodeViewBinder(mActivity, mTreeView, mTreeNode, view, isDetail);
            default:
                return null;
        }
    }
}
