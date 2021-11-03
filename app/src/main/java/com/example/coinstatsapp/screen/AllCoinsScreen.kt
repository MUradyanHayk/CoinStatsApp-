package com.example.coinstatsapp.screen

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coinstatsapp.adapter.AllCoinsAdapter

class AllCoinsScreen @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var recyclerView: RecyclerView? = null
    var adapter: AllCoinsAdapter? = null
//    set(value) {
//        field = value
//        recyclerView?.adapter = field
//        field?.notifyDataSetChanged()
//    }

    init {
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        this.layoutParams = params
//        createRecyclerView()
    }

    fun createRecyclerView(adapter:AllCoinsAdapter) {
        recyclerView = RecyclerView(context)
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        recyclerView?.layoutParams = params
        recyclerView?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView?.adapter = adapter
        this.adapter = adapter
        addView(recyclerView)
    }
}