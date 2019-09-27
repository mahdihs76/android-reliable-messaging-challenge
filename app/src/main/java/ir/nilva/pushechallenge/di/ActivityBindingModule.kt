package ir.nilva.pushechallenge.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.nilva.pushechallenge.view.MainActivity
import ir.nilva.pushechallenge.view.MainActivityModule

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            // activity
            MainActivityModule::class
        ]
    )
    internal abstract fun mainActivity(): MainActivity

}
