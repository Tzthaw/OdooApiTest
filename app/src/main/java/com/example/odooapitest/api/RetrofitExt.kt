package com.example.odooapitest.api

import com.example.hi5test.api.ApiWrongException
import com.example.hi5test.api.NetworkException
import retrofit2.Call
import retrofit2.Response

/**
 * Created by Vincent on 2019-10-21
 */

fun <T> Call<T>.executeOrThrow(): T {
  val response = this.execute()
  return response.getBodyOrThrowNetworkException()
}

fun <T> Response<T>.getBodyOrThrowNetworkException(): T {
  if (this.isSuccessful.not()) {
    val errorString = this.errorBody()!!.byteStream().bufferedReader().use { it.readText() }
    throw NetworkException(errorString, this.code())
  }

  if (this.code() == 206) {
    throw ApiWrongException()
  }

  val body = this.body() ?: throw NetworkException()
  return body
}

