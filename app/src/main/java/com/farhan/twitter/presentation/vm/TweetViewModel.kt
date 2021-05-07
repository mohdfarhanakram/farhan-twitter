package com.farhan.twitter.presentation.vm

import androidx.lifecycle.SavedStateHandle
import com.farhan.twitter.data.repository.IRepository
import com.farhan.twitter.model.Response
import com.farhan.twitter.model.Tweet
import com.farhan.twitter.model.TweetListWrapper
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.ArrayList
import javax.inject.Inject

/**
 * Created by Mohd Farhan on 5/6/2021.
 */
@HiltViewModel
class TweetViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: IRepository
): BaseVM(){

    private val disposable: CompositeDisposable = CompositeDisposable()

    private val tweetList = ArrayList<Tweet>()
    private var counter = 0

    fun postTweet(userId: String, tweet: Tweet) {
        liveData.postValue(Response.Loading)
        repository.tweet(userId, tweet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable?) {
                    disposable.add(d)
                }

                override fun onComplete() {
                    liveData.postValue(Response.Success("Your tweet is post now"))
                }

                override fun onError(e: Throwable) {
                    liveData.postValue(Response.Error(e.message ?:"Something went wrong"))
                }
            })

    }

    fun getTweets(){
        liveData.postValue(Response.Loading)
        repository.tweets()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()
            .subscribe(object : Observer<QuerySnapshot?> {
                override fun onSubscribe(d: Disposable?) {
                    disposable.add(d)
                }

                override fun onNext(queryDocumentSnapshots: QuerySnapshot?) {
                    if (queryDocumentSnapshots != null) {
                        for (dc in queryDocumentSnapshots.documentChanges) {
                            if (dc.type == DocumentChange.Type.ADDED) {
                                if (counter == 0) {
                                    val tweet: Tweet = dc.document.toObject(Tweet::class.java)
                                    tweetList.add(tweet)
                                } else {
                                    val tweet: Tweet = dc.document.toObject(Tweet::class.java)
                                    liveData.postValue(Response.Success(tweet))
                                }
                            }
                        }
                        if (counter == 0) {
                            val tweetListWrapper = TweetListWrapper(tweetList)
                            liveData.postValue(Response.Success(tweetListWrapper))
                            counter++
                        }
                    }
                }

                override fun onError(e: Throwable?) {
                    if (e != null) {
                        liveData.postValue(Response.Error(e.message ?:""))
                    }else{
                        liveData.postValue(Response.Error("Something went wrong"))
                    }
                }
                override fun onComplete() {}
            })
    }

}