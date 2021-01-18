package com.example.odooapitest.entity.customer


data class GetCustomerResponse(
    val jsonrpc: String,
    val result: List<Customer>
)