package com.teamx.equiz.ui.fragments.language

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentLanguageBinding
import com.teamx.equiz.ui.fragments.address.AddressViewModel
import com.teamx.equiz.utils.PrefHelper
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class LanguageFragment : BaseFragment<FragmentLanguageBinding, AddressViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_language
    override val viewModel: Class<AddressViewModel>
        get() = AddressViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner


        mViewDataBinding.btnBack.setOnClickListener {
            popUpStack()
        }

        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.enter_from_left
                popExit = R.anim.exit_to_left
            }
        }

        sharedViewModel.setActiveUser("")

        if (PrefHelper.getInstance(requireContext()).langType!! == com.teamx.equiz.utils.localization.LocaleManager.LANGUAGE_ENGLISH) {
            mViewDataBinding.radioEnglish.isChecked = true
            mViewDataBinding.radioArabic.isChecked = false
        } else {
            mViewDataBinding.radioEnglish.isChecked = false
            mViewDataBinding.radioArabic.isChecked = true

        }

        mViewDataBinding.radioEnglish.setOnClickListener {
            com.teamx.equiz.utils.localization.LocaleManager(requireContext()).setNewLocale(
                requireContext(), com.teamx.equiz.utils.localization.LocaleManager.LANGUAGE_ENGLISH
            )
            PrefHelper.getInstance(requireContext())
                .saveLANGTYPE(com.teamx.equiz.utils.localization.LocaleManager.LANGUAGE_ENGLISH)
//            requireActivity().recreate()

            Handler(Looper.getMainLooper()).post {
                val intent = requireActivity().intent
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION
                )
                requireActivity().overridePendingTransition(0, 0)
                requireActivity().finish()
                requireActivity().overridePendingTransition(0, 0)
                startActivity(intent)
            }


        }

        mViewDataBinding.radioArabic.setOnClickListener {
            com.teamx.equiz.utils.localization.LocaleManager(requireContext()).setNewLocale(
                requireContext(), com.teamx.equiz.utils.localization.LocaleManager.LANGUAGE_ARABIC
            )
            PrefHelper.getInstance(requireContext())
                .saveLANGTYPE(com.teamx.equiz.utils.localization.LocaleManager.LANGUAGE_ARABIC)

            Handler(Looper.getMainLooper()).post {
                val intent = requireActivity().intent
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION
                )
                requireActivity().overridePendingTransition(0, 0)
                requireActivity().finish()
                requireActivity().overridePendingTransition(0, 0)
                startActivity(intent)
            }
        }


    }

    fun setLocale(activity: Activity, languageCode: String?) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = activity.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }


}