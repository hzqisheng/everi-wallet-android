package com.qs.modulemain.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class RootGroup(
        var name: String = "",
        var group: Group = Group()
)

data class Group(
        var name: String = "",
        var key: String = "",
        var root: Root = Root()
)

data class Root(
        var threshold: Int = 1,
        var weight: Int = 0,
        var nodes: List<Any> = listOf()
)

data class NonLeafNode(
        var threshold: Int = 1,
        var weight: Int = 1,
        var nodes: List<Any> = listOf()
)

//非叶子节点
@Parcelize
data class GroupNonLeafNode(
        var threshold: Int = 1,
        var weight: Int = 1
) : Parcelable

//叶子节点
@Parcelize
data class GroupLeafNode(
        var key: String = "",
        var weight: Int = 1
) : Parcelable