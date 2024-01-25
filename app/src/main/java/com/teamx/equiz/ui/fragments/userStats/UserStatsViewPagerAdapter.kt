package com.teamx.equiz.ui.fragments.userStats

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.teamx.equiz.ui.fragments.loaderboard.LoaderBoardFragment
import com.teamx.equiz.ui.fragments.userprogress.UserProgressFragment
import com.teamx.equiz.ui.game_fragments.game_random.RandomGameFragment

class UserStatsViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private var totalCount: Int,
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return totalCount
    }

    override fun createFragment(position: Int): Fragment {


        return when (position) {
            0 -> UserProgressFragment()
            1 -> LoaderBoardFragment()
            2 -> UserProgressFragment()
//            3 -> GamesFragment()
            3 -> RandomGameFragment()
            else -> UserProgressFragment()
        }
    }

}