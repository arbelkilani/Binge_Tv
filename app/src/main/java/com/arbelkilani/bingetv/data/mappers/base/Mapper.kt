package com.arbelkilani.bingetv.data.mappers.base

interface Mapper<E, D> {

    fun mapFromEntity(type: E): D

    fun mapToEntity(type: D): E

}