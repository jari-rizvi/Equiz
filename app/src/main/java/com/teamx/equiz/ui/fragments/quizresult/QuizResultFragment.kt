package com.teamx.equiz.ui.fragments.quizresult

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.gson.JsonObject
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentQuizResultBinding
import com.teamx.equiz.ui.fragments.singlequize.SingleQuizesViewModel
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException

@AndroidEntryPoint
class QuizResultFragment : BaseFragment<FragmentQuizResultBinding, SingleQuizesViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_quiz_result
    override val viewModel: Class<SingleQuizesViewModel>
        get() = SingleQuizesViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions


    @RequiresApi(Build.VERSION_CODES.M)
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


        mViewDataBinding.btnback.setOnClickListener {

            findNavController().popBackStack()
        }
        mViewDataBinding.playAgainBtn.setOnClickListener {

            findNavController().popBackStack()
        }
        mViewDataBinding.backToHomeBtn.setOnClickListener {

            findNavController().navigate(R.id.dashboardFragment, arguments, options)
        }

        resultQuizFragment()



        if (!mViewModel.quizResultResponse.hasActiveObservers()) {
            mViewModel.quizResultResponse.observe(requireActivity()) {
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
                            mViewDataBinding.chancesTxt.text= data.quizId
                            mViewDataBinding.supportResultTxt.text="You answered ${data.score}% questions correctly"

                        }
                    }
                    Resource.Status.AUTH -> { loadingDialog.dismiss()
                        onToSignUpPage()
                    }
                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        if (isAdded) {
                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                    }
                }
            }
        }

    }


    private fun resultQuizFragment() {
        var bundle2 = arguments
        if (bundle2 == null) {
            bundle2 = Bundle()
        }

        val strId = bundle2.getString("quiz_id")
        val rightAnswer = bundle2.getInt("rightAnswer")
        val totalAnswer = bundle2.getInt("totalAnswer")

        val params = JsonObject()
        try {
            params.addProperty("total", totalAnswer)
            params.addProperty("correct", rightAnswer)
            params.addProperty("quizId", "$strId")
        } catch (e: JSONException) {
            e.printStackTrace()
        }


        mViewModel.quizResult(params)


    }


}