package com.example.coinstatsapp.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coinstatsapp.item.CoinItem
import com.example.coinstatsapp.model.CoinModel
import com.example.coinstatsapp.viewHolder.ViewHolder
import io.realm.RealmChangeListener
import io.realm.RealmResults
import java.lang.ref.WeakReference

interface AllCoinsAdapterDelegate {
    fun onFavoriteItemClick(id: String)
}

class CoinsAdapter(
    var context: Context,
    var isFromFavoriteScreen: Boolean,
    var delegate: WeakReference<AllCoinsAdapterDelegate>?,
    var list: RealmResults<CoinModel>?
) :
    RecyclerView.Adapter<ViewHolder>(),
    RealmChangeListener<RealmResults<CoinModel>> {

    init {
        this.list?.addChangeListener(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = CoinItem(context)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemView = holder.itemView as CoinItem
        list?.get(position)?.let {
            itemView.configure(it, isFromFavoriteScreen)
        }
        if (!isFromFavoriteScreen) {
            itemView.setOnClickListener {
                val id = list?.get(position)?.id ?: ""
                delegate?.get()?.onFavoriteItemClick(id)
            }
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onChange(t: RealmResults<CoinModel>) {
        notifyDataSetChanged()
    }
}