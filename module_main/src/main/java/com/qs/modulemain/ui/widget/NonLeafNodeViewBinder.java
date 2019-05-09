package com.qs.modulemain.ui.widget;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qs.modulemain.R;
import com.qs.modulemain.bean.GroupNonLeafNode;
import com.qs.modulemain.ui.activity.manage.AddNoteActivity;
import com.qs.modulemain.ui.activity.manage.CreateGroupActivity;

import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;
import me.texy.treeview.base.CheckableNodeViewBinder;

import static com.qs.modulemain.ui.activity.manage.CreateGroupActivityKt.nodeRequestCode;

/**
 * Created by zxy on 17/4/23.
 */

public class NonLeafNodeViewBinder extends CheckableNodeViewBinder {
    TreeView mTreeView;
    TreeNode mRootNode;
    LinearLayout llContent;
    ImageView ivExpand;
    TextView tvDes;
    ImageView ivAdd;
    ImageView ivDelete;
    Activity mActivity;

    public NonLeafNodeViewBinder(Activity activity, TreeView treeView, TreeNode treeNode, View itemView) {
        super(itemView);
        this.mActivity = activity;
        this.mTreeView = treeView;
        this.mRootNode = treeNode;
        llContent = (LinearLayout) itemView.findViewById(R.id.node_container);
        ivExpand = (ImageView) itemView.findViewById(R.id.arrow_img);
        tvDes = (TextView) itemView.findViewById(R.id.node_name_view);
        ivAdd = (ImageView) itemView.findViewById(R.id.iv_add);
        ivDelete = (ImageView) itemView.findViewById(R.id.iv_delete);
    }

    @Override
    public int getCheckableViewId() {
        return R.id.checkBox;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_node_nonleaf;
    }

    @Override
    public void bindView(final TreeNode treeNode) {
        llContent.setPadding(30 * treeNode.getLevel(), 0, 0, 0);
        ivExpand.setRotation(treeNode.isExpanded() ? 90 : 0);
        GroupNonLeafNode value = (GroupNonLeafNode) treeNode.getValue();
        StringBuilder sb = new StringBuilder(mActivity.getString(R.string.threshold));
        sb.append(":");
        sb.append(value.getThreshold());
        sb.append(" ");
        sb.append(mActivity.getString(R.string.weight));
        sb.append(":");
        sb.append(value.getWeight());
        tvDes.setText(sb.toString());
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateGroupActivity.Companion.setAddLevel1Node(false);
                CreateGroupActivity.Companion.setCurrentAddNode(treeNode);
                Intent intent = new Intent(mActivity, AddNoteActivity.class);
                intent.putExtra("currentLevel", treeNode.getLevel());
                mActivity.startActivityForResult(intent, nodeRequestCode);
            }
        });
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRootNode.removeChild(treeNode);
                mTreeView.refreshTreeView();
            }
        });
    }

    @Override
    public void onNodeToggled(TreeNode treeNode, boolean expand) {
        if (expand) {
            ivExpand.animate().rotation(90).setDuration(200).start();
        } else {
            ivExpand.animate().rotation(0).setDuration(200).start();
        }
    }
}
