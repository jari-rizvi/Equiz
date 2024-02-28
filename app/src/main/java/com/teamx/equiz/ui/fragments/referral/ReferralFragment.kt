package com.teamx.equiz.ui.fragments.referral

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.material.color.utilities.MaterialDynamicColors.background
import com.teamx.equiz.BR
import com.teamx.equiz.MainApplication
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentReferralBinding
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.localization.LocaleManager
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReferralFragment : BaseFragment<FragmentReferralBinding, ReferralViewModel>(),
    DialogHelperClass.Companion.DialogInviteAnotherCallBack {

    override val layoutId: Int
        get() = R.layout.fragment_referral
    override val viewModel: Class<ReferralViewModel>
        get() = ReferralViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions


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
        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }




        mViewDataBinding.btnInvite.setOnClickListener {

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "I found this cool app called E-Quiz and thought you'd like it too. \uD83D\uDE0A\n" +
                            "\n" +
                            "Use my code REF_546799AL when you sign up, and we both get [a bonus/discount/etc.]!\n" +
                            "\n" +
                            "1. Download E-Quiz.\n" +
                            "\n" +
                            "2. Sign up with code: REF_546799AL.\n" +
                            "\n" +
                            "Enjoy! \uD83D\uDE80"
                )
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)


//
//            DialogHelperClass.InviteDialog(
//                requireContext(), this, true
//            )
            //
        }

        mViewDataBinding.textView8.setOnClickListener {
            onClickCoupon(mViewDataBinding.code.text.toString())
            if (isAdded) {
                mViewDataBinding.root.snackbar("Copied")
            }
        }

        var bundle = arguments

        if (bundle != null) {
            val rCode = bundle.getString("referralCode")
            mViewDataBinding.code.text = rCode.toString()
        }

    }

    override fun InviteClicked() {
        val dialog = Dialog(requireContext())
        dialog.dismiss()
    }

    private fun onClickCoupon(str: String) {
        val clipboard: ClipboardManager? =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("io", "$str")
        clipboard!!.setPrimaryClip(clip)
    }
}
