package com.dphan.domain.interactor.bookmark

import com.dphan.domain.bookmark.BookmarkProject
import com.dphan.domain.executor.PostExecutionThread
import com.dphan.domain.repository.ProjectsRepository
import com.dphan.domain.test.ProjectDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class BookmarkProjectTest {

    private lateinit var bookmarkProject: BookmarkProject
    @Mock
    lateinit var projectsRepository: ProjectsRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        bookmarkProject = BookmarkProject(projectsRepository, postExecutionThread)
    }

    @Test
    fun bookProjectCompletes() {
        stubBookmarkProject(Completable.complete())
        val testObserver = bookmarkProject.buildUseCaseCompletable(BookmarkProject.Params.forProject(ProjectDataFactory.randomUuid())).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun bookmarkProjectThrowsException() {
        bookmarkProject.buildUseCaseCompletable().test()
    }

    private fun stubBookmarkProject(completable: Completable) {
        whenever(projectsRepository.bookmarkProject(any()))
                .thenReturn(completable)
    }
}