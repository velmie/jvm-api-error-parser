package com.velmie.parser

import com.velmie.parser.entity.parserResponse.ApiParserResponse
import com.velmie.parser.entity.parserResponse.ParserMessageEntity
import com.velmie.parser.entity.parserResponse.ParserResponseEntity
import com.velmie.parser.entity.apiResponse.interfaces.ApiResponseInterface
import com.velmie.parser.entity.apiResponse.interfaces.ErrorMessageInterface

class ApiParser(val errorMessages: Map<String, String>, val defaultErrorMessage: String) {

    fun <T> parse(response: ApiResponseInterface<T>): ApiParserResponse<T> {
        return ApiParserResponse.create(response = this.getParserResponse(response))
    }

    /*fun <T> getParserResponse(json: String, type: Type? = null): ParserResponseEntity<T> {
        return getParserResponse(apiResponse = gson.fromJson(json, type))
    }*/

    fun <T> getParserResponse(response: ApiResponseInterface<T>): ParserResponseEntity<T> {
        return ParserResponseEntity(
            response.data,
            errors = getErrors(response.errors ?: emptyList())
        )
    }

    /*fun <T> getParserResponse(data: T?, errorJson: String): ParserResponseEntity<T> {
        return ParserResponseEntity(data, errors = getErrors(errorJson))
    }*/

    fun getErrors(errors: List<ErrorMessageInterface>): List<ParserMessageEntity> {
        return errors.map {
            ParserMessageEntity(
                it.target,
                it.source,
                it.code,
                getMessage(it.code)
            )
        }
    }

    /*fun getErrors(json: String): List<ParserMessageEntity> {
        return getErrors(
            gson.fromJson<List<ErrorMessageEntity>>(
                json,
                object : TypeToken<List<ErrorMessageEntity>>() {}.type
            )
        )
    }*/

    fun getMessage(errorCode: String): String {
        return errorMessages[errorCode] ?: defaultErrorMessage
    }

    fun getMessage(errorMessage: ErrorMessageInterface): String {
        return errorMessages[errorMessage.code] ?: defaultErrorMessage
    }

    fun getFirstMessage(errors: List<ErrorMessageInterface>): String {
        return getMessage(errors.first())
    }
}
