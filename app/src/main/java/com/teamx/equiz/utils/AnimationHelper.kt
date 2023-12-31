package com.teamx.equiz.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.util.Property
import android.view.View

fun scaleUp(animateView: View, scaleXValue: Float, scaleYValue: Float, animationDuration: Long) {
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, scaleXValue)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, scaleYValue)
    val scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(animateView, scaleX, scaleY)
    scaleAnimator.repeatCount = 1
    scaleAnimator.repeatMode = ObjectAnimator.REVERSE
    scaleAnimator.duration = animationDuration
    scaleAnimator.start()
}

public fun rotater(animateView: View, angle: Float, stopAngle: Float, animationDuration: Long) {
    val animator = ObjectAnimator.ofFloat(animateView, View.ROTATION, angle, stopAngle)
    animator.duration = animationDuration
    animator.start()
}

public fun fade(animateView: View, fadeValue: Float, animationDuration: Long) {
    val animator = ObjectAnimator.ofFloat(animateView, View.ALPHA, fadeValue)
    animator.duration = animationDuration
    animator.repeatCount = 1
    animator.repeatMode = ObjectAnimator.REVERSE
    animator.start()
}

public fun translater(
    animateView: View, translator: Property<View, Float>,mode:Int,
    distance:Float,
    animationDuration: Long) {
    val animator = ObjectAnimator.ofFloat(animateView, translator, distance)
    animator.repeatCount = 1
    animator.repeatMode = mode
    animator.duration = animationDuration
    animator.start()
}


