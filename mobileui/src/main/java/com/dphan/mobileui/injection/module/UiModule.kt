package com.dphan.mobileui.injection.module

import com.dphan.domain.executor.PostExecutionThread
import com.dphan.mobileui.UiThread
import com.dphan.mobileui.bookmarked.BookmarkedActivity
import com.dphan.mobileui.browse.BrowseActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesBrowseActivity(): BrowseActivity

    @ContributesAndroidInjector
    abstract fun contributesBookmarkedActivity(): BookmarkedActivity
}