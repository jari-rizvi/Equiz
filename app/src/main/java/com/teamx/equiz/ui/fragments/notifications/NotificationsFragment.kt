package com.teamx.equiz.ui.fragments.notifications


import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Switch
import android.widget.TextView
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
import androidx.appcompat.widget.SwitchCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.gson.JsonObject
import org.json.JSONException

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

        sharedViewModel.setActiveUser("")

        mViewDataBinding.btnback.setOnClickListener {
            popUpStack()
        }

        mViewDataBinding.btnSetting.setOnClickListener {
            NotificationDialog().show()
            mViewModel.getNotificationSetting()
            if (!mViewModel.getNotificationSettingResponse.hasActiveObservers()) {
                mViewModel.getNotificationSettingResponse.observe(requireActivity()) {
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

                                emailSwitch.isChecked = data.data.email
                                smsSwitch.isChecked = data.data.sms
                                pushSwitch.isChecked = data.data.push


                            }
                        }

                        Resource.Status.AUTH -> {
                            loadingDialog.dismiss()
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
                        mViewModel.getNotificationSettingResponse.removeObservers(
                            viewLifecycleOwner
                        )
                    }
                }
            }
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

                    Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
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

    }


    private fun notificationRecyclerview() {
        notificationArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recNoti.layoutManager = linearLayoutManager

        notificationAdapter = NotificationAdapter(notificationArrayList)
        mViewDataBinding.recNoti.adapter = notificationAdapter

    }

    lateinit var emailSwitch: SwitchCompat
    lateinit var pushSwitch: SwitchCompat
    lateinit var smsSwitch: SwitchCompat


    var email: Boolean = false
    var sms: Boolean = false
    var push: Boolean = false


    fun NotificationDialog(): Dialog {
        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.notification_dialog)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.setCancelable(false)

        emailSwitch = dialog.findViewById(R.id.switchEmail)
        pushSwitch = dialog.findViewById(R.id.switchPush)
        smsSwitch = dialog.findViewById(R.id.switchSms)
        val btn = dialog.findViewById<TextView>(R.id.btnSave)


        /* if (emailSwitch.isChecked) {
             email = true
         }

         if (pushSwitch.isChecked) {
             push = true
         }

         if (smsSwitch.isChecked) {
             sms = true
         }*/


        btn.setOnClickListener {

            val params = JsonObject()
            try {
                params.addProperty("sms", smsSwitch.isChecked)
                params.addProperty("push", pushSwitch.isChecked)
                params.addProperty("email", emailSwitch.isChecked)
            } catch (e: JSONException) {
                e.printStackTrace()

            }

            mViewModel.updateNotificationSetting(params)
            if (!mViewModel.updateNotificationSettingResponse.hasActiveObservers()) {
                mViewModel.updateNotificationSettingResponse.observe(requireActivity()) {
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
                                email = data.data.email
                                push = data.data.push
                                sms = data.data.sms

                                dialog.dismiss()


                            }
                        }

                        Resource.Status.AUTH -> {
                            loadingDialog.dismiss()
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
                        mViewModel.updateNotificationSettingResponse.removeObservers(
                            viewLifecycleOwner
                        )
                    }
                }
            }
            dialog.dismiss()
        }




        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }


}