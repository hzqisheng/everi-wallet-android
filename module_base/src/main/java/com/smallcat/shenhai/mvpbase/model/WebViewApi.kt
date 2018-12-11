package com.smallcat.shenhai.mvpbase.model

object WebViewApi{
    /**
     * create EVT wallet
     * @param pwd password
     */
    fun createEVTWallet(pwd :String):String = "createEVTWallet('$pwd')"

    /**
     * getNFts List
     * @param publicKeys
     */
    fun getOwnedTokens(publicKeys :String):String = "getOwnedTokens('$publicKeys')"

    /**
     * getFts List
     * @param address
     */
    fun getFungibleBalance(address :String):String = "getFungibleBalance('$address')"

    /**
     * import EVT wallet
     * @param mnemoinc
     * @param password
     */
    fun importEVTWallet(mnemoinc :String, password:String):String = "importEVTWallet('$mnemoinc','$password')"

    /**
     * EVT init
     */
    fun EVTInit():String = "javascript:EVTInit()"

    /**
     * provide privateKey
     * @param privateKey
     */
    fun needPrivateKeyResponse(privateKey :String):String = "javascript:needPrivateKeyResponse('$privateKey')"

}