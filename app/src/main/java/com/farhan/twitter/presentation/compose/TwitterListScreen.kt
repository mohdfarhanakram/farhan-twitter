package com.farhan.twitter.presentation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.farhan.twitter.model.TweetListWrapper

/**
 * Created by Mohd Farhan on 5/7/2021.
 */

@Composable
fun TwitterListScreen (tweetListWrapper: TweetListWrapper,onSendMessage : (message:String)->Unit){

    Column(Modifier.fillMaxSize()) {

        TwitterListUi(
            modifier = Modifier.weight(1f),
            tweetListWrapper
        )
        TweetInput(onSendMessage)
    }
}