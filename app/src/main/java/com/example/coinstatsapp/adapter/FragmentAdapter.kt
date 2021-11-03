package com.example.coinstatsapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.coinstatsapp.fragment.AllCoinsFragment
import com.example.coinstatsapp.fragment.FavoriteCoinsFragment

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2

    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllCoinsFragment()
            1 -> FavoriteCoinsFragment()
            else -> AllCoinsFragment()
        }
    }

}