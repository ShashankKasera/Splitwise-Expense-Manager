package com.shashank.splitterexpensemanager.feature.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.core.SharedPref
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ActivityFragment : Fragment() {
    @Inject
    lateinit var sharedPref: SharedPref
    lateinit var recyclerView: RecyclerView
    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout
    lateinit var toolbar: Toolbar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_activity, container, false)

        init(v)
        setUpRecyclerView()
        return v
    }

    private fun init(v: View) {
        viewPager = v.findViewById(R.id.vp_activity)
        tabLayout = v.findViewById(R.id.tl_activity)
        toolbar = v.findViewById(R.id.tb_activity)

        toolbar.setTitle(getString(R.string.dashboard))
    }

    private fun setUpRecyclerView() {
        val adapter = ViewPagerAdapter(context, childFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}