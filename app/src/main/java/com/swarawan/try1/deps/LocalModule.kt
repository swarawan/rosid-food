package com.swarawan.try1.deps

import android.content.Context
import com.swarawan.corelibrary.sharedprefs.CorePreferences
import com.swarawan.try1.common.AppPreference
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Rio Swarawn on 8/12/18.
 */
@Module
class LocalModule {

    @Provides
    @Singleton
    fun providesAppPreferences(corePreferences: CorePreferences) =
            AppPreference(corePreferences)
}