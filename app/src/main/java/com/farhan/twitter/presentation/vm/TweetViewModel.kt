package com.farhan.twitter.presentation.vm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.farhan.twitter.data.repository.IRepository
import com.farhan.twitter.model.Response
import com.farhan.twitter.model.Tweet
import com.farhan.twitter.model.TweetListWrapper
import com.farhan.twitter.model.User
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
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

    val tweetListData: MutableState<TweetListWrapper> = mutableStateOf(
        TweetListWrapper(ArrayList())
    )

    init {
        getTweets()
    }

    fun postTweet(userId: String, tweet: Tweet) {
        //liveData.postValue(Response.Loading)
        repository.tweet(userId, tweet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable?) {
                    disposable.add(d)
                }

                override fun onComplete() {
                    liveData.postValue(Response.Success(tweet))
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
            .subscribe(object : Observer<TweetListWrapper?> {
                override fun onSubscribe(d: Disposable?) {
                    disposable.add(d)
                }

                override fun onNext(tweetListWrapper: TweetListWrapper?) {
                    if (tweetListWrapper != null) {
                        liveData.postValue(Response.Success(tweetListWrapper))
                        tweetListData.value = tweetListWrapper
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

    private fun getTweetObj(map : Map<String, Any>) : Tweet{
        return Tweet(map["post"].toString(),map["userId"].toString(),map["name"].toString(),map["email"].toString(),map["imageUrl"].toString(),map["timeStamp"].toString())
    }

}