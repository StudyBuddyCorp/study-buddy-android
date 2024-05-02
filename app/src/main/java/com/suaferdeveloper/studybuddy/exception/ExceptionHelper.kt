package com.suaferdeveloper.studybuddy.exception

import com.suaferdeveloper.studybuddy.R

class ExceptionHelper {

    companion object{

        fun getTextException(str : ExceptionType) : Int {

            if(str == ExceptionType.EMPTY){
                return R.string.exception_empty
            }

            if(str == ExceptionType.UNAUTHORIZED){
                return R.string.exception_wrong_password
            }

            if(str == ExceptionType.NOT_FOUND){
                return R.string.exception_not_found
            }

            return R.string.exception_unknown_error

        }


    }
}