package com.example.coinstatsapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.coinstatsapp.adapter.CoinsAdapter
import com.example.coinstatsapp.data.CoinData
import com.example.coinstatsapp.screen.FavoriteCoinsScreen
import com.example.coinstatsapp.viewModel.CoinViewModel

class FavoriteCoinsFragment : Fragment() {
    private var viewModel: CoinViewModel? = null
    private var screen: FavoriteCoinsScreen? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        screen = FavoriteCoinsScreen(requireContext())
        return screen
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CoinViewModel::class.java)
        viewModel?.initModel(requireContext())
        screen?.createRecyclerView(
            CoinsAdapter(
                requireContext(),
                true,
                null,
                viewModel?.getFavoriteList()
            )
        )
        viewModel?.isListEmpty?.observe(viewLifecycleOwner, {
            screen?.isNotItemsTextVisible(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.closeDB()
    }
}