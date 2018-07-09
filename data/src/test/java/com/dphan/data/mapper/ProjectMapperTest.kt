package com.dphan.data.mapper

import com.dphan.data.model.ProjectEntity
import com.dphan.data.test.factory.ProjectFactory
import com.dphan.domain.model.Project
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ProjectMapperTest {

    private val projectMapper = ProjectMapper()

    @Test fun mapFromEntityMapsData() {
        val entity = ProjectFactory.makeProjectEntity()
        val model = projectMapper.mapFromEntity(entity)

        assertEqualData(entity, model)
    }

    @Test fun mapToEntityMapsData() {
        val model = ProjectFactory.makeProject()
        val entity = projectMapper.mapToEntity(model)

        assertEqualData(entity, model)
    }

    private fun assertEqualData(entity: ProjectEntity, model: Project) {
        assertEquals(entity.id, model.id)
        assertEquals(entity.name, model.name)
        assertEquals(entity.fullName, model.fullName)
        assertEquals(entity.starCount, model.starCount)
        assertEquals(entity.dateCreated, model.dateCreated)
        assertEquals(entity.ownerName, model.ownerName)
        assertEquals(entity.ownerAvatar, model.ownerAvatar)
        assertEquals(entity.isBookmarked, model.isBookmarked)
    }

}