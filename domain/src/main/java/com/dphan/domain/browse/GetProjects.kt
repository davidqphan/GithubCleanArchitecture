package com.dphan.domain.browse

import com.dphan.domain.executor.PostExecutionThread
import com.dphan.domain.ObservableUseCase
import com.dphan.domain.model.Project
import com.dphan.domain.repository.ProjectsRepository
import io.reactivex.Observable
import javax.inject.Inject

open class GetProjects @Inject constructor(
        private val projectsRepository: ProjectsRepository,
        postExecutionThread: PostExecutionThread)
    : ObservableUseCase<List<Project>, Nothing?>(postExecutionThread) {

    public override fun buildUseCaseObservable(params: Nothing?): Observable<List<Project>> {
        return projectsRepository.getProjects()
    }

}