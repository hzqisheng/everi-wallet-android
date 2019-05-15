package com.qs.modulemain.bean

data class DomainDetailBean(
        val name: String = "",
        val creator: String = "",
        val create_time: String = "",
        val issue: DomainGroup = DomainGroup("issue"),
        val transfer: DomainGroup = DomainGroup("transfer"),
        val manage: DomainGroup = DomainGroup("manage"),
        val metas: ArrayList<DetailMetas> = arrayListOf(),
        val address: String = ""
)

data class DomainGroup(val name: String,
                       val threshold: Int = 1,
                       val authorizers: ArrayList<Authorizer> = arrayListOf())

data class Authorizer(
        val ref: String,
        val weight: Int = 1
)