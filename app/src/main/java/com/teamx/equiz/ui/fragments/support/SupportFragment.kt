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
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentReferralBinding
import com.teamx.equiz.databinding.FragmentSubscriptionBinding
import com.teamx.equiz.databinding.FragmentSupportBinding
import com.teamx.equiz.ui.fragments.Auth.login.LoginViewModel
import com.teamx.equiz.ui.fragments.referral.ReferralViewModel
import com.teamx.equiz.ui.fragments.subscription.SubscriptionViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController

@AndroidEntryPoint
class SupportFragment : BaseFragment<FragmentSupportBinding, SubscriptionViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_support
    override val viewModel: Class<SubscriptionViewModel>
        get() = SubscriptionViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions


    @SuppressLint("SetJavaScriptEnabled")
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

        mViewDataBinding.textView38.setOnClickListener {

            navController = Navigation.findNavController(
                requireActivity(), R.id.nav_host_fragment
            )
            navController.navigate(
                R.id.chattFragment, null, options
            )



// /*           val webView: WebView = mViewDataBinding.webView
//            webView.settings.javaScriptEnabled = true
//            webView.webViewClient = WebViewClient()
//
//            webView.webViewClient = object : WebViewClient() {
//                override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
//                    Log.e("WebView Error", error?.description.toString())
//                }
//            }
//*/
//
//            val webView =  mViewDataBinding.webView
//
//
//            webView.settings.javaScriptEnabled = true
//            webView.settings.domStorageEnabled = true // Tawk.to uses local storage
//
//// Load Tawk.to chat widget URL
//            webView.loadUrl("https://tawk.to/chat/652946c76fcfe87d54b97f12/1hckjove5")
//
//
//
//        /*    val widgetCode = "https://tawk.to/chat/652946c76fcfe87d54b97f12/1hckjove5" // Replace with your widget code
//            val htmlData = "<script>1hckjove5</script>"
//            webView.loadData(htmlData, "text/html", "UTF-8")*/

        }


    }
}