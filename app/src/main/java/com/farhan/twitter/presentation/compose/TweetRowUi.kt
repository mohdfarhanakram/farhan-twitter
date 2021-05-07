package com.farhan.twitter.presentation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farhan.twitter.model.Tweet

/**
 * Created by Mohd Farhan on 5/7/2021.
 */
@Composable
fun TweetRowUi(tweet: Tweet) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = tweet.name,
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 18.sp,
            )

            Text(
                text = tweet.post,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}