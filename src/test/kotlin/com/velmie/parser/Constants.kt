package com.velmie.parser

object Constants {

    object Message {
        const val DEFAULT = "DEFAULT"
        const val EMPTY_BALANCE = "It seems that user has empty balance"
        const val PUNCTUATION_ERROR = "It seems that the password provided is missing a punctuation character"
        const val PASSWORD_DO_NOT_MATCH = "It seems that the password and password confirmation fields do not match"
    }

    object ErrorCode {
        const val INSUFFICIENT_FUNDS = "insufficient_funds"
        const val INVALID_PUNCTUATION = "invalid_punctuation"
        const val INVALID_PASSWORD_CONFIRMATION = "invalid_password_confirmation"
        const val UNKNOWN = "unknown_code"
    }

    object TARGET {
        const val FIELD = "field"
        const val COMMON = "common"
    }

}