package com.suaferdeveloper.studybuddy.course

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginTop
import com.suaferdeveloper.studybuddy.R

class CustomCourseAdapter(private val context: Activity, arr: MutableList<Course>) :
    ArrayAdapter<Any?>(context, R.layout.course_card, arr as List<Any?>) {

    private val arr: MutableList<Course>

    init { this.arr = arr }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val inflater = context.layoutInflater

        view = inflater.inflate(R.layout.course_card, null, true)

        val textName = view.findViewById<TextView>(R.id.textName)
        val textInfo = view.findViewById<TextView>(R.id.textInfo)
        val imageCourse = view.findViewById<ImageView>(R.id.imageCourse)

        if(position == 0){
            val linear : LinearLayout = view.findViewById(R.id.linear)

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(0, 70, 0, 0)

            linear.layoutParams = layoutParams
        }
/*
        textName.text = arr[position].name()
        textInfo.text = arr[position].info()
        imageCourse.setImageResource(arr[position].image())
*/
        val animation = AnimationUtils.loadAnimation(context, R.anim.main_animation)
        view.startAnimation(animation)

        return view
    }
}