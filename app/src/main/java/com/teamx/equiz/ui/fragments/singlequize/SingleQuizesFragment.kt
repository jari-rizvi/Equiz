package com.teamx.equiz.ui.fragments.singlequize

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.constants.NetworkCallPoints
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentSingleQuizBinding
import com.teamx.equiz.ui.fragments.singlequize.model.Data
import com.teamx.equiz.ui.fragments.singlequize.model.SingleQuizData
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SingleQuizesFragment : BaseFragment<FragmentSingleQuizBinding, SingleQuizesViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_single_quiz
    override val viewModel: Class<SingleQuizesViewModel>
        get() = SingleQuizesViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions

    private lateinit var quizArrayList: ArrayList<Data>

    private var quesNo = 0

    private var rightAnswer = -1
    private var selectAnswer = -1

    @RequiresApi(Build.VERSION_CODES.M)
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

        NetworkCallPoints.TOKENER =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NTFlNWZiOGM3NjU2MDdlNzE0NjNiZGYiLCJpc0FkbWluIjp0cnVlLCJpYXQiOjE3MDE2ODg4ODAsImV4cCI6MTcwMTc3NTI4MH0.th7AmVunSuxLeq8XP5oe-JywCZGijWOAtrqPmImIKzM"


        val bundle = arguments
        val modelQuizId = bundle?.getString("modelQuizId")


        quizArrayList = ArrayList()

