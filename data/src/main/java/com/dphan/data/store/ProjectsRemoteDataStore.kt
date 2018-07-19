package com.dphan.data.store

import com.dphan.data.model.ProjectEntity
import com.dphan.data.repository.ProjectsDataStore
import com.dphan.data.repository.ProjectsRemote
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

open class ProjectsRemoteDataStore @Inject constructor(
        private val projectsRemote: ProjectsRemote)
    : ProjectsDataStore {

    override fun getProjects(): Flowable<List<ProjectEntity>> {
        return projectsRemote.getProjects()
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        throw UnsupportedOperationException("Saving projects isn't supported here...")
    }

    override fun clearProjects(): Completable {
        throw UnsupportedOperationException("Clearing projects isn't supported here...")
    }

    override fun getBookmarkedProjects(): Flowable<List<ProjectEntity>> {
        throw UnsupportedOperationException("Getting bookmarked projects isn't supported here...")
    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        throw UnsupportedOperationException("Setting bookmarks isn't supported here...")
    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        throw UnsupportedOperationException("Setting bookmarks isn't supported here...")
    }

}