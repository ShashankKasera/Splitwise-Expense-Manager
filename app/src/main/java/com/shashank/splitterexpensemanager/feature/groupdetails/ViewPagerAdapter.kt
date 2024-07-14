package com.shashank.splitterexpensemanager.feature.groupdetails

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.shashank.splitterexpensemanager.R
import com.shashank.splitterexpensemanager.feature.groupdetails.expenses.ExpensesFragment
import com.shashank.splitterexpensemanager.feature.groupdetails.repay.RepayFragment

class ViewPagerAdapter(private val context: GroupDetailsActivity, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ExpensesFragment()
            1 -> RepayFragment()
            else -> ExpensesFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.expenses)
            1 -> context.getString(R.string.repay)
            else -> null
        }
    }
}
