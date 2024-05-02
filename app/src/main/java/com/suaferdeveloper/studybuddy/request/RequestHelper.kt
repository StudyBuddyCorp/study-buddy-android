package com.suaferdeveloper.studybuddy.request

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.suaferdeveloper.studybuddy.user.LoginResult
import com.suaferdeveloper.studybuddy.user.UserLogin
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.Request
import okio.IOException
import org.json.JSONObject


class RequestHelper {



    companion object {
        private const val PATH = "http://10.0.2.2:8080/api/v1/"
        private val client = OkHttpClient()


        fun login() : String {

            return "http://10.0.2.2:8080/api/v1/auth/login"
        }

        fun createBody(email: String, password: String) : RequestBody {
            return RequestBody.create("application/json".toMediaTypeOrNull(), createJSON(email, password))
        }

        private fun createJSON(email: String, password: String): String {
            val json = JSONObject()
            json.put("email", email)
            json.put("password", password)

            return json.toString()
        }
    }
}