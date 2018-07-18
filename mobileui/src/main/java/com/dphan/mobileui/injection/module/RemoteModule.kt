package com.dphan.mobileui.injection.module

import com.dphan.data.repository.ProjectsRemote
import com.dphan.mobileui.BuildConfig
import com.dphan.remote.ProjectsRemoteImpl
import com.dphan.remote.service.GithubTrendingService
import com.dphan.remote.service.GithubTrendingServiceFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideGithubService(): GithubTrendingService {
            return GithubTrendingServiceFactory.makeGithubTrendingService(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindProjectsRemote(projectsRemoteImpl: ProjectsRemoteImpl): ProjectsRemote

}