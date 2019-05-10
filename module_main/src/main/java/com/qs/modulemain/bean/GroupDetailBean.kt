package com.qs.modulemain.bean

data class GroupDetailBean(
        val name: String = "",
        val key: String = "",
        val root: DetailRoot = DetailRoot(),
        val metas: List<DetailMetas> = listOf()
)

data class DetailRoot(
        val threshold: Int = 1,
        val nodes: List<Any> = listOf())


data class DetailMetas(
        val key: String = "",
        val value: String = "",
        val creator: String = "")