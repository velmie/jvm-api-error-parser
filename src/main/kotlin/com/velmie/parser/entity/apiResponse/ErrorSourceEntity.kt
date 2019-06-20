package com.velmie.parser.entity.apiResponse

import com.velmie.parser.entity.apiResponse.interfaces.ErrorSourceInterface

data class ErrorSourceEntity(override val field: String? = null) : ErrorSourceInterface