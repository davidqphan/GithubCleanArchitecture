package com.dphan.presentation.state

/*
    ResourceState Scenarios
    SUCCESS: message will be null
    ERROR: data will be null
 */
class Resource<out T> constructor(val status: ResourceState,
                                  val data: T?,
                                  val message: String?)