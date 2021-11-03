package com.example.coinstatsapp.screen

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coinstatsapp.R
import com.example.coinstatsapp.adapter.AllCoinsAdapter

class FavoriteCoinsScreen @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var recyclerView: RecyclerView? = null
    private var adapter: AllCoinsAdapter? = null

    init {
        setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        this.layoutParams = params
        createRecyclerView()
    }

    private fun createRecyclerView() {
        recyclerView = RecyclerView(context)
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        recyclerView?.layoutParams = params
        recyclerView?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView?.adapter = adapter
        addView(recyclerView)
    }
}