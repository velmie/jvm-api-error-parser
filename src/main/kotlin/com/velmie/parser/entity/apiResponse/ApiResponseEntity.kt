package com.velmie.parser.entity.apiResponse

import com.velmie.parser.entity.apiResponse.interfaces.ApiResponseInterface

data class ApiResponseEntity<T>(override val data: T?, override val errors: List<ErrorMessageEntity>) :
    ApiResponseInterface<T>