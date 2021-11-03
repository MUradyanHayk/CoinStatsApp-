package com.example.newprojmvvm.extensions

import android.content.res.Resources

val Int.dp
    get() = this * Resources.getSystem().displayMetrics.density.toInt()

val Float.dp
    get() = this * Resources.getSystem().displayMetrics.density.toFloat()

val Long.dp
    get() = this * Resources.getSystem().displayMetrics.density.toLong()

val Double.dp
    get() = this * Resources.getSystem().displayMetrics.density.toDouble()