//        mViewDataBinding.txtLogin1.background = ResourcesCompat.getDrawable(resources, R.drawable.txt_food_category,null)


        mViewDataBinding.txtLogin1.setOnClickListener {
            selectAnswer = 0
            selectedQuize(mViewDataBinding.txtLogin1, true, true)
            selectedQuize(mViewDataBinding.txtLogin112, false, false)
            selectedQuize(mViewDataBinding.txtLogin11542, false, false)
            selectedQuize(mViewDataBinding.txtLogin1154542, false, false)
            selectorDisable()
        }

        mViewDataBinding.txtLogin112.setOnClickListener {
            selectAnswer = 1
            selectedQuize(mViewDataBinding.txtLogin1, false, false)
            selectedQuize(mViewDataBinding.txtLogin112, true, true)
            selectedQuize(mViewDataBinding.txtLogin11542, false, false)
            selectedQuize(mViewDataBinding.txtLogin1154542, false, false)
            selectorDisable()
        }

        mViewDataBinding.txtLogin11542.setOnClickListener {
            selectAnswer = 2
            selectedQuize(mViewDataBinding.txtLogin1, false, false)
            selectedQuize(mViewDataBinding.txtLogin112, false, false)
            selectedQuize(mViewDataBinding.txtLogin11542, true, true)
            selectedQuize(mViewDataBinding.txtLogin1154542, false, false)
            selectorDisable()
        }

        mViewDataBinding.txtLogin1154542.setOnClickListener {
            selectAnswer = 3
            selectedQuize(mViewDataBinding.txtLogin1, false, false)
            selectedQuize(mViewDataBinding.txtLogin112, false, false)
            selectedQuize(mViewDataBinding.txtLogin11542, false, false)
            selectedQuize(mViewDataBinding.txtLogin1154542, true, true)
            selectorDisable()
        }


        mViewDataBinding.textView4654.setOnClickListener {
            findNavController().navigate(R.id.action_quizesFragment_to_quizResultFragment)
        }


        if (!mViewModel.quizFindResponse.hasActiveObservers()) {
            mViewModel.quizFind("World", "General Knowledge", null)
            mViewModel.quizFindResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            changeIndex(data)
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

    @RequiresApi(Build.VERSION_CODES.M)
    fun selectorDisable() {
        job.cancel()


        lifecycleScope.launch {
            mViewDataBinding.txtLogin1.isEnabled = false
            mViewDataBinding.txtLogin112.isEnabled = false
            mViewDataBinding.txtLogin11542.isEnabled = false
            mViewDataBinding.txtLogin1154542.isEnabled = false


            when (rightAnswer) {
                0 -> {
                    selectedQuize(mViewDataBinding.txtLogin1, true, true)
                }

                1 -> {
                    selectedQuize(mViewDataBinding.txtLogin112, true, true)
                }

                2 -> {
                    selectedQuize(mViewDataBinding.txtLogin11542, true, true)
                }

                3 -> {
                    selectedQuize(mViewDataBinding.txtLogin1154542, true, true)
                }
            }



            if (selectAnswer != rightAnswer){
                when (selectAnswer) {
                    0 -> {
                        selectedQuize(mViewDataBinding.txtLogin1, true, false)
                    }

                    1 -> {
                        selectedQuize(mViewDataBinding.txtLogin112, true, false)
                    }

                    2 -> {
                        selectedQuize(mViewDataBinding.txtLogin11542, true, false)
                    }

                    3 -> {
                        selectedQuize(mViewDataBinding.txtLogin1154542, true, false)
                    }
                }
            }

            delay(1000)

            changeObserver()


        }


    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun changeObserver() {
        mViewModel.quizFindResponse.observe(requireActivity()) {

            it.data.let {
                if (it != null) {
                    if (quesNo == it.data.size) {
                        if (job != null) {
                            job.cancel()
                            mViewDataBinding.textView4654.visibility = View.VISIBLE
                        }
                        return@observe
                    }
                    changeIndex(it)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun changeIndex(data: SingleQuizData) {

        mViewDataBinding.txtLogin1.isEnabled = true
        mViewDataBinding.txtLogin112.isEnabled = true
        mViewDataBinding.txtLogin11542.isEnabled = true
        mViewDataBinding.txtLogin1154542.isEnabled = true

        selectedQuize(mViewDataBinding.txtLogin1, false, false)
        selectedQuize(mViewDataBinding.txtLogin112, false, false)
        selectedQuize(mViewDataBinding.txtLogin11542, false, false)
        selectedQuize(mViewDataBinding.txtLogin1154542, false, false)

        timerStart()

        Log.d("changeIndex", "changeIndex: ${quesNo}/${data.data.size}")
        mViewDataBinding.textView465454.text = try {
            "${quesNo + 1}/${data.data.size}"
        } catch (e: Exception) {
            ""
        }

        val modelQuiz = data.data[0]

        rightAnswer = modelQuiz.question[quesNo].isCorrectIndex

        mViewDataBinding.textView4654545454.text = try {
            "${modelQuiz.question[quesNo].questionText}"
        } catch (e: Exception) {
            ""
        }

        mViewDataBinding.txtLogin1.text = try {
            "${modelQuiz.question[quesNo].options[0]}"
        } catch (e: Exception) {
            ""
        }

        mViewDataBinding.txtLogin112.text = try {
            "${modelQuiz.question[quesNo].options[1]}"
        } catch (e: Exception) {
            ""
        }

        mViewDataBinding.txtLogin11542.text = try {
            "${modelQuiz.question[quesNo].options[2]}"
        } catch (e: Exception) {
            ""
        }

        mViewDataBinding.txtLogin1154542.text = try {
            "${modelQuiz.question[quesNo].options[3]}"
        } catch (e: Exception) {
            ""
        }


        quesNo++
    }

    fun quizResult(select: Int, rightAnswer: Int) {

    }


    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceType")
    private fun selectedQuize(
        checkedTextView: AppCompatCheckedTextView,
        isSelected: Boolean,
        isRight: Boolean
    ) {

        if (isSelected) {
            checkedTextView.setBackgroundResource(R.drawable.quiz_selector)
            checkedTextView.isChecked = isRight
            checkedTextView.setTextColor(requireActivity().getColor(R.color.white))
        } else {
            checkedTextView.setBackgroundResource(R.drawable.button_radius_corner_red)
            checkedTextView.setTextColor(requireActivity().getColor(R.color.theme_color))
        }
    }

    private lateinit var job: Job

    @RequiresApi(Build.VERSION_CODES.M)
    private fun timerStart() {
        val durationSeconds = 30
        var remainingSeconds = durationSeconds
        val progressTime = 100
        mViewDataBinding.progressbar.progress = progressTime

        job = lifecycleScope.launch {
            while (remainingSeconds > 0) {
                delay(1000) // Delay for 1 second
                remainingSeconds--
                progressTime - 3
                mViewDataBinding.textView46545454.text = "$remainingSeconds"
                mViewDataBinding.progressbar.progress = progressTime
            }

            changeObserver()
        }
    }

}