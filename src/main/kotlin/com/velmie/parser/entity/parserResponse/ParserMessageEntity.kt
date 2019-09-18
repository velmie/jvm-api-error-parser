package com.velmie.parser.entity.parserResponse

import com.velmie.parser.entity.apiResponse.interfaces.ErrorSourceInterface

data class ParserMessageEntity<out T>(
    val target: String,
    val source: ErrorSourceInterface?,
    val code: String,
    val message: T? = null
)