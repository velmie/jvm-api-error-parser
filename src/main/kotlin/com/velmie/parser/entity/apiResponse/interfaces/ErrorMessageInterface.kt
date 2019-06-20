package com.velmie.parser.entity.apiResponse.interfaces

interface ErrorMessageInterface {
    val code: String
    val target: String
    val source: ErrorSourceInterface?
    val message: String
}