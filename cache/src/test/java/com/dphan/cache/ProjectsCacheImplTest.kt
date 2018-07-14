package com.dphan.cache

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.dphan.cache.db.ProjectsDatabase
import com.dphan.cache.mapper.CachedProjectMapper
import com.dphan.cache.test.factory.ProjectDataFactory
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ProjectsCacheImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            ProjectsDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    private val entityMapper = CachedProjectMapper()
    private val cache = ProjectsCacheImpl(database, entityMapper)

    @Test
    fun clearTablesCompletes() {
        val observer = cache.clearProjects().test()
        observer.assertComplete()
    }

    @Test
    fun savedProjectsCompletes() {
        val projects = listOf(ProjectDataFactory.makeProjectEntity())
        val observer = cache.saveProjects(projects).test()
        observer.assertComplete()
    }

    @Test
    fun getProjectsReturnsData() {
        val projects = listOf(ProjectDataFactory.makeProjectEntity())
        cache.saveProjects(projects).test()

        val observer = cache.getProjects().test()
        observer.assertValue(projects)
    }

    @Test
    fun getBookmarkedProjectsReturnsData() {
        val bookmarkedProject = ProjectDataFactory.makeBookmarkedProjectEntity()
        val projects = listOf(ProjectDataFactory.makeProjectEntity(),
                bookmarkedProject)
        cache.saveProjects(projects).test()

        val observer = cache.getBookmarkedProjects().test()
        observer.assertValue(listOf(bookmarkedProject))
    }

    @Test
    fun setProjectAsBookmarkedCompletes() {
        val projects = listOf(ProjectDataFactory.makeProjectEntity())
        cache.saveProjects(projects).test()

        val observer = cache.setProjectAsBookmarked(projects[0].id).test()
        observer.assertComplete()
    }

    @Test
    fun setProjectAsNotBookmarkedCompletes() {
        val projects = listOf(ProjectDataFactory.makeBookmarkedProjectEntity())
        cache.saveProjects(projects).test()

        val observer = cache.setProjectAsNotBookmarked(projects[0].id).test()
        observer.assertComplete()
    }

    @Test
    fun areProjectsCacheReturnsData() {
        val projects = listOf(ProjectDataFactory.makeProjectEntity())
        cache.saveProjects(projects).test()

        val observer = cache.areProjectsCached().test()
        observer.assertValue(true)
    }

    @Test
    fun setLastCacheTimeCompletes() {
        val observer = cache.setLastCacheTime(1000L).test()
        observer.assertComplete()
    }

    @Test
    fun isProjectsCacheExpiredReturnsExpired() {
        val observer = cache.isProjectsCacheExpired().test()
        observer.assertValue(true)
    }

    @Test
    fun isProjectsCacheExpiredReturnsNotExpired() {
        cache.setLastCacheTime(System.currentTimeMillis()).test()
        val observer = cache.isProjectsCacheExpired().test()
        observer.assertValue(false)
    }

}