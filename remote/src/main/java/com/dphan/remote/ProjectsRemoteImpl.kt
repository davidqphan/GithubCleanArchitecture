package com.dphan.remote

import com.dphan.data.model.ProjectEntity
import com.dphan.data.repository.ProjectsRemote
import com.dphan.remote.mapper.ProjectsResponseModelMapper
import com.dphan.remote.service.GithubTrendingService
import io.reactivex.Observable
import javax.inject.Inject

class ProjectsRemoteImpl @Inject constructor(
        private val service: GithubTrendingService,
        private val mapper: ProjectsResponseModelMapper) : ProjectsRemote {
    override fun getProjects(): Observable<List<ProjectEntity>> {
        return service.searchRepositories("language:kotlin", "stars", "desc")
                .map {
                    it.items.map {
                        mapper.mapFromModel(it)
                    }
                }
    }
}