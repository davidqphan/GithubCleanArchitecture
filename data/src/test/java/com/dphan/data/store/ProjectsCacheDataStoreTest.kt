package com.dphan.data.store

import com.dphan.data.model.ProjectEntity
import com.dphan.data.repository.ProjectsCache
import com.dphan.data.test.factory.DataFactory
import com.dphan.data.test.factory.ProjectFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProjectsCacheDataStoreTest {

    private val cache = mock<ProjectsCache>()
    private val store = ProjectsCacheDataStore(cache)

    @Test
    fun getProjectsCompletes() {
        stubProjectsCacheGetProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        val observer = store.getProjects().test()
        observer.assertComplete()
    }

    @Test
    fun getProjectsReturnsData() {
        val data = listOf(ProjectFactory.makeProjectEntity())
        stubProjectsCacheGetProjects(Observable.just(data))
        val observer = store.getProjects().test()
        observer.assertValue(data)
    }

    @Test
    fun getProjectsCallsCacheSource() {
        stubProjectsCacheGetProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        store.getProjects().test()
        verify(cache).getProjects()
    }

    private fun stubProjectsCacheGetProjects(observable: Observable<List<ProjectEntity>>) {
        whenever(cache.getProjects())
                .thenReturn(observable)
    }

    @Test
    fun getBookmarkedProjectsCompletes() {
        stubProjectsCacheGetBookmarkedProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        val observer = store.getBookmarkedProjects().test()
        observer.assertComplete()
    }

    @Test
    fun getBookmarkedProjectsReturnsData() {
        val data = listOf(ProjectFactory.makeProjectEntity())
        stubProjectsCacheGetBookmarkedProjects(Observable.just(data))
        val observer = store.getBookmarkedProjects().test()
        observer.assertValue(data)
    }

    @Test
    fun getBookmarkedProjectsCallsCacheSource() {
        stubProjectsCacheGetBookmarkedProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        store.getBookmarkedProjects().test()
        verify(cache).getBookmarkedProjects()
    }

    private fun stubProjectsCacheGetBookmarkedProjects(observable: Observable<List<ProjectEntity>>) {
        whenever(cache.getBookmarkedProjects())
                .thenReturn(observable)
    }

    @Test
    fun saveProjectsCompletes() {
        stubProjectsCacheSaveProjects(Completable.complete())
        stubProjectsCacheSetLastCacheTime(Completable.complete())
        val observer = store.saveProjects(listOf(ProjectFactory.makeProjectEntity())).test()
        observer.assertComplete()
    }

    @Test
    fun saveProjectsCallsCacheStore() {
        val data = listOf(ProjectFactory.makeProjectEntity())
        stubProjectsCacheSaveProjects(Completable.complete())
        stubProjectsCacheSetLastCacheTime(Completable.complete())
        store.saveProjects(data).test()
        verify(cache).saveProjects(data)
    }

    private fun stubProjectsCacheSaveProjects(completable: Completable) {
        whenever(cache.saveProjects(any()))
                .thenReturn(completable)
    }

    private fun stubProjectsCacheSetLastCacheTime(completable: Completable) {
        whenever(cache.setLastCacheTime(any()))
                .thenReturn(completable)
    }

    @Test
    fun clearProjectsCompletes() {
        stubProjectsClearProjects(Completable.complete())
        val observer = store.clearProjects().test()
        observer.assertComplete()
    }

    @Test
    fun clearProjectsCallsCacheStore() {
        stubProjectsClearProjects(Completable.complete())
        store.clearProjects().test()
        verify(cache).clearProjects()
    }

    private fun stubProjectsClearProjects(completable: Completable) {
        whenever(cache.clearProjects())
                .thenReturn(completable)
    }

    @Test
    fun setProjectAsBookmarkedCompletes() {
        stubProjectsCacheSetProjectAsBookmarked(Completable.complete())
        val observer = store.setProjectAsBookmarked(DataFactory.randomString()).test()
        observer.assertComplete()
    }

    @Test
    fun setProjectAsBookmarkedCallsCacheStore() {
        val projectId = DataFactory.randomString()
        stubProjectsCacheSetProjectAsBookmarked(Completable.complete())
        store.setProjectAsBookmarked(projectId).test()
        verify(cache).setProjectAsBookmarked(projectId)
    }

    private fun stubProjectsCacheSetProjectAsBookmarked(completable: Completable) {
        whenever(cache.setProjectAsBookmarked(any()))
                .thenReturn(completable)
    }


    @Test
    fun setProjectAsNotBookmarkedCompletes() {
        stubProjectsCacheSetProjectAsNotBookmarked(Completable.complete())
        val observer = store.setProjectAsNotBookmarked(DataFactory.randomString()).test()
        observer.assertComplete()
    }

    @Test
    fun setProjectAsNotBookmarkedCallsCacheStore() {
        val projectId = DataFactory.randomString()
        stubProjectsCacheSetProjectAsNotBookmarked(Completable.complete())
        store.setProjectAsNotBookmarked(projectId).test()
        verify(cache).setProjectAsNotBookmarked(projectId)
    }

    private fun stubProjectsCacheSetProjectAsNotBookmarked(completable: Completable) {
        whenever(cache.setProjectAsNotBookmarked(any()))
                .thenReturn(completable)
    }
}