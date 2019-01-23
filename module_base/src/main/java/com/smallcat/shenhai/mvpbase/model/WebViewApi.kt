package com.smallcat.shenhai.mvpbase.model


object WebViewApi {
    /**
     * create EVT wallet
     * @param pwd password
     */
    fun createEVTWallet(pwd: String): String = "createEVTWallet('$pwd')"


    fun privateToPublic(privateKey: String): String = "privateToPublic('$privateKey')"

    /**
     * getNFts List
     * @param publicKeys
     */
    fun getOwnedTokens(publicKeys: String): String = "getOwnedTokens('$publicKeys')"

    /**
     * getFts List
     * @param address
     */
    fun getFungibleBalance(address: String): String = "getFungibleBalance('$address')"

    /**
     * import EVT wallet
     * @param mnemoinc
     * @param password
     */
    fun importEVTWallet(mnemoinc: String, password: String): String = "importEVTWallet('$mnemoinc','$password')"

    /**
     * EVT init
     */
    fun EVTInit(): String = "javascript:EVTInit()"

    /**
     * provide privateKey
     * @param privateKey
     */
    fun needPrivateKeyResponse(privateKey: String): String = "javascript:needPrivateKeyResponse('$privateKey')"

    /**
     * hava fungibles list
     * @param publicKeys public key
     */
    fun getCreatedFungibles(publicKeys: String) = "javascript:getCreatedFungibles('$publicKeys')"

    /** pushTranscation **/
    fun pushTransaction(action: String, json: String) = "javascript:pushTransaction('$action','$json')"

    /** 选择代币列表 **/
    fun getEVTFungiblesList(publicKeys: String) = "javascript:getEVTFungiblesList('$publicKeys')"

    /** 获取域列表 **/
    fun getEVTDomainsList(publicKeys: String) = "javascript:getEVTDomainsList('$publicKeys')"

    /** 二维码相关 **/
    fun getEVTLinkQrImage(code: String, json: String, auto: String) = "javascript:getEVTLinkQrImage('$code','$json','$auto')"

    /** 停止二维码生成 **/
    fun stopEVTLinkQrImageReload() = "javascript:stopEVTLinkQrImageReload()"

    /** 获取linkId **/
    fun getUniqueLinkId() = "javascript:getUniqueLinkId()"

    /** 获取交易结果 **/
    fun getStatusOfEvtLink(link: String) = "javascript:getStatusOfEvtLink('$link')"
    //fun getStatusOfEvtLink() = "javascript:getStatusOfEvtLink()"

    /** pushTranscation **/
    fun pushTransaction(action: String, json: String, config: String) = "javascript:pushTransaction('$action','$json','$config')"

    //解析二维码
    fun parseEvtLink(code: String, options: String) = "javascript:parseEvtLink('$code','$options')"

    //代币
    fun getEVTFungibleBalanceList(publicKeys: String) = "javascript:getEVTFungibleBalanceList('$publicKeys')"

    /** 交易记录 **/
    fun getActions(json: String) = "javascript:getActions('$json')"

    /** 交易记录 **/
    fun getFungibleActionsByAddress(id: Int, publicKeys: String, current: Int, take: Int) = "javascript:getFungibleActionsByAddress('$id','$publicKeys','$current','$take')"

    /** 解析地址 **/
    fun parseEvtLink(link: String) = "javascript:parseEvtLink('$link')"

    /** 获取手续费 **/
    fun getEstimatedChargeForTransaction(code: String, json: String, json1: String) = "javascript:getEstimatedChargeForTransaction('$code','$json','$json1')"

    /** 获取币的详细信息 **/
    fun getFungibleSymbolDetail(sybid: Long) = "javascript:getFungibleSymbolDetail($sybid)"

    /** 上传图片 **/
    fun pushTransaction(s1: String, s2: String, s3: String, s4: String, int: Int) = "javascript:pushTransaction('$s1','$s2','$s3','$s4',$int)"

    //验证助记词
    fun validateMnemonic(s: String) = "javascript:validateMnemonic('$s')"

    //验证私钥
    fun isValidPrivateKey(s: String) = "javascript:isValidPrivateKey('$s')"

    //切换节点
    fun changeNetwork(s: String) = "javascript:changeNetwork('$s')"

    //获取交易信息
    fun getTransactionDetailById(s: String) = "javascript:getTransactionDetailById('$s')"

    //检查更新
    fun getAPPVersion() = "javascript:getAPPVersion()"

}