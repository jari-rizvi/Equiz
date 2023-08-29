package com.teamx.equiz.ui.fragments.notifications

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.teamx.equiz.ui.fragments.notifications.all.AllNotificationsFragment
import com.teamx.equiz.ui.fragments.notifications.read.ReadNotificationsFragment
import com.teamx.equiz.ui.fragments.notifications.unread.UnReadNotificationsFragment
import com.teamx.equiz.ui.fragments.orders.cancelled.CancelledFragment
import com.teamx.equiz.ui.fragments.orders.delivered.DeliveredFragment
import com.teamx.equiz.ui.fragments.orders.processing.ProcessingFragment

class ViewPagerNotificationAdapter(
    fragmentActivity: FragmentActivity,
    private var totalCount: Int
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return totalCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllNotificationsFragment()
            1 -> ReadNotificationsFragment()
            2 -> UnReadNotificationsFragment()
            else -> AllNotificationsFragment()
        }
    }

}