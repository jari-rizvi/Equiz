package com.teamx.equiz.ui.fragments.singlequize

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.RadioButton
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.squareup.picasso.Picasso
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
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

    private var quesNo: MutableLiveData<Int> = MutableLiveData(0)

    private var rightAnswer = -1
    private var rightAnswers = 0
    private var totalAnswers = 0
    private var selectAnswer = -1

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack(R.id.dashboardFragment, true)
            findNavController().navigate(R.id.dashboardFragment, arguments, options)
//            findNavController().popBackStack()
        }

        mViewDataBinding.btnback.setOnClickListener {
            findNavController().popBackStack(R.id.dashboardFragment, true);
            findNavController().navigate(R.id.dashboardFragment, arguments, options)
//            findNavController().popBackStack()
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

//        NetworkCallPoints.TOKENER =
//            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NTFlNWZiOGM3NjU2MDdlNzE0NjNiZGYiLCJpc0FkbWluIjp0cnVlLCJpYXQiOjE3MDE2ODg4ODAsImV4cCI6MTcwMTc3NTI4MH0.th7AmVunSuxLeq8XP5oe-JywCZGijWOAtrqPmImIKzM"


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

        mViewDataBinding.constAnswer1.setOnClickListener {
            selectAnswer = 0
            selectedRadioQuize(mViewDataBinding.constAnswer1, mViewDataBinding.radio1, true, true)
            selectedRadioQuize(mViewDataBinding.constAnswer2, mViewDataBinding.radio2, false, false)
            selectedRadioQuize(mViewDataBinding.constAnswer3, mViewDataBinding.radio3, false, false)
            selectedRadioQuize(mViewDataBinding.constAnswer4, mViewDataBinding.radio4, false, false)
            selectorRadio()
        }

        mViewDataBinding.constAnswer2.setOnClickListener {
            selectAnswer = 1
            selectedRadioQuize(mViewDataBinding.constAnswer1, mViewDataBinding.radio1, false, false)
            selectedRadioQuize(mViewDataBinding.constAnswer2, mViewDataBinding.radio2, true, true)
            selectedRadioQuize(mViewDataBinding.constAnswer3, mViewDataBinding.radio3, false, false)
            selectedRadioQuize(mViewDataBinding.constAnswer4, mViewDataBinding.radio4, false, false)
            selectorRadio()
        }

        mViewDataBinding.constAnswer3.setOnClickListener {
            selectAnswer = 2
            selectedRadioQuize(mViewDataBinding.constAnswer1, mViewDataBinding.radio1, false, false)
            selectedRadioQuize(mViewDataBinding.constAnswer2, mViewDataBinding.radio2, false, false)
            selectedRadioQuize(mViewDataBinding.constAnswer3, mViewDataBinding.radio3, true, true)
            selectedRadioQuize(mViewDataBinding.constAnswer4, mViewDataBinding.radio4, false, false)
            selectorRadio()
        }

        mViewDataBinding.constAnswer4.setOnClickListener {
            selectAnswer = 3
            selectedRadioQuize(mViewDataBinding.constAnswer1, mViewDataBinding.radio1, false, false)
            selectedRadioQuize(mViewDataBinding.constAnswer2, mViewDataBinding.radio2, false, false)
            selectedRadioQuize(mViewDataBinding.constAnswer3, mViewDataBinding.radio3, false, false)
            selectedRadioQuize(mViewDataBinding.constAnswer4, mViewDataBinding.radio4, true, true)
            selectorRadio()
        }


//        mViewDataBinding.backToHomeBtn.setOnClickListener {
//
//            var bundle = arguments
//            if (bundle == null) {
//                bundle = Bundle()
//            }
//            bundle?.putInt("rightAnswer", rightAnswers)
//            bundle?.putInt("totalAnswer", totalAnswers)
//
//
//            findNavController().navigate(
//                R.id.action_quizesFragment_to_quizResultFragment,
//                arguments,
//                options
//            )
//        }
        var bundle2 = arguments
        if (bundle2 == null) {
            bundle2 = Bundle()
        }

        val strId = bundle2.getString("quiz_id")

        if (!mViewModel.quizFindResponse.hasActiveObservers()) {
            mViewModel.quizFind("$strId", "", null)
            mViewModel.quizFindResponse.observe(requireActivity()) {
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
                                timerStart(data)
                                changeIndex(data)
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
                    }
                }
            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun selectorDisable() {
        totalAnswers++
//        job?.cancel()


        lifecycleScope.launch {
            mViewDataBinding.txtLogin1.isEnabled = false
            mViewDataBinding.txtLogin112.isEnabled = false
            mViewDataBinding.txtLogin11542.isEnabled = false
            mViewDataBinding.txtLogin1154542.isEnabled = false


//            when (rightAnswer) {
//                0 -> {
//                    selectedQuize(mViewDataBinding.txtLogin1, true, true)
//                }
//
//                1 -> {
//                    selectedQuize(mViewDataBinding.txtLogin112, true, true)
//                }
//
//                2 -> {
//                    selectedQuize(mViewDataBinding.txtLogin11542, true, true)
//                }
//
//                3 -> {
//                    selectedQuize(mViewDataBinding.txtLogin1154542, true, true)
//                }
//            }

            if (selectAnswer != rightAnswer) {
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
            } else {
                rightAnswers++
            }

            delay(500)


            changeObserver()


//            quesNo.value = quesNo.value?.plus(1)
        }


    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun selectorRadio() {
        totalAnswers++
//        job?.cancel()


        lifecycleScope.launch {
            mViewDataBinding.constAnswer1.isEnabled = false
            mViewDataBinding.constAnswer2.isEnabled = false
            mViewDataBinding.constAnswer3.isEnabled = false
            mViewDataBinding.constAnswer4.isEnabled = false


//            when (rightAnswer) {
//                0 -> {
//                    selectedRadioQuize(
//                        mViewDataBinding.constAnswer1,
//                        mViewDataBinding.radio1,
//                        true,
//                        true
//                    )
//                }
//
//                1 -> {
//                    selectedRadioQuize(
//                        mViewDataBinding.constAnswer2,
//                        mViewDataBinding.radio2,
//                        true,
//                        true
//                    )
//                }
//
//                2 -> {
//                    selectedRadioQuize(
//                        mViewDataBinding.constAnswer3,
//                        mViewDataBinding.radio3,
//                        true,
//                        true
//                    )
//                }
//
//                3 -> {
//                    selectedRadioQuize(
//                        mViewDataBinding.constAnswer4,
//                        mViewDataBinding.radio4,
//                        true,
//                        true
//                    )
//                }
//            }

            if (selectAnswer != rightAnswer) {
                when (selectAnswer) {
                    0 -> {
                        selectedRadioQuize(
                            mViewDataBinding.constAnswer1,
                            mViewDataBinding.radio1,
                            true,
                            false
                        )
                    }

                    1 -> {
                        selectedRadioQuize(
                            mViewDataBinding.constAnswer2,
                            mViewDataBinding.radio2,
                            true,
                            false
                        )
                    }

                    2 -> {
                        selectedRadioQuize(
                            mViewDataBinding.constAnswer3,
                            mViewDataBinding.radio3,
                            true,
                            false
                        )
                    }

                    3 -> {
                        selectedRadioQuize(
                            mViewDataBinding.constAnswer4,
                            mViewDataBinding.radio4,
                            true,
                            false
                        )
                    }
                }
            } else {
                rightAnswers++
            }

            delay(500)
            mViewDataBinding.radio1.isChecked = false
            mViewDataBinding.radio2.isChecked = false
            mViewDataBinding.radio3.isChecked = false
            mViewDataBinding.radio4.isChecked = false
            delay(100)

            changeObserver()


//            quesNo.value = quesNo.value?.plus(1)
        }


    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun changeObserver() {


        mViewModel.quizFindResponse.observe(requireActivity()) {

            it.data.let {
                if (it != null) {
                    if (quesNo.value == it.data?.get(0)?.question?.size) {
                        if (job != null) {
//                            job?.cancel()
                            var bundle = arguments
                            if (bundle == null) {
                                bundle = Bundle()
                            }
                            bundle?.putInt("rightAnswer", rightAnswers)
                            bundle?.putInt("totalAnswer", totalAnswers)


                            findNavController().navigate(
                                R.id.action_quizesFragment_to_quizResultFragment,
                                arguments,
                                options
                            )
//                            mViewDataBinding.backToHomeBtn.visibility = View.VISIBLE
                        }
                        return@observe
                    }

                    changeIndex(it)

                    val anim = AnimationUtils.loadAnimation(requireActivity(), R.anim.exit_to_left)
                    mViewDataBinding.constraintLayout9.startAnimation(anim)

                    val anim2 =
                        AnimationUtils.loadAnimation(requireActivity(), R.anim.enter_from_right)
                    mViewDataBinding.constraintLayout9.startAnimation(anim2)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun changeIndex(data: SingleQuizData) {
//        timerStart()
//        quesNo.observe(viewLifecycleOwner) {


        mViewDataBinding.txtLogin1.isEnabled = true
        mViewDataBinding.txtLogin112.isEnabled = true
        mViewDataBinding.txtLogin11542.isEnabled = true
        mViewDataBinding.txtLogin1154542.isEnabled = true

        mViewDataBinding.constAnswer1.isEnabled = true
        mViewDataBinding.constAnswer2.isEnabled = true
        mViewDataBinding.constAnswer3.isEnabled = true
        mViewDataBinding.constAnswer4.isEnabled = true

        selectedQuize(mViewDataBinding.txtLogin1, false, false)
        selectedQuize(mViewDataBinding.txtLogin112, false, false)
        selectedQuize(mViewDataBinding.txtLogin11542, false, false)
        selectedQuize(mViewDataBinding.txtLogin1154542, false, false)

        selectedRadioQuize(mViewDataBinding.constAnswer1, mViewDataBinding.radio1, false, false)
        selectedRadioQuize(mViewDataBinding.constAnswer2, mViewDataBinding.radio2, false, false)
        selectedRadioQuize(mViewDataBinding.constAnswer3, mViewDataBinding.radio3, false, false)
        selectedRadioQuize(mViewDataBinding.constAnswer4, mViewDataBinding.radio4, false, false)





        mViewDataBinding.playAgainBtn.text = try {
            "${quesNo.value!! + 1}/${data.data?.get(0)?.question?.size}"
        } catch (e: Exception) {
            ""
        }

        val modelQuiz = data.data?.get(0)

        if (modelQuiz != null) {
            try {
                rightAnswer = modelQuiz.question?.get(quesNo.value!!)?.isCorrectIndex ?: 0
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (modelQuiz != null) {
            mViewDataBinding.textView4654545454.text = try {
                modelQuiz.question?.get(quesNo.value!!)?.questionText
            } catch (e: Exception) {
                ""
            }
        }

        if (modelQuiz != null) {
            mViewDataBinding.txtLogin1.text = try {
                modelQuiz.question?.get(quesNo.value!!)?.options?.get(0)?.text ?: ""
            } catch (e: Exception) {
                ""
            }
        }

        if (modelQuiz != null) {
            mViewDataBinding.txtLogin112.text = try {
                modelQuiz.question?.get(quesNo.value!!)?.options?.get(1)?.text ?: ""
            } catch (e: Exception) {
                ""
            }
        }

        if (modelQuiz != null) {
            mViewDataBinding.txtLogin11542.text = try {
                modelQuiz.question?.get(quesNo.value!!)?.options?.get(2)?.text ?: ""
            } catch (e: Exception) {
                ""
            }
        }

        if (modelQuiz != null) {
            mViewDataBinding.txtLogin1154542.text = try {
                modelQuiz.question?.get(quesNo.value!!)?.options?.get(3)?.text ?: ""
            } catch (e: Exception) {
                ""
            }
        }

        if (modelQuiz != null) {
            if (modelQuiz.question?.get(quesNo.value!!)?.questionImage.isNullOrBlank()) {
                mViewDataBinding.imgQuestion.visibility = View.GONE
            } else {
                mViewDataBinding.imgQuestion.visibility = View.VISIBLE
                Picasso.get().load(modelQuiz.question?.get(quesNo.value!!)?.questionImage)
                    .placeholder(R.drawable.group_19215).error(R.drawable.group_19215)
                    .resize(500, 500)
                    .into(mViewDataBinding.imgQuestion)
            }
        }

        if (modelQuiz != null) {
            if (!modelQuiz.question?.get(quesNo.value!!)?.options?.get(0)?.icon.isNullOrBlank()) {
                mViewDataBinding.imageLayout.visibility = View.VISIBLE
                mViewDataBinding.textLayout.visibility = View.GONE

                Picasso.get().load(modelQuiz.question?.get(quesNo.value!!)?.options?.get(0)?.icon)
                    .placeholder(R.drawable.group_19215).error(R.drawable.group_19215)
                    .resize(500, 500)
                    .into(mViewDataBinding.imgAnswer1)

                Picasso.get().load(modelQuiz.question?.get(quesNo.value!!)?.options?.get(1)?.icon)
                    .placeholder(R.drawable.group_19215).error(R.drawable.group_19215)
                    .resize(500, 500)
                    .into(mViewDataBinding.imgAnswer2)

                Picasso.get().load(modelQuiz.question?.get(quesNo.value!!)?.options?.get(2)?.icon)
                    .placeholder(R.drawable.group_19215).error(R.drawable.group_19215)
                    .resize(500, 500)
                    .into(mViewDataBinding.imgAnswer3)

                Picasso.get().load(modelQuiz.question?.get(quesNo.value!!)?.options?.get(3)?.icon)
                    .placeholder(R.drawable.group_19215).error(R.drawable.group_19215)
                    .resize(500, 500)
                    .into(mViewDataBinding.imgAnswer4)

            } else {
                mViewDataBinding.imageLayout.visibility = View.GONE
                mViewDataBinding.textLayout.visibility = View.VISIBLE
            }
        }

        if (modelQuiz != null) {
            if (!modelQuiz.question?.get(quesNo.value!!)?.options?.get(0)?.text.isNullOrBlank()) {
                mViewDataBinding.txtAnswer1.text = try {
                    modelQuiz.question?.get(quesNo.value!!)?.options?.get(0)?.text
                } catch (e: Exception) {
                    ""
                }

                mViewDataBinding.txtAnswer2.text = try {
                    modelQuiz.question?.get(quesNo.value!!)?.options?.get(1)?.text
                } catch (e: Exception) {
                    ""
                }
                mViewDataBinding.txtAnswer3.text = try {
                    modelQuiz.question?.get(quesNo.value!!)?.options?.get(2)?.text
                } catch (e: Exception) {
                    ""
                }
                mViewDataBinding.txtAnswer4.text = try {
                    modelQuiz.question?.get(quesNo.value!!)?.options?.get(3)?.text
                } catch (e: Exception) {
                    ""
                }
                mViewDataBinding.txtAnswer1.visibility = View.VISIBLE
                mViewDataBinding.txtAnswer2.visibility = View.VISIBLE
                mViewDataBinding.txtAnswer3.visibility = View.VISIBLE
                mViewDataBinding.txtAnswer4.visibility = View.VISIBLE
            } else {
                mViewDataBinding.txtAnswer1.visibility = View.GONE
                mViewDataBinding.txtAnswer2.visibility = View.GONE
                mViewDataBinding.txtAnswer3.visibility = View.GONE
                mViewDataBinding.txtAnswer4.visibility = View.GONE
            }
        }


        quesNo.value = quesNo.value?.plus(1)
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


    @SuppressLint("ResourceAsColor")
    private fun selectedRadioQuize(
        checkedTextView: ConstraintLayout,
        radioButton: RadioButton,
        isSelected: Boolean,
        isRight: Boolean
    ) {

        if (isSelected) {
            radioButton.isChecked = true
            val newColor = ContextCompat.getColor(
                requireActivity(),
                R.color.red_quiz
            )
//            checkedTextView.buttonTintList = ColorStateList.valueOf(newColor)
            checkedTextView.backgroundTintList = ColorStateList.valueOf(newColor)
            if (isRight) {
                val newColor = ContextCompat.getColor(
                    requireActivity(),
                    R.color.Green_quiz
                ) // Aap apne pasand ke color resource ka use karein
                checkedTextView.backgroundTintList = ColorStateList.valueOf(newColor)
                checkedTextView.backgroundTintMode
            }
        } else {
            radioButton.isChecked = false
            val newColor = ContextCompat.getColor(
                requireActivity(),
                R.color.white
            ) // Aap apne pasand ke color resource ka use karein
            checkedTextView.backgroundTintList = ColorStateList.valueOf(newColor)
        }
    }

    private var job: Job? = null

    @RequiresApi(Build.VERSION_CODES.M)
    private fun timerStart(data: SingleQuizData) {
//        var durationSeconds = 30.0
        var durationSeconds = data.data?.get(0)?.timer ?: 0.0
        var progressTime = 100.0

        var finalProgress = progressTime / durationSeconds

        job = lifecycleScope.launch {
            mViewDataBinding.textView46545454.text = "${durationSeconds.toInt()}"
            mViewDataBinding.progressbar.progress = progressTime.toInt()
            while (durationSeconds > 0) {

                delay(1000)
                durationSeconds--

                progressTime = progressTime - finalProgress

//                progressTime = progressTime / durationSeconds
                mViewDataBinding.textView46545454.text = "${durationSeconds.toInt()}"
                Log.d("progressTime", "timerStart: ${progressTime}")
                mViewDataBinding.progressbar.progress = progressTime.toInt()

            }

            var bundle = arguments
            if (bundle == null) {
                bundle = Bundle()
            }
            bundle?.putInt("rightAnswer", rightAnswers)
            bundle?.putInt("totalAnswer", totalAnswers)


            findNavController().navigate(
                R.id.action_quizesFragment_to_quizResultFragment,
                arguments,
                options
            )

//            changeObserver()
        }
    }

}