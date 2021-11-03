package com.example.coinstatsapp.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coinstatsapp.item.CoinItem
import com.example.coinstatsapp.viewHolder.ViewHolder

class AllCoinsAdapter(var context: Context, var list: MutableList<String>) : RecyclerView.Adapter<ViewHolder>() {

//    private var list: MutableList<String>? = null
//    private var context: MutableList<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = CoinItem(context)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemView = holder.itemView as CoinItem

    }

    override fun getItemCount(): Int {
        return list.size
    }
}