package com.farhan.twitter.data.remote

import com.farhan.twitter.model.Response
import com.farhan.twitter.model.Tweet
import com.farhan.twitter.model.TweetListWrapper
import com.farhan.twitter.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.functions.Cancellable
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
            val tweetReference = firebaseFirestore.collection("tweets").document(userId+tweet.timeStamp)
            requestBatch[tweetReference] = tweet
            requestBatch.commit()
                .addOnFailureListener { e -> emitter.onError(e) }
                .addOnSuccessListener { emitter.onComplete() }
        }
    }

    override fun tweets(): Flowable<TweetListWrapper> {
        val tweetList = ArrayList<Tweet>()
        var counter = 0
        return Flowable.create({ emitter ->
            val reference =
                firebaseFirestore.collection("tweets")
            val registration = reference.addSnapshotListener { queryDocumentSnapshots, e ->
                if (e != null) {
                    emitter.onError(e)
                }
                if (queryDocumentSnapshots != null) {

                    for (dc in queryDocumentSnapshots.documentChanges) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            if (counter == 0) {
                                val map : Map<String,Any> = dc.document.data?.toMap() as Map<String, Any>
                                val tweet: Tweet = getTweetObj(map)
                                tweetList.add(tweet)
                            } else { // its only for single tweet
                                val map : Map<String,Any> = dc.document.data?.toMap() as Map<String, Any>
                                val tweet: Tweet = getTweetObj(map)

                                val tweetListWrapper = TweetListWrapper(ArrayList(),true)
                                tweetListWrapper.tweetList.addAll(tweetList)
                                tweetListWrapper.tweetList.add(tweet)
                                emitter.onNext(tweetListWrapper)
                                tweetList.add(tweet)
                            }
                        }
                    }

                    if (counter == 0) {  // its for loading all tweets
                        val tweetListWrapper = TweetListWrapper(tweetList)
                        emitter.onNext(tweetListWrapper)
                        counter++
                    }

                }
            }
            emitter.setCancellable { registration.remove() }
        }, BackpressureStrategy.BUFFER)
    }

    override fun getUserInfo(uid: String): Flowable<User> {
        return Flowable.create({ emitter ->
            val reference = firebaseFirestore.collection("users").document(uid)
            val registration = reference.addSnapshotListener { documentSnapshot, e ->
                if (e != null) {
                    emitter.onError(e)
                }
                if (documentSnapshot != null) {
                    val map : Map<String,Any> =
                        documentSnapshot.data?.toMap() as Map<String, Any>
                    val user = User(map["name"].toString(),map["email"].toString(),map["imageUrl"].toString(),uid)
                    emitter.onNext(user)
                }
            }
            emitter.setCancellable { registration.remove() }
        }, BackpressureStrategy.BUFFER)
    }

    private fun getTweetObj(map : Map<String, Any>) : Tweet{
        return Tweet(map["post"].toString(),map["userId"].toString(),map["name"].toString(),map["email"].toString(),map["imageUrl"].toString(),map["timeStamp"].toString())
    }
}