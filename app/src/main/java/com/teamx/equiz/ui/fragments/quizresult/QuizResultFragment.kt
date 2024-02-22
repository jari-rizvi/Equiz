package com.teamx.equiz.ui.fragments.quizresult

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
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


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack(R.id.dashboardFragment, true)
            findNavController().navigate(R.id.playQuizFragment, arguments, options)
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

//            findNavController().popBackStack()
//            findNavController().navigate(R.id.dashboardFragment, arguments, options)
            findNavController().popBackStack(R.id.dashboardFragment, true);
            findNavController().navigate(R.id.playQuizFragment, arguments, options)
        }
        mViewDataBinding.playAgainBtn.setOnClickListener {
            findNavController().popBackStack(R.id.dashboardFragment, true);
            findNavController().navigate(R.id.playQuizFragment, arguments, options)
//            findNavController().navigate(R.id.singleQuizFragment, arguments, options)
//            findNavController().popBackStack()
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
                            var chances = data.chances ?: 0

                            mViewDataBinding.chancesTxt.text= "You Won $chances Chances"

                            if(data.chances.equals(0.0)){
                                mViewDataBinding.textView4654545454.text= data.populatedQuizScoreData.userId.name
                            }
                            else{
                                mViewDataBinding.textView4654545454.text= "Congratulations "+data.populatedQuizScoreData.userId.name

                            }

                            mViewDataBinding.supportResultTxt.text="You answered ${data.populatedQuizScoreData.score.toInt()} questions correctly"
                            /*${String.format("%.3f", data.populatedQuizScoreData.score.toInt())}*/

                            Log.d("TAG", "resulttt: ${data.chances}")
                            Log.d("TAG", "resulttt: ${data.populatedQuizScoreData}")
                        }
                    }
                    Resource.Status.AUTH -> { loadingDialog.dismiss()
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
        val totalTime = bundle2.getDouble("totalTime")
        val remainingTime = bundle2.getDouble("remainingTime")

        val params = JsonObject()
        try {
            params.addProperty("total", totalAnswer)
            params.addProperty("correct", rightAnswer)
            params.addProperty("quizId", "$strId")
            params.addProperty("totalTime", totalTime)
            params.addProperty("remainingTime", remainingTime)
        } catch (e: JSONException) {
            e.printStackTrace()
        }


        mViewModel.quizResult(params)


    }


}