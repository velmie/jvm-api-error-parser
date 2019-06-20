package com.velmie.parser.entity.apiResponse

import com.velmie.parser.entity.apiResponse.interfaces.ErrorMessageInterface

data class ErrorMessageEntity(
    override val code: String = "",
    override val target: String = "",
    override val source: ErrorSourceEntity? = null,
    override val message: String = ""
) : ErrorMessageInterface