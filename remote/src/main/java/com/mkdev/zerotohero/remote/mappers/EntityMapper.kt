package com.mkdev.zerotohero.remote.mappers

interface EntityMapper<M, E> {

    fun mapFromModel(model: M): E
}
