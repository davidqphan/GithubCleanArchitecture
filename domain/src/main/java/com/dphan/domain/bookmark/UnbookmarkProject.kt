package com.dphan.domain.bookmark

import com.dphan.domain.executor.PostExecutionThread
import com.dphan.domain.interactor.CompletableUseCase
import com.dphan.domain.repository.ProjectsRepository
import io.reactivex.Completable
import javax.inject.Inject

open class UnbookmarkProject @Inject constructor(
        private val projectsRepository: ProjectsRepository,
        postExecutionThread: PostExecutionThread)
    : CompletableUseCase<UnbookmarkProject.Params>(postExecutionThread) {

    public override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params can't be null!")
        return projectsRepository.unbookmarkProject(params.projectId)
    }

    data class Params constructor(val projectId: String) {
        companion object {
            fun forProject(projectId: String): Params {
                return Params(projectId)
            }
        }
    }
}