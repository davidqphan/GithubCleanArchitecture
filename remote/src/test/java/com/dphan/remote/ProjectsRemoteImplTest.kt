package com.dphan.remote

import com.dphan.data.model.ProjectEntity
import com.dphan.remote.mapper.ProjectsResponseModelMapper
import com.dphan.remote.model.ProjectModel
import com.dphan.remote.model.ProjectsResponseModel
import com.dphan.remote.service.GithubTrendingService
import com.dphan.remote.test.factory.ProjectDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProjectsRemoteImplTest {

    private val mapper = mock<ProjectsResponseModelMapper>()
    private val service = mock<GithubTrendingService>()
    private val remote = ProjectsRemoteImpl(service, mapper)

    @Test
    fun getProjectsCompletes() {
        stubGithubTrendingServiceSearchRepositories(Observable.just(ProjectDataFactory.makeProjectsResponse()))
        stubProjectsResponseModelMapperMapFromModel(any(), ProjectDataFactory.makeProjectEntity())

        val observer = remote.getProjects().test()
        observer.assertComplete()
    }

    @Test
    fun getProjectsCallsServer() {
        stubGithubTrendingServiceSearchRepositories(Observable.just(ProjectDataFactory.makeProjectsResponse()))
        stubProjectsResponseModelMapperMapFromModel(any(), ProjectDataFactory.makeProjectEntity())

        remote.getProjects().test()
        verify(service).searchRepositories(any(), any(), any())
    }

    @Test
    fun getProjectsReturnsData() {
        val response = ProjectDataFactory.makeProjectsResponse()
        stubGithubTrendingServiceSearchRepositories(Observable.just(response))

        val entities = mutableListOf<ProjectEntity>()

        response.items.forEach {
            val entity = ProjectDataFactory.makeProjectEntity()
            entities.add(entity)
            stubProjectsResponseModelMapperMapFromModel(it, entity)
        }

        val observer = remote.getProjects().test()
        observer.assertValue(entities)
    }

    @Test fun getProjectsCallsServerWithCorrectParameters() {
        stubGithubTrendingServiceSearchRepositories(Observable.just(ProjectDataFactory.makeProjectsResponse()))
        stubProjectsResponseModelMapperMapFromModel(any(), ProjectDataFactory.makeProjectEntity())

        remote.getProjects().test()
        verify(service).searchRepositories("language:kotlin", "stars", "desc")
    }

    private fun stubGithubTrendingServiceSearchRepositories(observable: Observable<ProjectsResponseModel>) {
        whenever(service.searchRepositories(any(), any(), any()))
                .thenReturn(observable)
    }

    private fun stubProjectsResponseModelMapperMapFromModel(model: ProjectModel, entity: ProjectEntity) {
        whenever(mapper.mapFromModel(model))
                .thenReturn(entity)
    }

}