package com.example.hi5test.api

import java.io.IOException

data class NetworkException constructor(
    val errorBody: String? = null,
    var errorCode: Int = 0
) : IOException()
