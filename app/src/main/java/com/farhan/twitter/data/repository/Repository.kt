package com.farhan.twitter.data.repository

import com.farhan.twitter.data.remote.IAuthSource
import com.farhan.twitter.data.remote.IDataSource
import com.farhan.twitter.model.Tweet
import com.farhan.twitter.model.TweetListWrapper
import com.farhan.twitter.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

/**
 * Created by Mohd Farhan on 5/5/2021.
 */
class Repository @Inject constructor(
    private val firebaseAuthSource : IAuthSource,
    private val firebaseDataSource: IDataSource
) : IRepository{

    override fun register(email: String, password: String, name: String): Completable {
        return firebaseAuthSource.register(email,password,name)
    }

    override fun login(email: String, password: String):Flowable<User> {
        return firebaseAuthSource.login(email,password)
            .flatMap {
                return@flatMap firebaseDataSource.getUserInfo(it)
            }
    }

    override fun signOut() {
        firebaseAuthSource.signOut()
    }

    override fun userId(): String {
        return firebaseAuthSource.userId()
    }

    override fun tweet(userId: String, tweet: Tweet): Completable {
        return firebaseDataSource.tweet(userId,tweet)
    }

    override fun tweets(): Flowable<TweetListWrapper> {
        return firebaseDataSource.tweets()
    }

}