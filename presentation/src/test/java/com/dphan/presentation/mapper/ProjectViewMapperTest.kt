package com.dphan.presentation.mapper

import com.dphan.domain.model.Project
import com.dphan.presentation.model.ProjectView
import com.dphan.presentation.test.factory.ProjectFactory
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProjectViewMapperTest {

    private val mapper = ProjectViewMapper()

    @Test fun mapToViewMapsData() {
        val project = ProjectFactory.makeProject()
        val projectView = mapper.mapToView(project)

        assertEqualData(projectView, project)
    }

    private fun assertEqualData(projectView: ProjectView, model: Project) {
        assertEquals(projectView.id, model.id)
        assertEquals(projectView.name, model.name)
        assertEquals(projectView.fullName, model.fullName)
        assertEquals(projectView.starCount, model.starCount)
        assertEquals(projectView.dateCreated, model.dateCreated)
        assertEquals(projectView.ownerName, model.ownerName)
        assertEquals(projectView.ownerAvatar, model.ownerAvatar)
        assertEquals(projectView.isBookmarked, model.isBookmarked)
    }
}