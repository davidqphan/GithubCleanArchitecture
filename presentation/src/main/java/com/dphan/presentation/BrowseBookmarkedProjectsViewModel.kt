package com.dphan.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.dphan.domain.interactor.bookmark.GetBookmarkedProjects
import com.dphan.domain.model.Project
import com.dphan.presentation.mapper.ProjectViewMapper
import com.dphan.presentation.model.ProjectView
import com.dphan.presentation.state.Resource
import com.dphan.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BrowseBookmarkedProjectsViewModel @Inject constructor(
        private val getBookmarkedProjects: GetBookmarkedProjects,
        private val mapper: ProjectViewMapper) : ViewModel() {

    private val liveData: MutableLiveData<Resource<List<ProjectView>>> = MutableLiveData()

    fun getProjects(): LiveData<Resource<List<ProjectView>>> {
        return liveData
    }

    override fun onCleared() {
        getBookmarkedProjects.dispose()
        super.onCleared()
    }

    fun fetchProjects() {
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        return getBookmarkedProjects.execute(ProjectsSubscriber())
    }

    inner class ProjectsSubscriber : DisposableObserver<List<Project>>() {
        override fun onComplete() {
        }

        override fun onNext(t: List<Project>) {
            val projectView = t.map { mapper.mapToView(it) }
            liveData.postValue(Resource(ResourceState.SUCCESS, projectView, null))
        }

        override fun onError(e: Throwable) {
            val errorMessage = e.localizedMessage
            liveData.postValue(Resource(ResourceState.ERROR, null, errorMessage))
        }

    }

}