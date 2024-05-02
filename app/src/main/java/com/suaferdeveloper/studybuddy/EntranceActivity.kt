package com.suaferdeveloper.studybuddy

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.suaferdeveloper.studybuddy.exception.ExceptionHelper
import com.suaferdeveloper.studybuddy.user.LoginResult
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.IOException
import java.util.Locale

import com.suaferdeveloper.studybuddy.exception.ExceptionType


import com.suaferdeveloper.studybuddy.request.RequestHelper.Companion.createBody
import com.suaferdeveloper.studybuddy.request.RequestHelper.Companion.login
import com.suaferdeveloper.studybuddy.user.User

class EntranceActivity : AppCompatActivity() {

    private val client = OkHttpClient()

    private lateinit var textEnter : TextView
    private lateinit var edittextLogin : EditText; private lateinit var edittextPassword : EditText

    private lateinit var textMessage : TextView; private lateinit var linearMessage : LinearLayout

    private lateinit var dialogLoading : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrance)
        window.statusBarColor = ContextCompat.getColor(this, R.color.background)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.background)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR

        init()
    }


    private fun init(){
        val linear = findViewById<LinearLayout>(R.id.linear)
        val slideInTop = AnimationUtils.loadAnimation(this, R.anim.main_animation)
        linear.startAnimation(slideInTop)

        edittextLogin = findViewById(R.id.edittextLogin)
        edittextPassword = findViewById(R.id.edittextPassword)

        textEnter = findViewById(R.id.textEnter)


        linearMessage = findViewById(R.id.linearMessage)
        textMessage = findViewById(R.id.textMessage)

        val textLangRU = findViewById<TextView>(R.id.textLangRU)
        val textLangEN = findViewById<TextView>(R.id.textLangEN)
        val textLangZH = findViewById<TextView>(R.id.textLangZH)

        edittextLogin.setText("test@test.ru")
        edittextPassword.setText("1234")

        chooseLang(textLangRU, textLangEN, textLangZH)

        edittextLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                edittextLogin.setBackgroundResource(R.drawable.corner_blue_2dp_20dp)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        edittextPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                edittextPassword.setBackgroundResource(R.drawable.corner_blue_2dp_20dp)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        findViewById<ImageView>(R.id.image_logo).setOnClickListener {
            val i = Intent(this@EntranceActivity, CourseActivity::class.java)
            startActivity(i)
        }


        textEnter.setOnClickListener{
            if(checkField()){
                showError()
                showErrorMessage(ExceptionType.EMPTY)
            }else{
                click()
            }

            linearMessage.setOnClickListener {
                linearMessage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_move_up))
                linearMessage.visibility = View.GONE
            }



            //val intent = Intent(this, CourseActivity::class.java)
            //startActivity(intent)
        }
    }

    private fun createCreateDialog() { /** Создание диалога загрузки **/
        dialogLoading = Dialog(this@EntranceActivity)
        dialogLoading.setContentView(R.layout.loading_dialog)
        dialogLoading.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialogLoading.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogLoading.window!!.attributes.windowAnimations = R.style.DialogLoadingAnim
        dialogLoading.setCancelable(false)

        val imageLoading = dialogLoading.findViewById<ImageView>(R.id.imageLoading)

        val rotateAnimation = RotateAnimation(0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimation.interpolator = LinearInterpolator()
        rotateAnimation.duration = 1000
        rotateAnimation.repeatCount = Animation.INFINITE

        imageLoading.startAnimation(rotateAnimation)

        dialogLoading.show()
    }




    private fun chooseLang(textLangRU : TextView, textLangEN : TextView, textLangZH : TextView){
        val lang = Locale.getDefault().language
        val color = resources.getColor(R.color.white)

        if(lang == "ru"){ setChosenLang(textLangRU, color); return }
        if(lang == "en"){ setChosenLang(textLangEN, color); return }
        if(lang == "zh"){ setChosenLang(textLangZH, color) }
    }
    private fun setChosenLang(textView: TextView, color : Int){ /** Устанавливаем стиль выбранного языка **/
        textView.setBackgroundResource(R.drawable.selected_language_light)
        textView.setTextColor(color)
    }


    private fun click() {
        linearMessage.visibility = View.GONE
        showWait()
        textEnter.isClickable = false

        createCreateDialog()

        run(login(), createBody(edittextLogin.text.toString(), edittextPassword.text.toString())) { loginResult ->
            runOnUiThread {

                if(loginResult.getMessage().contains("\"message\":") && loginResult.getMessage().contains("\"status\":")){
                    try{
                        val jsonGame: String = loginResult.getMessage()
                        val gson = Gson()
                        val type = object : TypeToken<com.suaferdeveloper.studybuddy.exception.Exception>() {}.type
                        val error : com.suaferdeveloper.studybuddy.exception.Exception = gson.fromJson(jsonGame, type)
                        //Toast.makeText(this@EntranceActivity, error.message, Toast.LENGTH_LONG).show()
                        dialogLoading.dismiss()
                        showErrorMessage(error.status)


                    }catch (e : Exception){
                        dialogLoading.dismiss()
                        Log.d(TAG, e.message.toString())
                        showErrorMessage(ExceptionType.UNKNOWN_ERROR)
                    }

                    showError()
                    setDef()
                    textEnter.isClickable = true

                }else{
                    //Toast.makeText(this@EntranceActivity, "+", Toast.LENGTH_LONG).show()
                    try{
                        val user = User.getInstance()
                        val jsonGame: String = loginResult.getMessage()
                        val gson = Gson()
                        val type = object : TypeToken<User>() {}.type
                        user.updateFrom(gson.fromJson(jsonGame, type))

                        val hi : String = resources.getText(R.string.hi).toString() + user.getName()
                        Toast.makeText(this@EntranceActivity, hi, Toast.LENGTH_LONG).show()


                    }catch (e : Exception){
                        dialogLoading.dismiss()
                        Log.d(TAG, e.message.toString())
                        showErrorMessage(ExceptionType.UNKNOWN_ERROR)
                        setDef()
                        textEnter.isClickable = true

                    }

                }
            }
        }
    }


    private fun run(url: String, requestBody: RequestBody, callback: (LoginResult) -> Unit) {
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val responseBody = e.message
                //runOnUiThread { Toast.makeText(this@EntranceActivity, responseBody, Toast.LENGTH_LONG).show() }
                if (responseBody != null) {
                    Log.d(TAG, responseBody)
                    callback(LoginResult(LoginResult.LoginResultType.Error, responseBody))
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                //runOnUiThread { Toast.makeText(this@EntranceActivity, responseBody, Toast.LENGTH_LONG).show() }
                if (responseBody != null) {
                    Log.d(TAG, responseBody)
                    callback(LoginResult(LoginResult.LoginResultType.Success, responseBody))
                }
            }
        })
    }


    private fun checkField() : Boolean { return edittextLogin.text.toString().isEmpty() || edittextPassword.text.toString().isEmpty() }

    private fun showErrorMessage(exceptionType: ExceptionType){
        linearMessage.visibility = View.VISIBLE
        textMessage.setText(ExceptionHelper.getTextException(exceptionType))
        val animName = AnimationUtils.loadAnimation(applicationContext, R.anim.anim_move_top)
        linearMessage.startAnimation(animName)
    }

    private fun showError(){
        edittextLogin.setBackgroundResource(R.drawable.corner_red_2dp_20dp)
        edittextPassword.setBackgroundResource(R.drawable.corner_red_2dp_20dp)
    }

    private fun setDef(){
        edittextLogin.setBackgroundResource(R.drawable.corner_blue_2dp_20dp)
        edittextPassword.setBackgroundResource(R.drawable.corner_blue_2dp_20dp)
        textEnter.setBackgroundResource(R.drawable.background_button_blue)
    }

    private fun showWait(){
        textEnter.setBackgroundResource(R.drawable.background_button_gray)
        edittextLogin.setBackgroundResource(R.drawable.corner_gray_2dp_20dp)
        edittextPassword.setBackgroundResource(R.drawable.corner_gray_2dp_20dp)
    }

}