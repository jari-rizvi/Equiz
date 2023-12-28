package com.teamx.equiz.ui.fragments.referral

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentReferralBinding
import com.teamx.equiz.ui.fragments.Auth.login.LoginViewModel
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.addCallback
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
            DialogHelperClass.InviteDialog(
                requireContext(), this, true
            )
        }

        mViewDataBinding.textView8.setOnClickListener {
            onClickCoupon(mViewDataBinding.code.text.toString())
        }

    }

    override fun InviteClicked() {
        val dialog = Dialog(requireContext())
        dialog.dismiss()
    }

    fun onClickCoupon(str: String) {
        val clipboard: ClipboardManager? =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("io", "$str")
        clipboard!!.setPrimaryClip(clip)
    }
}
