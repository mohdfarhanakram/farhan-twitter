package com.farhan.twitter.data.remote

import io.reactivex.rxjava3.core.Completable

/**
 * Created by Mohd Farhan on 5/5/2021.
 */
class FirebaseAuthSource : IAuthSource{

    override fun register(email: String, password: String, name: String): Completable {
        TODO("Not yet implemented")
    }

    override fun login(email: String, password: String): Completable {
        TODO("Not yet implemented")
    }

    override fun signOut(){
        TODO("Not yet implemented")
    }

    override fun userId(): String {
        TODO("Not yet implemented")
    }
}