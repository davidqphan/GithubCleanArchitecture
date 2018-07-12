package com.dphan.remote.test.factory

import com.dphan.data.model.ProjectEntity
import com.dphan.remote.model.OwnerModel
import com.dphan.remote.model.ProjectModel
import com.dphan.remote.model.ProjectsResponseModel

object ProjectDataFactory {

    fun makeOwner(): OwnerModel {
        return OwnerModel(DataFactory.randomUuid(), DataFactory.randomUuid())
    }

    fun makeProject(): ProjectModel {
        return ProjectModel(DataFactory.randomUuid(), DataFactory.randomUuid(),
                DataFactory.randomUuid(), DataFactory.randomInt(),
                DataFactory.randomUuid(), makeOwner())
    }

    fun makeProjectEntity(): ProjectEntity {
        return ProjectEntity(DataFactory.randomUuid(), DataFactory.randomUuid(),
                DataFactory.randomUuid(), DataFactory.randomUuid(),
                DataFactory.randomUuid(), DataFactory.randomUuid(),
                DataFactory.randomUuid(), DataFactory.randomBoolean())
    }

    fun makeProjectsResponse(): ProjectsResponseModel {
        return ProjectsResponseModel(listOf(makeProject(), makeProject()))
    }
}