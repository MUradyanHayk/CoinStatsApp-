package com.example.coinstatsapp.screen

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coinstatsapp.R
import com.example.coinstatsapp.adapter.CoinsAdapter
import com.example.newprojmvvm.extensions.dp

open class AllCoinsScreen @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var notItemsText: TextView? = null
    private var progressBar: ProgressBar? = null
    var recyclerView: RecyclerView? = null
    var adapter: CoinsAdapter? = null

    init {
        setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        this.layoutParams = params
        createNoInternetText()
        createProgressBar()
    }

    fun createRecyclerView(adapter: CoinsAdapter) {
        recyclerView = RecyclerView(context)
        recyclerView?.setPadding(0, 16.dp, 0, 0)
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        recyclerView?.layoutParams = params
        recyclerView?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView?.adapter = adapter
        this.adapter = adapter
        recyclerView?.clipToPadding = false
        addView(recyclerView)
    }

    private fun createNoInternetText() {
        notItemsText = TextView(context)
        isNotItemsTextVisible(false)
        notItemsText?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        notItemsText?.setTextColor(ContextCompat.getColor(context, R.color.gray))
        notItemsText?.text = context.resources.getString(R.string.no_item_text)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.CENTER
        notItemsText?.layoutParams = params
        addView(notItemsText)
    }

    private fun createProgressBar() {
        progressBar = ProgressBar(context)
        isProgressBarVisible(false)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.CENTER
        progressBar?.layoutParams = params
        addView(progressBar)
    }

    fun isNotItemsTextVisible(isVisible: Boolean) {
        if (isVisible) {
            notItemsText?.visibility = VISIBLE
        } else {
            notItemsText?.visibility = GONE
        }
    }

    fun isProgressBarVisible(isVisible: Boolean) {
        if (isVisible) {
            progressBar?.visibility = VISIBLE
        } else {
            progressBar?.visibility = GONE
        }
    }
}