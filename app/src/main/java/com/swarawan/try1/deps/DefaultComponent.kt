package com.swarawan.try1.deps

import com.swarawan.corelibrary.AppModule
import com.swarawan.corelibrary.CommonDeps
import com.swarawan.corelibrary.bluetooth.BluetoothModule
import com.swarawan.corelibrary.eventbus.EventBusModule
import com.swarawan.corelibrary.firebase.FirebaseModule
import com.swarawan.corelibrary.network.NetworkModule
import com.swarawan.corelibrary.sharedprefs.CorePreferencesModule
import com.swarawan.try1.RosidFoodApplication
import com.swarawan.try1.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Rio Swarawn on 8/12/18.
 */

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        EventBusModule::class,
        FirebaseModule::class,
        CorePreferencesModule::class,
        NetworkModule::class,
        BluetoothModule::class,
        LocalModule::class
))
interface DefaultComponent : CommonDeps {

    fun inject(app: RosidFoodApplication)

    fun inject(app: MainActivity)
}