package com.example.coinstatsapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.coinstatsapp.adapter.AllCoinsAdapter
import com.example.coinstatsapp.model.CoinModel
import com.example.coinstatsapp.screen.AllCoinsScreen
import com.example.coinstatsapp.viewModel.AllCoinsViewModel

class AllCoinsFragment : Fragment() {
    private var viewModel: AllCoinsViewModel? = null
    private var screen: AllCoinsScreen? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        screen = AllCoinsScreen(requireContext())
        return screen
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AllCoinsViewModel::class.java)
        viewModel?.configure(requireContext())
        screen?.createRecyclerView(AllCoinsAdapter(requireContext(), viewModel?.realmDB?.where(CoinModel::class.java)?.findAll()))
    }
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        viewModel = ViewModelProvider(this).get(AllCoinsViewModel::class.java)
//        viewModel?.configure(context)
//        screen?.createRecyclerView()
//    }
}