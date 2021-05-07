package com.farhan.twitter.presentation.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.MutableLiveData
import com.farhan.twitter.model.Response
import com.farhan.twitter.model.Tweet
import com.farhan.twitter.model.TweetListWrapper

/**
 * Created by Mohd Farhan on 5/7/2021.
 */

@Composable
fun TwitterListUi(modifier: Modifier = Modifier, tweetListWrapper: TweetListWrapper){

    val tweets = tweetListWrapper.tweetList

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        LazyColumn {
            items(tweets){ tweet ->
                run {
                    TweetRowUi(tweet)
                }
                Divider()
            }
        }
    }


}

