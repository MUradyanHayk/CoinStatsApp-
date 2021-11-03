package com.example.coinstatsapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.coinstatsapp.R
import com.example.coinstatsapp.screen.AllCoinsScreen
import com.example.coinstatsapp.screen.FavoriteCoinsScreen
import com.example.coinstatsapp.viewModel.AllCoinsViewModel
import com.example.coinstatsapp.viewModel.FavoriteCoinsViewModel

class FavoriteCoinsFragment : Fragment() {
    private var viewModel: AllCoinsViewModel? = null
    private var screen: FavoriteCoinsScreen? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        screen = FavoriteCoinsScreen(requireContext())
        return screen
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AllCoinsViewModel::class.java)
//        viewModel?.configure(context)
    }
}