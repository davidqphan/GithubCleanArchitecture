package com.dphan.remote.mapper

import com.dphan.data.model.ProjectEntity
import com.dphan.remote.model.ProjectModel

class ProjectsResponseModelMapper: ModelMapper<ProjectModel, ProjectEntity> {

    override fun mapFromModel(model: ProjectModel): ProjectEntity {
        return ProjectEntity(model.id, model.name, model.fullName, model.starCount.toString(),
                model.dateCreated, model.owner.ownerName, model.owner.ownerAvatar, false)
    }

}