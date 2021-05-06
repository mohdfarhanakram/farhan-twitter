package com.farhan.twitter.data.remote

import com.farhan.twitter.model.Tweet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import io.reactivex.rxjava3.core.*
import javax.inject.Inject

/**
 * Created by Mohd Farhan on 5/5/2021.
 */
class FirebaseDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore)
: IDataSource {

    override fun tweet(userId: String, tweet: Tweet): Completable {
        return Completable.create { emitter ->
            val requestBatch = firebaseFirestore.batch()
            val tweetReference = firebaseFirestore.collection("tweets").document(userId)
            requestBatch[tweetReference] = tweet
            requestBatch.commit()
                .addOnFailureListener { e -> emitter.onError(e) }
                .addOnSuccessListener { emitter.onComplete() }
        }
    }

    override fun tweets(): Flowable<QuerySnapshot> {
        return Flowable.create({ emitter ->
            val reference =
                firebaseFirestore.collection("tweets")
            val registration = reference.addSnapshotListener { queryDocumentSnapshots, e ->
                if (e != null) {
                    emitter.onError(e)
                }
                if (queryDocumentSnapshots != null) {
                    emitter.onNext(queryDocumentSnapshots)
                }
            }
            emitter.setCancellable { registration.remove() }
        }, BackpressureStrategy.BUFFER)
    }
}