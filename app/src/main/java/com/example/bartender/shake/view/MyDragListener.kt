package com.example.bartender.shake.view

import android.view.DragEvent
import android.view.View
import android.view.View.OnDragListener
import android.view.ViewGroup
import android.widget.LinearLayout

//Unfinished implementation of drag and drop to the shaker
class MyDragListener(private val target : LinearLayout) : OnDragListener {

    override fun onDrag(v: View, event: DragEvent): Boolean {
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
            }
            DragEvent.ACTION_DRAG_EXITED -> {
            }
            DragEvent.ACTION_DROP -> {
                // Dropped, reassign View to ViewGroup
                val view: View = event.localState as View
                val owner = view.parent as ViewGroup
                owner.removeView(view)
                target.addView(view)
                view.visibility = View.VISIBLE
            }
            DragEvent.ACTION_DRAG_ENDED -> { }
            else -> { }
        }
        return true
    }
}