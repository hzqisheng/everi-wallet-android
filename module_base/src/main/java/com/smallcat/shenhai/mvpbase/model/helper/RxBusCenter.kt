package com.smallcat.shenhai.mvpbase.model.helper

/**
 * Created by hui on 2018/8/23.
 */
object RxBusCenter {

    const val LOGIN = 10001
    const val MY_FTS = 10002
    const val MY_NFTS = 10003
    const val MY_NFTS_ACTIVITY = 10004

    //选择代币
    const val CHOOSE_FTS = 0x2001

    //创建代币
    const val ADD_FTS = 0x2002
    //发行代币
    const val ISSUE_FTS = 0x2004
    //需要密码
    const val NEED_PRIVATE_KEY = 0x2003
    //创建域
    const val CREATE_DOMAIN = 0x2005 //创建域
    //我的域
    const val MY_DOMAIN = 0x2006
    //设置代币
    const val SET_FTS = 0x2007
    //发行域
    const val ISSUE_DOMAIN = 0x2008

    //付款
    const val QRCODE_PAL = 0x2009

    //收款
    const val QRCODE_RECE = 0x2010

    //收款扫码
    const val SCAN_RECE = 0x2011

    //获取linkId
    const val GET_LINkID = 0x2012

    //请求付款
    const val REQUEST_PAY = 0x2013

    //交易记录
    const val RECORD_TRANSACATION = 0x2014

    //首页代币
    const val HOME_FTS = 0x2015
    //创建钱包
    const val CREATE_WALL = 0x2016

    //手续费
    const val SERVICE_CHARGE = 0x2017
    //私钥转获取公钥
    const val PRIVATE_TO_PUBLIC = 0x2018

    //解析扫码收款地址
    const val SCAN_QRLINKE = 0x2019

    //获取币的详细信息
    const val SYMBOL_DETAIL = 0x2020

    //交易记录
    const val PAY_RECORD = 0x2021

    //通证交易
    const val PAY_NFTS = 0x2022

    //上传图片
    const val UPLOAD_IMG = 0x2023

    //验证助记词
    const val CHECK_MEMO = 0x2024

    //验证私钥
    const val CHECK_PRIVATE = 0x2025

    //修改节点
    const val CHANGE_NODE = 0x2026

    //交易数据
    const val TRAN_MSG = 0x2027

    //检查更新
    const val CHECK_VERSION = 0x2028

    //验证私钥
    const val CHECK_ADDRESS = 0x2029

    //获取资产编号
    const val RANDOM_VALID_SYMID = 0x2030

    //请求失败
    const val REQUEST_ERROR = 0x2031

    //添加节点
    const val ADD_NODE = 0x2032

    //创建组
    const val CREATE_GROUP = 0x2033 //创建组

    //获取由publicKeys管理的组列表
    const val MY_GROUP = 0x2034

    //通过name获取有关组的详细信息
    const val GROUP_DETAIL = 0x2035
}