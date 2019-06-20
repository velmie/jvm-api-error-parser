package com.velmie.parser.entity.parserResponse

/**
 * Common class used by API responses.
 * @param <T> the type of the apiResponse object
</T> */
@Suppress("unused")
sealed class ApiParserResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiParserErrorResponse<T> {
            return ApiParserErrorResponse(
                listOf(
                    ParserMessageEntity(
                        "unknown target",
                        null,
                        "unknown code",
                        error.message ?: "unknown error"
                    )
                )
            )
        }

        fun <T> create(errors: List<ParserMessageEntity>): ApiParserErrorResponse<T> {
            return ApiParserErrorResponse(errors)
        }

        fun <T> create(response: ParserResponseEntity<T>): ApiParserResponse<T> {
            return when {
                response.errors.isNotEmpty() -> ApiParserErrorResponse(errors = response.errors)
                response.data != null -> ApiParserSuccessResponse(body = response.data)
                else -> ApiParserEmptyResponse()
            }
        }
    }
}

class ApiParserEmptyResponse<T> : ApiParserResponse<T>()

data class ApiParserSuccessResponse<T>(val body: T) : ApiParserResponse<T>()

data class ApiParserErrorResponse<T>(val errors: List<ParserMessageEntity>) : ApiParserResponse<T>()
