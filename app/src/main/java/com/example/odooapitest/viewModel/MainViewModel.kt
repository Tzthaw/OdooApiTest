package com.example.odooapitest.viewModel

import android.content.Context
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.odooapitest.data.OdooRepository
import com.example.odooapitest.entity.customer.Customer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel @ViewModelInject internal constructor(
    private val repository: OdooRepository
) :ViewModel() {

    private val _customerListLD = MutableLiveData<List<Customer>>()
    val customerListLD:LiveData<List<Customer>> = _customerListLD

    private val _errorLD = MutableLiveData<String>()
    val errorLD:LiveData<String> = _errorLD

    fun callCustomerList(){
        viewModelScope.launch {
            try{
                repository.callApiCustomer().collect {
                    withContext(Dispatchers.Main){
                        if(it!=null){
                            _customerListLD.postValue(it)
                        }
                    }
                }
            }catch (t:Throwable){
                val th = t
                withContext(Dispatchers.Main){
                    _errorLD.postValue(th.message)
                }
            }

        }
    }
}