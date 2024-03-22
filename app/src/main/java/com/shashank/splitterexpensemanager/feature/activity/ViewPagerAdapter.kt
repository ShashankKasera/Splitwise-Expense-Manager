package com.shashank.splitterexpensemanager.feature.activity

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.feature.activity.allexpenses.AllExpensesFragment
import com.shashank.splitterexpensemanager.feature.activity.allrepay.AllRepayFragment

class ViewPagerAdapter(private val context: Context?, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> AllExpensesFragment()
            1 -> AllRepayFragment()
            else -> AllExpensesFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context?.getString(R.string.expenses)
            1 -> context?.getString(R.string.repay)
            else -> null
        }
    }
}
