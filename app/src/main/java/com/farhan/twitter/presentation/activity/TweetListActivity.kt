package com.farhan.twitter.presentation.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.farhan.twitter.model.Tweet
import com.farhan.twitter.model.TweetListWrapper
import com.farhan.twitter.model.User
import com.farhan.twitter.presentation.compose.TwitterListScreen
import com.farhan.twitter.presentation.vm.AuthViewModel
import com.farhan.twitter.presentation.vm.TweetViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

/**
 *   Created by Mohd Farhan on 06/05/2021.
 */
@AndroidEntryPoint
class TweetListActivity : BaseActivity() {

    val tweetViewModel : TweetViewModel by viewModels()

    lateinit var user : User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = intent.getParcelableExtra("userInfo")!!

        setContent {
            TwitterListScreen(tweetViewModel.tweetListData.value){
                val tweet = Tweet(it,user.uId,user.name,user.email,user.imageUrl)
                tweetViewModel.postTweet(user.uId,tweet)
            }
        }

        tweetViewModel.liveData.observe(this){
            showResult(it)
        }

    }

    override fun populateUi(result: Any) {
        when(result){
            is Tweet -> {
                //showToastMessage("Tweet successfully")
                return
            }
            is TweetListWrapper->{
                //TODO don't need to handle it
            }
            is String -> {
                showToastMessage(result)
                return
            }
        }
    }

}