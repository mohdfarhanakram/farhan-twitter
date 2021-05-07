package com.farhan.twitter.presentation.activity

import android.app.ProgressDialog
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.farhan.twitter.model.Response
import dagger.hilt.android.AndroidEntryPoint

/**
 *   Created by Mohd Farhan on 06/05/2021.
 */
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

    var dialog : ProgressDialog? = null

    fun showToastMessage(message : String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    private fun showLoader(){
        hideLoader()
        dialog = ProgressDialog(this)
        dialog!!.show()
    }

    private fun hideLoader(){
        if(dialog!=null && dialog?.isShowing == true){
            dialog?.dismiss()
        }
    }
}