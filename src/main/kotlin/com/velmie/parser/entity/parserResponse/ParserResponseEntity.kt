package com.velmie.parser.entity.parserResponse

data class ParserResponseEntity<out T, out E>(val data: T?, val errors: List<ParserMessageEntity<E>>)