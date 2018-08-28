package com.swarawan.try1.deps

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Rio Swarawan on 8/28/18.
 */
@Module
class GoogleSignInModule {

    @Provides
    @Singleton
    fun provideGoogleAccount(context: Context): GoogleSignInAccount? =
            GoogleSignIn.getLastSignedInAccount(context)

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth =
            FirebaseAuth.getInstance()
}