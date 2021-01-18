package com.example.odooapitest.entity.customer

data class Customer(
    val comment: Boolean,
    val country_id: Any,
    val email: String?,
    val id: Int,
    val mobile: String?,
    val name: String,
    val phone: String?
)