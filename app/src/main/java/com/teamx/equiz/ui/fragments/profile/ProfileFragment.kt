package com.teamx.equiz.ui.fragments.profile


import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.widget.SwitchCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.bumptech.glide.Glide
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentProfileBinding
import com.teamx.equiz.ui.activity.mainActivity.MainActivity.Companion.isEnable
import com.teamx.equiz.ui.fragments.Auth.login.LoginViewModel
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.PrefHelper
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, LoginViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_profile
    override val viewModel: Class<LoginViewModel>
        get() = LoginViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions
    var useremaill = ""
    var userphonee = ""
    var userpasswordd = ""

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

        mViewDataBinding.btnTouchId.setOnClickListener {
            TouchIdDialog().show()

        }


        var prefUser = PrefHelper.getUSerInstance(requireContext()).getCredentials()
        if (prefUser == null) {
            prefUser = PrefHelper.getUSerInstance(requireContext()).getCredentials()
        }
        prefUser?.let {

            useremaill = it.email.toString()
            userphonee = it.phone.toString()
            userpasswordd = it.Password
            isEnable = it.isDetection

        }


        val bundle = arguments

        val strRank = bundle?.getString("rankUser", "")

        mViewDataBinding.textView50.setText(strRank)

        mViewModel.me()
        if (!mViewModel.meResponse.hasActiveObservers()) {
            mViewModel.meResponse.observe(requireActivity()) {
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

                            try {
                                mViewDataBinding.textView3.text = data.user.name
                                mViewDataBinding.textView4.text = data.user.email
                                mViewDataBinding.textView4.setText(data.user.phone)
                                mViewDataBinding.textView52.setText(data.user.chances.toString())


//                                mViewDataBinding.textView51.setText(data.user.wallet.toString())
                                val formattedNumber = String.format("%.2f", data.user.wallet)
                                mViewDataBinding.textView51.text = formattedNumber + " Points"

                                Log.d("TAG", "onViewCreated121212: ${data.user.isPremium}")
                                Log.d("TAG", "onViewCreated121212: ${data.user.wallet}")

                                if (data.user.isPremium) {
                                    Log.d("TAG", "onViewCreated121212: ${data.user.isPremium}")
                                    mViewDataBinding.premium.visibility = View.VISIBLE

                                }

                                if (!data.user.image.isNullOrEmpty()) {
//                                    Picasso.get().load(data.user.image).resize(500, 500)
//                                        .into(mViewDataBinding.profilePicture)
                                    Glide.with(mViewDataBinding.profilePicture.context)
                                        .load(data.user.image)
                                        .placeholder(R.drawable.baseline_person_white)
                                        .into(mViewDataBinding.profilePicture)
                                }

                                if (data.user.isPremium) {
                                    mViewDataBinding.btnUnSubscribe.visibility = View.VISIBLE
                                } else {
                                    mViewDataBinding.btnUnSubscribe.visibility = View.GONE

                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
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
                        if (isAdded) {
                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                        Log.d("TAG", "eeeeeeeeeee: ${it.message}")
                    }
                }
            }
        }


//        if (!mViewModel.unsubResponse.hasActiveObservers()) {
//            mViewModel.unsubResponse.observe(requireActivity()) {
//                when (it.status) {
//                    Resource.Status.LOADING -> {
//                        loadingDialog.show()
//                    }
//
//                    Resource.Status.NOTVERIFY -> {
//                        loadingDialog.dismiss()
//                    }
//
//                    Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
//                        it.data?.let { data ->
//
//                            try {
////                                mViewDataBinding.root.snackbar(data)
//                                mViewDataBinding.root.snackbar("Subcription will end at the end of the Month")
//
//
//                            } catch (e: Exception) {
//                                e.printStackTrace()
//                            }
//                        }
//                    }
//
//                    Resource.Status.AUTH -> {
//                        loadingDialog.dismiss()
//                        if (isAdded) {
//                            try {
//                                onToSignUpPage()
//                            } catch (e: Exception) {
//                                e.printStackTrace()
//                            }
//                        }
//                    }
//
//                    Resource.Status.ERROR -> {
//                        loadingDialog.dismiss()
//                        if (isAdded) {
//                            mViewDataBinding.root.snackbar(it.message!!)
//                        }
//                        Log.d("TAG", "eeeeeeeeeee: ${it.message}")
//                    }
//                }
//            }
//        }

        if (!mViewModel.deleteUserResponse.hasActiveObservers()) {
            mViewModel.deleteUserResponse.observe(requireActivity()) {
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

                            try {
                                if (isAdded) {
                                    mViewDataBinding.root.snackbar(data.message.toString())
                                }

                                findNavController().popBackStack(R.id.logInFragment, true)
                                findNavController().navigate(R.id.logInFragment, arguments, options)

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
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
                        if (isAdded) {
                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                        Log.d("TAG", "eeeeeeeeeee: ${it.message}")
                    }
                }
            }
        }


        mViewDataBinding.btnEditProfile.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileFragment_to_editProfileFragment,
                arguments,
                options
            )
        }
        mViewDataBinding.btnAddress.setOnClickListener {
            findNavController().navigate(
                R.id.addressListFragment,
                arguments,
                options
            )
        }
        mViewDataBinding.btnOrder.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileFragment_to_ordersFragment,
                arguments,
                options
            )
        }
        mViewDataBinding.btnWishList.setOnClickListener {
            findNavController().navigate(
                R.id.wishlistFragment,
                arguments,
                options
            )
        }
        mViewDataBinding.btnDeleteAcc.setOnClickListener {

            DialogHelperClass.deleteUserDialog(requireContext(),
                object : DialogHelperClass.Companion.DeleteUserDialogCallBack {
                    override fun onSignInClick1() {

                    }

                    override fun onSignUpClick1() {
                        mViewModel.deleteUser()

                    }

                }).show()

        }

        mViewDataBinding.btnUnSubscribe.setOnClickListener {
//            var bundle = arguments
//            if (bundle == null) {
//                bundle = Bundle()
//            }
//            bundle?.putString("routeSubs", "profile")


            findNavController().navigate(R.id.unsubsFragment, arguments, options)


            /*   DialogHelperClass.unsubUserDialog(requireContext(),
                   object : DialogHelperClass.Companion.DeleteUserDialogCallBack {
                       override fun onSignInClick1() {

                       }

                       override fun onSignUpClick1() {
                           mViewModel.unsub()

                       }

                   }).show()*/


        }
        mViewDataBinding.btnChance.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileFragment_to_chancesFragment,
                arguments,
                options
            )
        }
        mViewDataBinding.btnleaderboard.setOnClickListener {
            findNavController().navigate(R.id.loaderBoardFragment, arguments, options)
        }
        mViewDataBinding.btnSecurity.setOnClickListener {
//            findNavController().navigate(R.id.loaderBoardFragment, arguments, options)

            val uri: Uri =
                Uri.parse("https://sites.google.com/view/equiz-privacy-policy?usp=sharing") // missing 'http://' will cause crashed


            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }


    }


    lateinit var touchEnable: SwitchCompat


    fun TouchIdDialog(): Dialog {
        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.touchid_dialog)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.setCancelable(false)

        touchEnable = dialog.findViewById(R.id.switchEnable)
        val btn = dialog.findViewById<ImageView>(R.id.imageView17)



        btn.setOnClickListener {
            dialog.dismiss()
            Log.d("TAG", "TouchIdDialog:")
        }

        if (isEnable) {
            touchEnable.isChecked = true
        }


        touchEnable.setOnCheckedChangeListener { compoundButton, b ->
            Log.d("TAG", "TouchIdDialog:$b")

            if (b == true) {
                PrefHelper.getUSerInstance(requireContext()).setCredentials(
                    PrefHelper.UserCredential(
                        useremaill,
                        userphonee,
                        userpasswordd,
                        true
                    )
                )
                isEnable = true

            } else {
                PrefHelper.getUSerInstance(requireContext()).setCredentials(
                    PrefHelper.UserCredential(
                        useremaill,
                        userphonee,
                        userpasswordd,
                        false
                    )
                )
                isEnable = false
            }


            Log.d("TAG", "TouchIdDialog: $isEnable}")


        }


        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }

}