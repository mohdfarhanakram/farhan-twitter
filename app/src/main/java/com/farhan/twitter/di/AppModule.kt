package com.farhan.twitter.di

import com.farhan.twitter.data.remote.FirebaseAuthSource
import com.farhan.twitter.data.remote.FirebaseDataSource
import com.farhan.twitter.data.remote.IAuthSource
import com.farhan.twitter.data.remote.IDataSource
import com.farhan.twitter.data.repository.IRepository
import com.farhan.twitter.data.repository.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Mohd Farhan on 5/7/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun getAuthInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideAuthSource(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): IAuthSource {
        return FirebaseAuthSource(firebaseAuth, firebaseFirestore)
    }

    @Singleton
    @Provides
    fun provideDataSource(firebaseAuth: FirebaseAuth,
                          firebaseFirestore: FirebaseFirestore): IDataSource {
        return FirebaseDataSource(firebaseAuth,firebaseFirestore)
    }

    @Singleton
    @Provides
    fun provideRepository(authSource: IAuthSource,
                          dataSource: IDataSource): IRepository {
        return Repository(authSource,dataSource)
    }

}