package com.dphan.data.store

import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import kotlin.test.assertEquals

class ProjectsDataStoreFactoryTest {

    private val cacheDataStore = mock<ProjectsCacheDataStore>()
    private val remoteDataStore = mock<ProjectsRemoteDataStore>()
    private val projectsDataStoreFactory = ProjectsDataStoreFactory(cacheDataStore, remoteDataStore)

    @Test
    fun getDataStoreReturnsRemoteStoreWhenCacheExpired() {
        assertEquals(remoteDataStore, projectsDataStoreFactory.getDataStore(true, true))
    }

    @Test
    fun getDataStoreReturnsRemoteStoreWhenProjectsNotCached() {
        assertEquals(remoteDataStore, projectsDataStoreFactory.getDataStore(false, false))
    }

    @Test
    fun getDataStoreReturnsCacheStore() {
        assertEquals(cacheDataStore, projectsDataStoreFactory.getDataStore(true, false))
    }

    @Test
    fun getCacheDataStoreReturnsCacheStore() {
        assertEquals(cacheDataStore, projectsDataStoreFactory.getCacheDataStore())
    }
}