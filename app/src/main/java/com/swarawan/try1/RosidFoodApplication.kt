package com.swarawan.try1

import com.swarawan.corelibrary.AppModule
import com.swarawan.corelibrary.CommonDeps
import com.swarawan.corelibrary.CommonDepsProvider
import com.swarawan.try1.deps.DaggerDefaultComponent
import com.swarawan.try1.deps.DefaultComponent

/**
 * Created by Rio Swarawan on 8/23/18.
 */
class RosidFoodApplication : BaseApp(), CommonDepsProvider {

    lateinit var defaultComponent: DefaultComponent

    override fun initApplication() {
        defaultComponent = DaggerDefaultComponent.builder()
                .appModule(AppModule(applicationContext))
                .build()
        defaultComponent.inject(this@RosidFoodApplication)
    }

    override fun getCommonDeps(): CommonDeps = defaultComponent
}