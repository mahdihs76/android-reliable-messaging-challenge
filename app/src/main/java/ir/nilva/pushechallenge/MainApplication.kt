package ir.nilva.pushechallenge

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import ir.nilva.pushechallenge.di.DaggerAppComponent
import timber.log.Timber

class MainApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}