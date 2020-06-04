package com.arbelkilani.bingetv.data.mapper.base

interface Mapper<E, D> {

    fun mapFromEntity(type: E): D

    fun mapToEntity(type: D): E

}