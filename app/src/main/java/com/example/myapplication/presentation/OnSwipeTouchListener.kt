package com.example.myapplication.presentation

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import kotlin.math.abs


open class OnSwipeTouchListener(ctx: Context?) : OnTouchListener {
    private val gestureDetector: GestureDetector

    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    init {
        gestureDetector = GestureDetector(ctx, GestureListener())
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        view?.performClick()
        return gestureDetector.onTouchEvent(event!!)
    }

    private inner class GestureListener : SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            touchEvent: MotionEvent?,
            detouchEvent: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var isSuccess = false
            try {

                val diffY = detouchEvent.y - touchEvent!!.y
                val diffX = detouchEvent.x - touchEvent.x

                val isHorizontalSwipe = abs(diffX) > abs(diffY)

                val isValidVerticalSwipe = !isHorizontalSwipe &&
                    abs(diffX) > Companion.SWIPE_THRESHOLD && abs(velocityX) > Companion.SWIPE_VELOCITY_THRESHOLD

                val isValidHorizontalSwipe =
                    isHorizontalSwipe && abs(diffX) > Companion.SWIPE_THRESHOLD && abs(
                        velocityX
                    ) > Companion.SWIPE_VELOCITY_THRESHOLD



                if (isValidHorizontalSwipe) {
                    if (diffX > 0)
                        onSwipeRight()
                    else
                        onSwipeLeft()

                    isSuccess = true

                } else if (isValidVerticalSwipe) {

                    if (diffY > 0) {
                        onSwipeBottom()
                    } else {
                        onSwipeTop()
                    }

                    isSuccess = true

                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return isSuccess
        }
    }

    open fun onSwipeRight() {}
    open fun onSwipeLeft() {}
    open fun onSwipeTop() {}
    open fun onSwipeBottom() {}
}