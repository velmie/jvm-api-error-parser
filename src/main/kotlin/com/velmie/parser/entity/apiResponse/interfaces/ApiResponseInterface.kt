package com.velmie.parser.entity.apiResponse.interfaces

interface ApiResponseInterface<T> {
    val data: T?
    val errors: List<ErrorMessageInterface>
}