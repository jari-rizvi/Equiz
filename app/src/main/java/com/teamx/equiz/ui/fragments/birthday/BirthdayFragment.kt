package com.teamx.equiz.ui.fragments.birthday


import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.anupkumarpanwar.scratchview.ScratchView
import com.bumptech.glide.Glide
import com.cooltechworks.views.ScratchImageView
import com.teamx.equiz.BR
import com.teamx.equiz.MainApplication
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentBirthdayBinding
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BirthdayFragment : BaseFragment<FragmentBirthdayBinding, BirthdayViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_birthday
    override val viewModel: Class<BirthdayViewModel>
        get() = BirthdayViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions
    lateinit var scratchImageView: ScratchView

    @RequiresApi(Build.VERSION_CODES.N)
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

        scratchImageView = mViewDataBinding.scratchView

        // on below line we are setting reveal listener for our scratch card image view.
//        scratchImageView.setRevealListener(object : ScratchImageView.IRevealListener {
//            override fun onRevealed(iv: ScratchImageView) {
//                // this method is called after revealing the image.
//                // on below line we are displaying a toast message
//                // when the scratch card is revealed.
//                Toast.makeText(requireContext(), "Congratulations!", Toast.LENGTH_SHORT).show()
//            }
//            override fun onRevealPercentChangedListener(siv: ScratchImageView, percent: Float) {
//                // we can check how much percentage of
//                // image is revealed using percent variable
//            }
//        })


        mViewModel.scratchimg("65687763a31327701e23cf0b")
        if (!mViewModel.scratchimgResponse.hasActiveObservers()) {
            mViewModel.scratchimgResponse.observe(requireActivity()) {
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
                            Glide.with(MainApplication.context).load(data.data.image)
                                .into(mViewDataBinding.hiddenImg);


                        }
                    }

                    Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
                        onToSignUpPage()
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
                    mViewModel.scratchimgResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }


        scratchImageView.setRevealListener(object : ScratchView.IRevealListener {
            override fun onRevealed(scratchView: ScratchView) {
                Toast.makeText(requireContext(), "Revealed", Toast.LENGTH_SHORT).show()
                mViewDataBinding.scrTxt.visibility = View.GONE
                mViewDataBinding.imgTxt2.visibility = View.GONE
                mViewDataBinding.scratchView.visibility = View.GONE
                mViewDataBinding.img2.visibility = View.VISIBLE
            }

            override fun onRevealPercentChangedListener(scratchView: ScratchView, percent: Float) {

                if (percent >= .8f) {
                    scratchView.reveal()
                }

            }
        })


    }
}