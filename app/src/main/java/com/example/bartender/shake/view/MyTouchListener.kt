package com.example.bartender.shake.view

import android.content.ClipData
import android.view.MotionEvent
import android.view.View

//Unfinished implementation of drag and drop to the shaker
class MyTouchListener : View.OnTouchListener {

    override fun onTouch(v: View, event: MotionEvent): Boolean {

        return if (event.action == MotionEvent.ACTION_DOWN) {
            val data: ClipData = ClipData.newPlainText("", "")
            val shadowBuilder: View.DragShadowBuilder = View.DragShadowBuilder(v)
            v.startDrag(data, shadowBuilder, v, 0)
            v.visibility = View.INVISIBLE;
            true
        } else {
            false
        }
    }
}