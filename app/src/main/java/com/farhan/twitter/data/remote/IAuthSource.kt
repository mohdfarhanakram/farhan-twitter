package com.farhan.twitter.data.remote

import io.reactivex.rxjava3.core.Completable

/**
 * Created by Mohd Farhan on 5/5/2021.
 */
interface IAuthSource {
    fun register(email : String, password : String, name : String) : Completable
    fun login(email : String, password : String) : Completable
    fun signOut()
    fun userId() : String
}