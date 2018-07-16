package com.dphan.presentation.mapper

import com.dphan.domain.model.Project
import com.dphan.presentation.model.ProjectView
import javax.inject.Inject

open class ProjectViewMapper @Inject constructor() : Mapper<ProjectView, Project> {

    override fun mapToView(type: Project): ProjectView {
        return ProjectView(type.id, type.name, type.fullName,
                type.starCount, type.dateCreated, type.ownerName,
                type.ownerAvatar, type.isBookmarked)
    }

}