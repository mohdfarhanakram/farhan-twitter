package com.farhan.twitter.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

/**
 * Created by Mohd Farhan on 5/5/2021.
 */

class FirebaseAuthSource @Inject constructor(
     private val firebaseAuth: FirebaseAuth,
     private val firebaseFirestore: FirebaseFirestore) : IAuthSource{

    override fun register(email: String, password: String, name: String): Completable {
        return Completable.create { emitter ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnFailureListener { e -> emitter.onError(e) }
                .addOnCompleteListener { //create new user
                    val map = HashMap<String, Any>()
                    map["email"] = email
                    map["name"] = name
                    map["imageUrl"] = "default"
                    firebaseFirestore.collection("users")
                        .document(userId()).set(map)
                        .addOnFailureListener { e -> emitter.onError(e) }
                        .addOnSuccessListener { emitter.onComplete() }
                }
        }
    }

    override fun login(email: String, password: String): Completable {
        return Completable.create { emitter ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnFailureListener { e -> emitter.onError(e) }
                .addOnSuccessListener { emitter.onComplete() }
        }
    }

    override fun signOut(){
        firebaseAuth.signOut()
    }

    override fun userId(): String {
        return firebaseAuth.currentUser?.uid ?:""
    }
}