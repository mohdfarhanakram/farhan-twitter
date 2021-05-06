package com.farhan.twitter.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.farhan.twitter.model.User
import com.farhan.twitter.presentation.compose.LoginUI
import com.farhan.twitter.presentation.compose.RegisterUI
import com.farhan.twitter.presentation.vm.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Mohd Farhan on 5/6/2021.
 */
@AndroidEntryPoint
class LoginActivity : BaseActivity(){

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginUI(
                onLogin = {
                        email, password ->
                            run {
                                authViewModel.login(email,password)
                            }
                },
                register = {
                    val intent = Intent(this,RegisterActivity::class.java)
                    startActivity(intent)
                }
            )
        }

        authViewModel.liveData.observe(this){
            showResult(it)
        }
    }

    override fun populateUi(result: Any) {
        when(result){
            is User -> {
                return
            }
            is String -> {
                showToastMessage(result)
                return
            }
        }
    }
}