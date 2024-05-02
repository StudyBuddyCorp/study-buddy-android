package com.suaferdeveloper.studybuddy

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class CourseActivity : AppCompatActivity() {


    private var i : Intent = Intent()

    private var navigationStatus : Boolean = true

    private lateinit var linearCourse : LinearLayout; private lateinit var linearBRS : LinearLayout
    private lateinit var linearProfile :LinearLayout; private lateinit var linearTime :LinearLayout

    private lateinit var listCourse : ListView

    private lateinit var linearSortMenu: LinearLayout
    private lateinit var selectedItemText: TextView
    private lateinit var imageSortMenu : ImageView

    private var isMenuOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)
        window.statusBarColor = ContextCompat.getColor(this, R.color.background)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.blue)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        val linearNavigation = findViewById<LinearLayout>(R.id.linearNavigation)
        val imageNavigation = findViewById<ImageView>(R.id.imageNavigation)

        init()


        linearNavigation.setOnClickListener{
            TransitionManager.beginDelayedTransition(linearNavigation)
            navigationStatus = if(navigationStatus){
                setView(View.VISIBLE)
                rotateImage(imageNavigation, 0f, 180f)
                false
            }else{
                setView(View.GONE)
                rotateImage(imageNavigation, 180f, 0f)
                true
            }

        }
    }

    private fun init(){
        linearCourse = findViewById(R.id.linearCourse)
        linearBRS = findViewById(R.id.linearBRS)
        linearProfile = findViewById(R.id.linearProfile)
        linearTime = findViewById(R.id.linearTime)

        linearSortMenu = findViewById(R.id.linearSortMenu)
        selectedItemText = findViewById(R.id.selected_item_text)
        imageSortMenu = findViewById(R.id.imageSortMenu)


        listCourse = findViewById(R.id.listCourse)

        val listString : List<String> = listOf("1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listString)
        listCourse.adapter = adapter


        linearBRS.setOnClickListener {
            if(linearBRS.visibility == View.VISIBLE){
                i.setClass(this@CourseActivity, BRSActivity::class.java)
                startActivity(i)
            }
        }

        linearProfile.setOnClickListener {
            if(linearProfile.visibility == View.VISIBLE){
                i.setClass(this@CourseActivity, BRSActivity::class.java)
                startActivity(i)
            }
        }

        linearTime.setOnClickListener {
            if(linearTime.visibility == View.VISIBLE){
                i.setClass(this@CourseActivity, BRSActivity::class.java)
                startActivity(i)
            }
        }



    }

    fun toggleMenu(view: View) {
        rotateImage(imageSortMenu, 0f, 180f)

        val popupMenu = PopupMenu(this, linearSortMenu)
        popupMenu.inflate(R.menu.drop_menu_corse_sort)

        popupMenu.setOnMenuItemClickListener { item: MenuItem? ->
            when (item?.itemId) {
                R.id.action_item_AZ -> {
                    selectedItemText.text = resources.getText(R.string.course_sort_AZ)
                    true
                }
                R.id.action_item_ZA -> {
                    selectedItemText.text = resources.getText(R.string.course_sort_ZA)
                    true
                }
                else -> false
            }
        }

        popupMenu.setOnDismissListener {
            rotateImage(imageSortMenu, 180f, 0f)
        }

        popupMenu.show()
    }


    private fun setView(view: Int) {
        linearCourse.visibility = view
        linearBRS.visibility = view
        linearProfile.visibility = view
        linearTime.visibility = view
    }

    private fun rotateImage(imageView: ImageView, from : Float, to : Float){
        val rotationAnimation = ObjectAnimator.ofFloat(imageView, "rotation", from, to)
        rotationAnimation.duration = 500
        rotationAnimation.start()
    }
}