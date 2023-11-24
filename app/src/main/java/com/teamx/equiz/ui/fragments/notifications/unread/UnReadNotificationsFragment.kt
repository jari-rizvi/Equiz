package com.teamx.equiz.ui.fragments.notifications.unread

import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.R
import com.teamx.equiz.BR
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentCanclledBinding
import com.teamx.equiz.ui.fragments.notifications.NotificationAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UnReadNotificationsFragment : BaseFragment<FragmentCanclledBinding, UnReadNotificaitonsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_canclled
    override val viewModel: Class<UnReadNotificaitonsViewModel>
        get() = UnReadNotificaitonsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions

    lateinit var productAdapter: NotificationAdapter

    lateinit var productArrayList: ArrayList<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }

        productRecyclerview()

        productArrayList.add("")
        productArrayList.add("")
        productArrayList.add("")
        productArrayList.add("")

        productAdapter.notifyDataSetChanged()

    }


    private fun productRecyclerview() {
        productArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.cancelledRecyclerView.layoutManager = linearLayoutManager

        productAdapter = NotificationAdapter(productArrayList)
        mViewDataBinding.cancelledRecyclerView.adapter = productAdapter

    }


}