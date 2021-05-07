package com.farhan.twitter.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Mohd Farhan on 5/5/2021.
 */
@Parcelize
data class User(
    val name : String,
    val email : String,
    val imageUrl : String,
    val uId : String
) : Parcelable
