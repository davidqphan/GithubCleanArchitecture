package com.dphan.mobileui.test.factory

import com.dphan.presentation.model.ProjectView

object TestProjectFactory {

    fun makeProjectView(): ProjectView {
        return ProjectView(TestDataFactory.randomUuid(), TestDataFactory.randomUuid(),
                TestDataFactory.randomUuid(), TestDataFactory.randomUuid(), TestDataFactory.randomUuid(),
                TestDataFactory.randomUuid(), TestDataFactory.randomUuid(), TestDataFactory.randomBoolean())
    }

}