package com.qs.modulemain.bean

data class TransactionResult(
        val pending: Boolean,
        val transactionId: String?,
        val successful: Boolean
)