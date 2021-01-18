package com.example.odooapitest.entity

data class CustomerJson(
    val jsonrpc: String,
    val method: String,
    val params: ParamsX
)