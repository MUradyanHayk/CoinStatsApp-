package com.example.coinstatsapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
        screen?.createRecyclerView(CoinsAdapter(requireContext(), false, WeakReference(this), viewModel?.realmDB?.where(CoinData::class.java)?.findAll()))

        viewModel?.hasInternetConnection?.observe(viewLifecycleOwner, {
            screen?.isNoInternetVisible(!it)
        })
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