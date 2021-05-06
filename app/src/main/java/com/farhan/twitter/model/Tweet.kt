package com.farhan.twitter.model

import java.sql.Timestamp

/**
 * Created by Mohd Farhan on 5/5/2021.
 */
data class Tweet(
    var post : String,
    val userId : String,
    val name : String,
    val email : String,
    val imageUrl : String,
    val timeStamp : String = Timestamp(System.currentTimeMillis()).toString()
)
