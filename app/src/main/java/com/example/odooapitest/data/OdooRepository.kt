package com.example.odooapitest.data

import android.content.Context
import android.net.Uri
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.example.odooapitest.api.PythonApiService
import com.example.odooapitest.api.executeOrThrow
import com.example.odooapitest.entity.CustomerJson
import com.example.odooapitest.entity.Field
import com.example.odooapitest.entity.ParamsX
import com.example.odooapitest.entity.customer.Customer
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.net.URLEncoder
import javax.inject.Inject

class OdooRepository @Inject constructor(
        private val api: PythonApiService,
        private val context: Context
) {
    suspend fun callApiCustomer(): Flow<List<Customer>> {
        val isCompany = listOf(listOf(listOf("is_company", Uri.encode("=","@#&=*+-_.,:!?()/~'%"), "True")))
//        val fields = "{\"fields\": [\"name\", \"country_id\", \"comment\",\"phone\",\"mobile\",\"email\"],\n" +
//                "                        \"limit\": 5}"

        val customer = CustomerJson(
            jsonrpc =  "2.0",
            method = "call",
            params = ParamsX(
                args = listOf(
                    "ServiceHub_UAT",
                     2,
                    "123456",
                    "res.partner",
                    "search_read",
                    isCompany,
                    Field(
                        listOf("name", "country_id", "comment","phone","mobile","email"),
                        5
                    )),
                method = "execute_kw",
                service = "object"
            )
        )

        val customerJson = Gson().toJson(customer)
        val dataRequest= customerJson.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        return withContext(Dispatchers.IO){
            val result = api.getUserData(dataRequest).executeOrThrow()
            flowOf(result.result)
                .flowOn(Dispatchers.IO)
        }
    }

    fun parse(name: String) : Any? {
        val cls = Parser::class.java
        return cls.getResourceAsStream(name)?.let { inputStream ->
            return Parser.default().parse(inputStream)
        }
    }
}