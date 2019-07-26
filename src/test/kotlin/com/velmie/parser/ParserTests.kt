package com.velmie.parser

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.velmie.parser.entity.DataEntity
import com.velmie.parser.entity.apiResponse.ApiResponseEntity
import com.velmie.parser.entity.apiResponse.interfaces.ErrorMessageInterface
import com.velmie.parser.entity.parserResponse.ApiParserEmptyResponse
import com.velmie.parser.entity.parserResponse.ApiParserErrorResponse
import com.velmie.parser.entity.parserResponse.ApiParserSuccessResponse
import com.velmie.parser.entity.parserResponse.ParserMessageEntity
import org.junit.Test
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ParserTests {

    private val gson = Gson()

    private val parser: ApiParser = ApiParser(
        mapOf(
            Pair(Constants.ErrorCode.INSUFFICIENT_FUNDS, Constants.Message.EMPTY_BALANCE),
            Pair(Constants.ErrorCode.INVALID_PASSWORD_CONFIRMATION, Constants.Message.PASSWORD_DO_NOT_MATCH),
            Pair(Constants.ErrorCode.INVALID_PUNCTUATION, Constants.Message.PUNCTUATION_ERROR)
        ), Constants.Message.DEFAULT
    )

    private var response: ApiResponseEntity<DataEntity>? = null

    @BeforeTest
    fun initJson() {
        response = gson.fromJson(getJson(), object : TypeToken<ApiResponseEntity<DataEntity>>() {}.type)
    }

    fun getJson(): String {
        return Files.lines(Paths.get(ClassLoader.getSystemClassLoader().getResource("test.json")!!.toURI()))
            .parallel()
            .collect(Collectors.joining())
    }

    @Test
    fun testMessageFromCode() {
        assertEquals(parser.getMessage(Constants.ErrorCode.INVALID_PUNCTUATION), Constants.Message.PUNCTUATION_ERROR)
        assertEquals(parser.getMessage(Constants.ErrorCode.UNKNOWN), Constants.Message.DEFAULT)
    }

    @Test
    fun testFirstErrorMessage() {
        assertEquals(parser.getFirstMessage(response!!.errors!!), Constants.Message.EMPTY_BALANCE)
        val errors = response!!.errors!!.toMutableList()
        errors.add(0, response!!.errors!![2])
        assertEquals(parser.getFirstMessage(errors), Constants.Message.DEFAULT)
    }

    @Test
    fun testMessage() {
        assertEquals(parser.getMessage(response!!.errors!!.last()), Constants.Message.PASSWORD_DO_NOT_MATCH)
        assertEquals(parser.getMessage(response!!.errors!![2]), Constants.Message.DEFAULT)
    }

    @Test
    fun testListError() {
        checkErrorList(response!!.errors!!, parser.getErrors(response!!.errors!!))
    }

    @Test
    fun testParsing() {
        val parserResponse = parser.getParserResponse(response!!)

        assertEquals(response!!.data, parserResponse.data)

        checkErrorList(response!!.errors!!, parserResponse.errors)
    }

    @Test
    fun testApiParserResponse() {
        val errorResponse = parser.parse(response!!)
        assertTrue { errorResponse is ApiParserErrorResponse }
        checkErrorList(response!!.errors!!, (errorResponse as ApiParserErrorResponse).errors)

        val emptyResponse = ApiResponseEntity(null, listOf())
        assertTrue { parser.parse(emptyResponse) is ApiParserEmptyResponse }

        val successResponse = ApiResponseEntity(response!!.data, listOf())
        assertTrue { parser.parse(successResponse) is ApiParserSuccessResponse }
        assertNotNull(successResponse.data)
        assertEquals(successResponse.data, response!!.data)
    }

    /* @Test
     fun testParse() {
         val parserResponseFromJson = parser.getParserResponse<DataEntity>(
             json = getJson(),
             type = object : TypeToken<ApiResponseEntity<DataEntity>>() {}.type
         )
         assertEquals(apiResponse!!.data, parserResponseFromJson.data)
         checkErrorList(apiResponse!!.errors, parserResponseFromJson.errors)
     }*/

    /*@Test
    fun getParserResponse () {
        val responseStringError: ResponseStringErrorEntity =
            gson.fromJson(getJson(), object : TypeToken<ResponseStringErrorEntity>() {}.type)
        val parserResponse = parser.getParserResponse(responseStringError.data, gson.toJson(responseStringError.errors))
        assertEquals(responseStringError.data, parserResponse.data)
        checkErrorList(apiResponse!!.errors, parser.getErrors(gson.toJson(responseStringError.errors)))
    }*/

    /* @Test
     fun parseErrorFromJson() {
         val responseStringError: ResponseStringErrorEntity =
             gson.fromJson(getJson(), object : TypeToken<ResponseStringErrorEntity>() {}.type)
         parser.getErrors(gson.toJson(responseStringError.errors))
         checkErrorList(apiResponse!!.errors, parser.getErrors(gson.toJson(responseStringError.errors)))
     }*/

    private fun checkErrorList(errors: List<ErrorMessageInterface>, parserErrors: List<ParserMessageEntity>) {
        assertEquals(errors[0].code, parserErrors[0].code)
        assertEquals(errors[1].code, parserErrors[1].code)
        assertEquals(errors[2].code, parserErrors[2].code)
        assertEquals(errors[3].code, parserErrors[3].code)

        assertEquals(parserErrors[0].message, Constants.Message.EMPTY_BALANCE)
        assertEquals(parserErrors[1].message, Constants.Message.PUNCTUATION_ERROR)
        assertEquals(parserErrors[2].message, Constants.Message.DEFAULT)
        assertEquals(parserErrors[3].message, Constants.Message.PASSWORD_DO_NOT_MATCH)

        assertNotNull(parserErrors[0].source)
        assertNotNull(parserErrors[3].source)
    }
}
