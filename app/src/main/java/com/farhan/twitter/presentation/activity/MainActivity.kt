package com.farhan.twitter.presentation.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import com.farhan.twitter.presentation.compose.LoginUI

/**
 *   Created by Mohd Farhan on 06/05/2021.
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginUI {

            }
        }
    }
}