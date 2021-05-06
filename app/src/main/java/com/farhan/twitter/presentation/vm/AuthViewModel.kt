package com.farhan.twitter.presentation.vm

import com.farhan.twitter.data.repository.Repository
import com.farhan.twitter.model.Response
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Mohd Farhan on 5/6/2021.
 */
class AuthViewModel @Inject constructor(
    private val repository: Repository
): BaseVM() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    fun login(email : String, password : String){
        liveData.postValue(Response.Loading)
        repository.login(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable?) {
                    disposable.add(d)
                }

                override fun onComplete() {
                    liveData.postValue(Response.Success("Login Successfully"))
                }

                override fun onError(e: Throwable) {
                    liveData.postValue(Response.Error(e.message ?:"Something went wrong"))
                }
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