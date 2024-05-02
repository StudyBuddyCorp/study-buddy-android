package com.suaferdeveloper.studybuddy.user

class LoginResult(
    private val result : LoginResultType?,
    private val message : String
) {

    fun getResult() : LoginResultType? {return result}
    fun getMessage() : String {return message}

    enum class LoginResultType{
        Error, Success
    }
}