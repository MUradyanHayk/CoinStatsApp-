package com.example.coinstatsapp.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object MainApplicationUtils {
    var applicationContext: Context? = null

    val executor: ExecutorService by lazy {
        Executors.newSingleThreadExecutor()
    }
    val handler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }
}