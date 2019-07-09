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
    val androidUploadUrl: String,
    val androidEnUploadMessage: String,
    val androidChUploadMessage: String,
    val iOSVersion: String,
    val iOSUploadUrl: String,
    val iOSEnUploadMessage: String,
    val iOSChUploadMessage: String,
    val isAndroidForceUpdate: Boolean
)