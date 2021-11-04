package com.example.coinstatsapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coinstatsapp.R
import com.example.coinstatsapp.adapter.CoinsAdapter
import com.example.coinstatsapp.adapter.AllCoinsAdapterDelegate
import com.example.coinstatsapp.data.CoinData
import com.example.coinstatsapp.screen.AllCoinsScreen
import com.example.coinstatsapp.viewModel.CoinViewModel
import java.lang.ref.WeakReference

class AllCoinsFragment : Fragment(), AllCoinsAdapterDelegate {
    private var viewModel: CoinViewModel? = null
    private var screen: AllCoinsScreen? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        screen = AllCoinsScreen(requireContext())
        return screen
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CoinViewModel::class.java)
        viewModel?.initModel(requireContext())
        viewModel?.configureData(requireContext())
        screen?.createRecyclerView(CoinsAdapter(requireContext(), false, WeakReference(this), viewModel?.getAllItemList()))

        screen?.recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isLastItemDisplayed(recyclerView)) {
                    viewModel?.parseJson(requireContext())
                }
            }
        })

        viewModel?.hasInternetConnection?.observe(viewLifecycleOwner, {
//            screen?.isNotItemsTextVisible(!it)
            if (!it) {
                Toast.makeText(requireContext(), resources.getString(R.string.no_internet_connection_text), Toast.LENGTH_SHORT).show()
            }
        })

        viewModel?.isLoading?.observe(viewLifecycleOwner, {
            screen?.isProgressBarVisible(it)
        })

        viewModel?.isListEmpty?.observe(viewLifecycleOwner, {
            screen?.isNotItemsTextVisible(it)
        })
    }

    private fun isLastItemDisplayed(recyclerView: RecyclerView): Boolean {
        val adapter = recyclerView.adapter ?: return false
        if (adapter.itemCount == 0) {
            return false
        }

        val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)?.findLastCompletelyVisibleItemPosition()

        if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == adapter.itemCount - 1) {
            return true
        }

        return false
    }

    override fun onFavoriteItemClick(id: String) {
        if (id.isEmpty()) {
            return
        }
        viewModel?.changeFavorite(id)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.closeDB()
    }
}