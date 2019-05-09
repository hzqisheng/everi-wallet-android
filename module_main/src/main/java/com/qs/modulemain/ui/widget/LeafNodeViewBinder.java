package com.qs.modulemain.ui.widget;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qs.modulemain.R;
import com.qs.modulemain.bean.GroupLeafNode;

import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;
import me.texy.treeview.base.CheckableNodeViewBinder;

/**
 * Created by zxy on 17/4/23.
 */

public class LeafNodeViewBinder extends CheckableNodeViewBinder {
    TreeView mTreeView;
    TreeNode mRootNode;
    LinearLayout llContent;
    //    ImageView ivExpand;
    TextView tvDes;
    ImageView ivDelete;
    Activity mActivity;

    public LeafNodeViewBinder(Activity activity, TreeView treeView, TreeNode treeNode, View itemView) {
        super(itemView);
        this.mActivity = activity;
        this.mTreeView = treeView;
        this.mRootNode = treeNode;
        llContent = (LinearLayout) itemView.findViewById(R.id.node_container);
//        ivExpand = (ImageView) itemView.findViewById(R.id.arrow_img);
        tvDes = (TextView) itemView.findViewById(R.id.node_name_view);
        ivDelete = (ImageView) itemView.findViewById(R.id.iv_delete);
    }

    @Override
    public int getCheckableViewId() {
        return R.id.checkBox;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_node_leaf;
    }

    @Override
    public void bindView(final TreeNode treeNode) {
        llContent.setPadding(39 * treeNode.getLevel(), 0, 0, 0);
//        ivExpand.setRotation(treeNode.isExpanded() ? 90 : 0);
        GroupLeafNode value = (GroupLeafNode) treeNode.getValue();
        StringBuilder sb = new StringBuilder(value.getKey());
        sb.append(" ");
        sb.append(mActivity.getString(R.string.weight));
        sb.append(":");
        sb.append(value.getWeight());
        tvDes.setText(sb.toString());
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRootNode.removeChild(treeNode);
                mTreeView.refreshTreeView();
            }
        });
    }

//    @Override
//    public void onNodeToggled(TreeNode treeNode, boolean expand) {
//        if (expand) {
//            ivExpand.animate().rotation(90).setDuration(200).start();
//        } else {
//            ivExpand.animate().rotation(0).setDuration(200).start();
//        }
//    }
}
