package com.dphan.mobileui.mapper

interface ViewMapper<P, V> {

    fun mapToView(presentation: P): V

}