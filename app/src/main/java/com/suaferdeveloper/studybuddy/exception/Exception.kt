package com.suaferdeveloper.studybuddy.exception

class Exception(
    val status : ExceptionType,
    val timestamp : String,
    val message : String,
    val debugMessage : String,
) {
}