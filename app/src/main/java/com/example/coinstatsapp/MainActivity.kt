package com.example.coinstatsapp

import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.coinstatsapp.adapter.FragmentAdapter
import com.example.coinstatsapp.viewModel.CoinViewModel
import com.google.android.material.tabs.TabLayout
import io.realm.Realm

class MainActivity : AppCompatActivity() {
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager2? = null
    private var fragmentAdapter: FragmentAdapter? = null
    var viewModel: CoinViewModel? = null
    var realmDB: Realm? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        Realm.init(this)
        realmDB = Realm.getDefaultInstance()
        viewModel = ViewModelProvider(this).get(CoinViewModel::class.java)
        viewModel?.initModel(this, realmDB)
        viewModel?.configureData(this)
    }

    private fun initViews() {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.blue)))
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue)

        tabLayout = findViewById(R.id.tab_layout_id)
        viewPager = findViewById(R.id.view_pager_id)
        fragmentAdapter = FragmentAdapter(supportFragmentManager, lifecycle)
        viewPager?.adapter = fragmentAdapter

        tabLayout?.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.blue))
        tabLayout?.setTabTextColors(ContextCompat.getColor(this, R.color.gray), ContextCompat.getColor(this, R.color.blue))

        tabLayout?.addTab(tabLayout!!.newTab().setText(resources.getString(R.string.all_coins_title_text)))
        tabLayout?.addTab(tabLayout!!.newTab().setText(resources.getString(R.string.favorite_coins_title_text)))

        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab == null) {
                    return
                }
                viewPager?.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        viewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout?.selectTab(tabLayout?.getTabAt(position))
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        realmDB?.close()
    }
}