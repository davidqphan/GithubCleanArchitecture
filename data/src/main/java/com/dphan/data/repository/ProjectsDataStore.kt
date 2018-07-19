package com.dphan.data.repository

import com.dphan.data.model.ProjectEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface ProjectsDataStore {

    fun getProjects(): Flowable<List<ProjectEntity>>

    fun saveProjects(projects: List<ProjectEntity>): Completable

    fun clearProjects(): Completable

    fun getBookmarkedProjects(): Flowable<List<ProjectEntity>>

    fun setProjectAsBookmarked(projectId: String): Completable

    fun setProjectAsNotBookmarked(projectId: String): Completable

}