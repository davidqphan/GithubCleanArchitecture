package com.dphan.domain.bookmark

import com.dphan.domain.executor.PostExecutionThread
import com.dphan.domain.interactor.ObservableUseCase
import com.dphan.domain.model.Project
import com.dphan.domain.repository.ProjectsRepository
import io.reactivex.Observable
import javax.inject.Inject

open class GetBookmarkedProjects @Inject constructor(
        private val projectsRepository: ProjectsRepository,
        postExecutionThread: PostExecutionThread)
    : ObservableUseCase<List<Project>, Nothing?>(postExecutionThread) {
    
    public override fun buildUseCaseObservable(params: Nothing?): Observable<List<Project>> {
        return projectsRepository.getBookmarkedProjects()
    }
}