package com.suaferdeveloper.studybuddy.user

class UserLogin (
    private val login : String,
    private val password : String,
) {

    fun login() : String { return login }
    fun password() : String { return password }

}