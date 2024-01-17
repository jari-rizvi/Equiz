package com.teamx.equiz.ui.fragments.notifications


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.coupons.Data
import com.teamx.equiz.data.models.notificationData.NewNotification
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentNotificationsBinding
import com.teamx.equiz.databinding.FragmentOrdersBinding
import com.teamx.equiz.databinding.FragmentProfileBinding
import com.teamx.equiz.ui.fragments.Auth.login.LoginViewModel
import com.teamx.equiz.ui.fragments.coupons.CouponsAdapter
import com.teamx.equiz.ui.fragments.orders.ViewPagerAdapter
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController

@AndroidEntryPoint
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding, NotificaitonsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_notifications
    override val viewModel: Class<NotificaitonsViewModel>
        get() = NotificaitonsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    lateinit var notificationAdapter: NotificationAdapter
    lateinit var notificationArrayList: ArrayList<NewNotification>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }

        mViewDataBinding.btnback.setOnClickListener {
            popUpStack()
        }

        mViewModel.getNotifications()

        if (!mViewModel.getNotificationsResponse.hasActiveObservers()) {
            mViewModel.getNotificationsResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }
                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }
                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            /*    data.data.forEach {
                                    couponsArrayList.add(it)
                                }*/

                            data.newNotification.forEach {
                                if (it != null) {
                                    notificationArrayList.add(it)
                                }
                            }

                            notificationAdapter.notifyDataSetChanged()


                        }
                    }
                    Resource.Status.AUTH -> { loadingDialog.dismiss()
                         if (isAdded) {
                            try {
                                onToSignUpPage()
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        DialogHelperClass.errorDialog(
                            requireContext(),
                            it.message!!
                        )
                    }
                }
                if (isAdded) {
                    mViewModel.getNotificationsResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }

        notificationRecyclerview()

        /*        setupViewPager()
                setupTabLayout()*/

    }


    private fun notificationRecyclerview() {
        notificationArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recNoti.layoutManager = linearLayoutManager

        notificationAdapter = NotificationAdapter(notificationArrayList)
        mViewDataBinding.recNoti.adapter = notificationAdapter

    }


    /* private fun setupViewPager() {
         val adapter = ViewPagerAdapter(requireActivity(), 3)
         mViewDataBinding.viewPagerNotification.adapter = adapter
     }

     private fun setupTabLayout() {
         TabLayoutMediator(
             mViewDataBinding.tabLayout, mViewDataBinding.viewPagerNotification
         ) { tab, position ->

             when (position) {
                 0 -> {

                     tab.text = "All"
                 }

                 1 -> {
                     tab.text = "Unread"
                 }

                 2 -> {
                     tab.text = "Read"
                 }
             }


         }.attach()
     }*/
}