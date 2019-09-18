package com.velmie.parser.entity.parserResponse

/**
 * Common class used by API responses.
 * @param <T> the type of the apiResponse object
</T> */
@Suppress("unused")
sealed class ApiParserResponse<T, E> {
    companion object {
        fun <T> create(error: Throwable): ApiParserErrorResponse<T, String> {
            return ApiParserErrorResponse(
                listOf(
                    ParserMessageEntity(
                        "unknown target",
                        null,
                        "unknown code",
                        error.message
                    )
                )
            )
        }

        fun <T, E> create(errors: List<ParserMessageEntity<E>>): ApiParserErrorResponse<T, E> {
            return ApiParserErrorResponse(errors)
        }

        fun <T, E> create(response: ParserResponseEntity<T, E>): ApiParserResponse<T, E> {
            return when {
                response.errors.isNotEmpty() -> ApiParserErrorResponse(errors = response.errors)
                response.data != null -> ApiParserSuccessResponse(body = response.data)
                else -> ApiParserEmptyResponse()
            }
        }
    }
}

class ApiParserEmptyResponse<T, E> : ApiParserResponse<T, E>()

data class ApiParserSuccessResponse<T, E>(val body: T) : ApiParserResponse<T, E>()

data class ApiParserErrorResponse<T, E>(val errors: List<ParserMessageEntity<E>>) : ApiParserResponse<T, E>()
