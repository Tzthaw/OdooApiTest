package com.example.odooapitest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.odooapitest.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import oogbox.api.odoo.OdooClient
import oogbox.api.odoo.client.helper.data.OdooResult
import oogbox.api.odoo.client.helper.utils.ODomain
import oogbox.api.odoo.client.helper.utils.OdooFields
import oogbox.api.odoo.client.listeners.IOdooResponse

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel:MainViewModel by viewModels()
    private var email:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.customerListLD.observe(this,{ customerList ->
            for(customer in customerList){
                email += customer.email
                txtCustomerList.text =  email
            }
        })
        mainViewModel.errorLD.observe(this,{
            Toast.makeText(this,it,Toast.LENGTH_LONG).show()
        })
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.callCustomerList()
    }

}