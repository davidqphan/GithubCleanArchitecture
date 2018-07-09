package com.dphan.data

import com.dphan.data.mapper.ProjectMapper
import com.dphan.data.model.ProjectEntity
import com.dphan.data.repository.ProjectsCache
import com.dphan.data.repository.ProjectsDataStore
import com.dphan.data.store.ProjectsDataStoreFactory
import com.dphan.data.test.factory.ProjectFactory
import com.dphan.domain.model.Project
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.concurrent.CountedCompleter

@RunWith(JUnit4::class)
class ProjectsDataRepositoryTest {

    private val mapper = mock<ProjectMapper>()
    private val factory = mock<ProjectsDataStoreFactory>()
    private val store = mock<ProjectsDataStore>()
    private val cache = mock<ProjectsCache>()
    private val repository = ProjectsDataRepository(mapper, cache, factory)

    @Before fun setup() {
        stubFactoryGetDataStore()
        stubFactoryGetCacheDataStore()
        stubIsCacheExpired(Single.just(false))
        stubAreProjectsExpired(Single.just(false))
        stubSaveProjects(Completable.complete())
    }

    @Test fun getProjectsCompletes() {
        stubGetProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        stubMapper(ProjectFactory.makeProject(), any())

        val testObserver = repository.getProjects().test()
        testObserver.assertComplete()
    }

    private fun stubFactoryGetDataStore() {
        whenever(factory.getDataStore(any(), any()))
                .thenReturn(store)
    }

    private fun stubFactoryGetCacheDataStore() {
        whenever(factory.getCacheDataStore())
                .thenReturn(store)
    }

    private fun stubIsCacheExpired(single: Single<Boolean>) {
        whenever(cache.isProjectsCacheExpired())
                .thenReturn(single)
    }

    private fun stubAreProjectsExpired(single: Single<Boolean>) {
        whenever(cache.areProjectsCached())
                .thenReturn(single)
    }

    private fun stubSaveProjects(completable: Completable) {
        whenever(store.saveProjects(any()))
                .thenReturn(completable)
    }

    private fun stubGetProjects(observable: Observable<List<ProjectEntity>>?) {
        whenever(store.getProjects())
                .thenReturn(observable)
    }

    private fun stubMapper(model: Project, entity: ProjectEntity) {
        whenever(mapper.mapFromEntity(entity))
                .thenReturn(model)
    }

}