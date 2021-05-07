package com.farhan.twitter.data.remote

import com.farhan.twitter.model.Tweet
import com.farhan.twitter.model.User
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

/**
 * Created by Mohd Farhan on 5/5/2021.
 */
interface IDataSource {
    fun tweet(userId : String, tweet : Tweet) : Completable
    fun tweets() : Flowable<QuerySnapshot>
    fun getUserInfo(uid: String): Flowable<User>
}