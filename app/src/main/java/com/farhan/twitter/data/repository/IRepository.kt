package com.farhan.twitter.data.repository

import com.farhan.twitter.model.Tweet
import com.farhan.twitter.model.TweetListWrapper
import com.farhan.twitter.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

/**
 * Created by Mohd Farhan on 5/5/2021.
 */
interface IRepository {
    fun register(email : String, password : String, name : String) : Completable
    fun login(email : String, password : String) : Flowable<User>
    fun signOut()
    fun userId() : String
    fun tweet(userId : String, tweet: Tweet) : Completable
    fun tweets() : Flowable<TweetListWrapper>
}