package com.farhan.twitter.data.repository

import com.farhan.twitter.data.remote.FirebaseAuthSource
import com.farhan.twitter.data.remote.FirebaseDataSource
import com.farhan.twitter.model.Tweet
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

/**
 * Created by Mohd Farhan on 5/5/2021.
 */
class Repository @Inject constructor(
    private val firebaseAuthSource : FirebaseAuthSource,
    private val firebaseDataSource: FirebaseDataSource
) : IRepository{

    override fun register(email: String, password: String, name: String): Completable {
        return firebaseAuthSource.register(email,password,name)
    }

    override fun login(email: String, password: String): Completable {
        return firebaseAuthSource.login(email,password)
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

    override fun tweets(): Flowable<QuerySnapshot> {
        return firebaseDataSource.tweets()
    }
}