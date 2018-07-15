package com.dphan.cache.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.dphan.cache.db.ProjectsDatabase
import com.dphan.cache.test.factory.ProjectDataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class CachedProjectsDaoTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            ProjectsDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getProjectsReturnData() {
        val project = ProjectDataFactory.makeCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))

        val observer = database.cachedProjectsDao().getProjects().test()
        observer.assertValue(listOf(project))
    }

    @Test
    fun deleteProjectsClearData() {
        val project = ProjectDataFactory.makeCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))
        database.cachedProjectsDao().deleteProjects()

        val observer = database.cachedProjectsDao().getProjects().test()
        observer.assertValue(emptyList())
    }

    @Test
    fun getBookmarkedProjectsReturnData() {
        val project = ProjectDataFactory.makeCachedProject()
        val bookmarkedProject = ProjectDataFactory.makeBookmarkedCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project, bookmarkedProject))

        val observer = database.cachedProjectsDao().getBookmarkedProjects().test()
        observer.assertValue(listOf(bookmarkedProject))
    }

    @Test
    fun setProjectAsBookmarkedSavesData() {
        val project = ProjectDataFactory.makeCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))
        database.cachedProjectsDao().setBookmarkStatus(true, project.id)
        project.isBookmarked = true

        val observer = database.cachedProjectsDao().getBookmarkedProjects().test()
        observer.assertValue(listOf(project))
    }

    @Test
    fun setProjectAsNotBookmarkedSavesData() {
        val project = ProjectDataFactory.makeBookmarkedCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))
        database.cachedProjectsDao().setBookmarkStatus(false, project.id)
        project.isBookmarked = false

        val observer = database.cachedProjectsDao().getBookmarkedProjects().test()
        observer.assertValue(emptyList())
    }

}