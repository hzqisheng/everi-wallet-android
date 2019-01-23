package com.qs.modulemain.bean

data class NodeBean(
        var nodeAddress: String = "",
        var nodeName: String = "",
        var isChoose: Boolean = false
)

data class HelpCenterBean(
        var title: String = "",
        var msg: String = ""
)

data class VersionBean(
    val androidVersion: String,
    val iOSVersion: String,
    val isAndroidForceUpdate: Boolean
)