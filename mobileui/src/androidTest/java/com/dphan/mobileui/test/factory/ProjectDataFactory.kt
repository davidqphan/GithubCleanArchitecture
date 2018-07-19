package com.dphan.mobileui.test.factory

import com.dphan.domain.model.Project

object ProjectDataFactory {

    fun makeProject(): Project {
        return Project(TestDataFactory.randomUuid(), TestDataFactory.randomUuid(),
                TestDataFactory.randomUuid(), TestDataFactory.randomUuid(), TestDataFactory.randomUuid(),
                TestDataFactory.randomUuid(), TestDataFactory.randomUuid(), TestDataFactory.randomBoolean())
    }

}