package com.velmie.parser.entity.parserResponse

data class ParserResponseEntity<T>(val data: T?, val errors: List<ParserMessageEntity>)