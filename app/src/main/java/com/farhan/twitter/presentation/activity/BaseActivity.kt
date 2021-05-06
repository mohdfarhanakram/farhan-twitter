package com.farhan.twitter.presentation.activity

import android.app.ProgressDialog
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.farhan.twitter.model.Response
import dagger.hilt.android.AndroidEntryPoint

/**
 *   Created by Mohd Farhan on 06/05/2021.
 */
@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    fun showResult(result: Response<Any>) {
        when(result) {
            is Response.Success -> {
                hideLoader()
                populateUi(result.data)
            }
            is Response.Error -> {
                showToastMessage(result.error)
                hideLoader()
            }
            is Response.Loading -> {
               showLoader()
            }
        }
    }

    open fun populateUi(result: Any){

    }

    lateinit var dialog : ProgressDialog

    fun showToastMessage(message : String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    fun showLoader(){
        val dialog = ProgressDialog(this)
        dialog.show()
    }

    fun hideLoader(){
        if(dialog.isShowing){
            dialog.dismiss()
        }
    }
}