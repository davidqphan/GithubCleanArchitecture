package com.dphan.presentation.browse

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.dphan.domain.interactor.bookmark.BookmarkProject
import com.dphan.domain.interactor.bookmark.UnbookmarkProject
import com.dphan.domain.interactor.browse.GetProjects
import com.dphan.domain.model.Project
import com.dphan.presentation.BrowseProjectsViewModel
import com.dphan.presentation.mapper.ProjectViewMapper
import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.DisposableObserver
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor

@RunWith(JUnit4::class)
class BrowseProjectsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    var getProjects = mock<GetProjects>()
    var bookmarkProject = mock<BookmarkProject>()
    var unbookmarkProject = mock<UnbookmarkProject>()
    var projectViewMapper = mock<ProjectViewMapper>()
    var projectViewModel = BrowseProjectsViewModel(getProjects, bookmarkProject, unbookmarkProject,
            projectViewMapper)

    @Captor
    val captor = argumentCaptor<DisposableObserver<List<Project>>>()

    @Test
    fun fetchProjectsExecutesUseCase() {
        projectViewModel.fetchProjects()
        verify(getProjects, times(1)).execute(any(), eq(null))

    }
}