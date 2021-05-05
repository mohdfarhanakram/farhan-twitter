package com.farhan.twitter.data.remote

import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

/**
 * Created by Mohd Farhan on 5/5/2021.
 */
class FirebaseDataSource : IDataSource {

    override fun tweet(userId: String, message: String): Completable {
        TODO("Not yet implemented")
    }

    override fun tweets(): Flowable<QuerySnapshot> {
        TODO("Not yet implemented")
    }
}