package com.example.coinstatsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.coinstatsapp.adapter.FragmentAdapter
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager2? = null
    private var fragmentAdapter: FragmentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        tabLayout = findViewById(R.id.tab_layout_id)
        viewPager = findViewById(R.id.view_pager_id)
        fragmentAdapter = FragmentAdapter(supportFragmentManager, lifecycle)
        viewPager?.adapter = fragmentAdapter

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
}