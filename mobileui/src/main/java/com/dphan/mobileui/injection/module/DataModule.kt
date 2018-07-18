package com.dphan.mobileui.injection.module

import com.dphan.data.ProjectsDataRepository
import com.dphan.domain.repository.ProjectsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindDataRepository(dataRepository: ProjectsDataRepository): ProjectsRepository

}