package com.teamx.equiz.ui.fragments.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.gson.JsonObject
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentEditProfileBinding
import com.teamx.equiz.ui.fragments.Auth.login.LoginViewModel
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding, EditProfileViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_edit_profile
    override val viewModel: Class<EditProfileViewModel>
        get() = EditProfileViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private var fName: String? = null
    private var lName: String? = null

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

        mViewDataBinding.btnSave.setOnClickListener {
            ApiCall()
        }


    }

    private fun initialization() {
        fName = mViewDataBinding.fName.text.toString().trim()
        lName = mViewDataBinding.lName.text.toString().trim()
    }

    fun ApiCall() {

        initialization()
        val params = JsonObject()
        try {
            params.addProperty("fName", fName)
            params.addProperty("lName", lName)
        } catch (e: JSONException) {
            e.printStackTrace()
        }


        mViewModel.editProfile(params)

        if (!mViewModel.editProfileResponse.hasActiveObservers()) {
            mViewModel.editProfileResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()

                        it.data?.let { data ->

                            showToast("Updated")


                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }
                }
                if (isAdded) {
                    mViewModel.editProfileResponse.removeObservers(viewLifecycleOwner)
                }
            }
        }

    }


}
