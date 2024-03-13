package com.teamx.equiz.ui.fragments.support


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentChatBinding
import com.teamx.equiz.databinding.FragmentReferralBinding
import com.teamx.equiz.databinding.FragmentSubscriptionBinding
import com.teamx.equiz.databinding.FragmentSupportBinding
import com.teamx.equiz.ui.fragments.Auth.login.LoginViewModel
import com.teamx.equiz.ui.fragments.referral.ReferralViewModel
import com.teamx.equiz.ui.fragments.subscription.SubscriptionViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.addCallback
@AndroidEntryPoint
class ChattFragment : BaseFragment<FragmentChatBinding, SubscriptionViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_chat
    override val viewModel: Class<SubscriptionViewModel>
        get() = SubscriptionViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.dashboardFragment)
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

            val webView =  mViewDataBinding.webView


            webView.settings.javaScriptEnabled = true
            webView.settings.domStorageEnabled = true // Tawk.to uses local storage

// Load Tawk.to chat widget URL
            webView.loadUrl("https://tawk.to/chat/659eb44f8d261e1b5f519728/1hjpv0mb5")



        /*    val widgetCode = "https://tawk.to/chat/652946c76fcfe87d54b97f12/1hckjove5" // Replace with your widget code
            val htmlData = "<script>1hckjove5</script>"
            webView.loadData(htmlData, "text/html", "UTF-8")*/




    }
}