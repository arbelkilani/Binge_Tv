package com.arbelkilani.bingetv.data.mappers.base

interface DocumentDataMapper<Document, Data> {

    fun mapFromData(type: Data): Document

    fun mapFromDocument(type: Document): Data
}
