package com.farhan.twitter.data.repository

import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

/**
 * Created by Mohd Farhan on 5/5/2021.
 */
interface IRepository {
    fun register(email : String, password : String, name : String) : Completable
    fun login(email : String, password : String) : Completable
    fun signOut()
    fun userId() : String
    fun tweet(userId : String, message : String) : Completable
    fun tweets() : Flowable<QuerySnapshot>
}