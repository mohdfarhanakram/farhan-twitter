package com.farhan.twitter.presentation.vm

import androidx.lifecycle.SavedStateHandle
import com.farhan.twitter.data.repository.IRepository
import com.farhan.twitter.model.Response
import com.farhan.twitter.model.Tweet
import com.farhan.twitter.model.TweetListWrapper
import com.farhan.twitter.model.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
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
class AuthViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: IRepository
): BaseVM() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    fun login(email : String, password : String){
        /*liveData.postValue(Response.Loading)
        repository.login(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<DocumentSnapshot?> {
                override fun onSubscribe(d: Disposable?) {
                    TODO("Not yet implemented")
                }

                override fun onNext(t: DocumentSnapshot?) {
                    TODO("Not yet implemented")
                }

                override fun onError(e: Throwable?) {
                    TODO("Not yet implemented")
                }

                override fun onComplete() {
                    TODO("Not yet implemented")
                }

            })*/


        liveData.postValue(Response.Loading)
        repository.login(email,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()
            .subscribe(object : Observer<User?> {
                override fun onSubscribe(d: Disposable?) {
                    disposable.add(d)
                }

                override fun onNext(user: User?) {
                    if(user!=null){
                        liveData.postValue(Response.Success(user))
                    }else{
                        liveData.postValue(Response.Error("Unable to login"))
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

    fun register(email : String, password : String, name : String){
        liveData.postValue(Response.Loading)
        repository.register(email, password,name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable?) {
                    disposable.add(d)
                }

                override fun onComplete() {
                    liveData.postValue(Response.Success("Register Successfully"))
                }

                override fun onError(e: Throwable) {
                    liveData.postValue(Response.Error(e.message ?:"Something went wrong"))
                }
            })
    }

}