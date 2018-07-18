package com.dphan.mobileui.injection.module

import com.dphan.domain.executor.PostExecutionThread
import com.dphan.mobileui.BrowseActivity
import com.dphan.mobileui.UiThread
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesBrowseActivity(): BrowseActivity

}