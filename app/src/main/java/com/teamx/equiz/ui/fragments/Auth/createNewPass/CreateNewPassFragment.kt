package com.teamx.equiz.ui.fragments.Auth.createNewPass


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import androidx.navigation.fragment.findNavController
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentCreatePasswordBinding
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException

@AndroidEntryPoint
class CreateNewPassFragment :
    BaseFragment<FragmentCreatePasswordBinding, CreateNewPassViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_create_password
    override val viewModel: Class<CreateNewPassViewModel>
        get() = CreateNewPassViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    private var uniquId: String? = null
    private var confirmPassword: String? = null
    private var password: String? = null


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


        mViewDataBinding.btnReset.setOnClickListener {
            validate()
        }

    }

    private fun initialization() {
        confirmPassword = mViewDataBinding.etCnfrmPass.text.toString().trim()
        password = mViewDataBinding.etNewPass.text.toString().trim()
    }

    private fun resetPassCall() {
        initialization()
        val bundle = arguments
        if (bundle != null) {
            uniquId = bundle.getString("newuniquId").toString()
        }


        val params = JsonObject()
        try {
            params.addProperty("uniqueID", uniquId)
            params.addProperty("password", password)
            params.addProperty("confirmPassword", confirmPassword)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        mViewModel.resetPassword(params)

        mViewModel.resetPasswordResponse.observe(requireActivity(), Observer {
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
                        findNavController().navigate(R.id.action_createNewPassFragment2_to_passChangeSuccessFragment)
                    }
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    DialogHelperClass.errorDialog(requireContext(), it.message!!)
                }
            }
        })
    }

    fun validate(): Boolean {
        if (mViewDataBinding.etNewPass.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar(getString(R.string.enter_Password))
            return false
        }
        if (mViewDataBinding.etNewPass.text.toString().trim().length < 8) {
            mViewDataBinding.root.snackbar(getString(R.string.password_8_character))
            return false
        }
        if (mViewDataBinding.etCnfrmPass.text.toString().trim().isEmpty()) {
            mViewDataBinding.root.snackbar(getString(R.string.enter_Password))
            return false
        }
        if (mViewDataBinding.etCnfrmPass.text.toString().trim().length < 7) {
            mViewDataBinding.root.snackbar(getString(R.string.password_8_character))
            return false
        }
        if (!mViewDataBinding.etNewPass.text.toString().trim()
                .equals(mViewDataBinding.etCnfrmPass.text.toString().trim())
        ) {
            mViewDataBinding.root.snackbar(getString(R.string.password_does_not_match))
            return false
        }
        resetPassCall()
        return true
    }

}