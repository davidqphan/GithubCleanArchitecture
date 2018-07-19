package com.dphan.data.repository

import com.dphan.data.model.ProjectEntity
import io.reactivex.Flowable

interface ProjectsRemote {

    fun getProjects(): Flowable<List<ProjectEntity>>

}