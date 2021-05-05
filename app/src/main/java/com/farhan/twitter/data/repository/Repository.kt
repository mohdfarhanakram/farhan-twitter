package com.farhan.twitter.data.repository

import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

/**
 * Created by Mohd Farhan on 5/5/2021.
 */
class Repository : IRepository{

    override fun register(email: String, password: String, name: String): Completable {
        TODO("Not yet implemented")
    }

    override fun login(email: String, password: String): Completable {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }

    override fun userId(): String {
        TODO("Not yet implemented")
    }

    override fun tweet(userId: String, message: String): Completable {
        TODO("Not yet implemented")
    }

    override fun tweets(): Flowable<QuerySnapshot> {
        TODO("Not yet implemented")
    }
}