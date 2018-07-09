package com.dphan.data

import com.dphan.data.mapper.ProjectMapper
import com.dphan.data.repository.ProjectsCache
import com.dphan.data.store.ProjectsDataStoreFactory
import com.dphan.domain.model.Project
import com.dphan.domain.repository.ProjectsRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class ProjectsDataRepository @Inject constructor(
        private val projectMapper: ProjectMapper,
        private val projectsCache: ProjectsCache,
        private val projectsDataStoreFactory: ProjectsDataStoreFactory)
    : ProjectsRepository {

    override fun getProjects(): Observable<List<Project>> {
        return Observable.zip(projectsCache.areProjectsCached().toObservable(),
                projectsCache.isProjectsCacheExpired().toObservable(),
                BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> { areCached, isExpired ->
                    Pair(areCached, isExpired)
                })
                .flatMap {
                    projectsDataStoreFactory.getDataStore(it.first, it.second).getProjects()
                }
                .flatMap { projects ->
                    projectsDataStoreFactory.getCacheDataStore()
                            .saveProjects(projects)
                            .andThen(Observable.just(projects))
                }
                .map {
                    it.map {
                        projectMapper.mapFromEntity(it)
                    }
                }
    }

    override fun bookmarkProject(projectId: String): Completable {
        return projectsDataStoreFactory.getCacheDataStore().setProjectAsBookmarked(projectId)
    }

    override fun unbookmarkProject(projectId: String): Completable {
        return projectsDataStoreFactory.getCacheDataStore().setProjectAsNotBookmarked(projectId)
    }

    override fun getBookmarkedProjects(): Observable<List<Project>> {
        return projectsDataStoreFactory.getCacheDataStore().getBookmarkedProjects()
                .map {
                    it.map { projectMapper.mapFromEntity(it) }
                }
    }

}