package com.example.coinstatsapp.screen

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coinstatsapp.R
import com.example.coinstatsapp.adapter.AllCoinsAdapter

open class AllCoinsScreen @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var recyclerView: RecyclerView? = null
    private var noInternetText: TextView? = null
    var adapter: AllCoinsAdapter? = null

    init {
        setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        this.layoutParams = params
        createNoInternetText()
    }

    fun createRecyclerView(adapter: AllCoinsAdapter) {
        recyclerView = RecyclerView(context)
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        recyclerView?.layoutParams = params
        recyclerView?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView?.adapter = adapter
        this.adapter = adapter
        addView(recyclerView)
    }

    fun createNoInternetText() {
        noInternetText = TextView(context)
        isNoInternetVisible(false)
        noInternetText?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
        noInternetText?.setTextColor(ContextCompat.getColor(context, R.color.gray))
        noInternetText?.text = context.resources.getString(R.string.no_internet_connection_text)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.CENTER
        noInternetText?.layoutParams = params
        addView(noInternetText)
    }

    fun isNoInternetVisible(isVisible: Boolean) {
        if (isVisible) {
            noInternetText?.visibility = VISIBLE
            recyclerView?.visibility = GONE
        } else {
            noInternetText?.visibility = GONE
            recyclerView?.visibility = VISIBLE
        }
    }
}