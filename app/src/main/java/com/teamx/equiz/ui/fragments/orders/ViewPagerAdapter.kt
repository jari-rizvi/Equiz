package com.teamx.equiz.ui.fragments.orders

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.teamx.equiz.ui.fragments.orders.cancelled.CancelledFragment
import com.teamx.equiz.ui.fragments.orders.delivered.DeliveredFragment
import com.teamx.equiz.ui.fragments.orders.processing.ProcessingFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity, private var totalCount: Int) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return totalCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DeliveredFragment()
            1 -> ProcessingFragment()
            2 -> CancelledFragment()
            else -> CancelledFragment()
        }
    }

}