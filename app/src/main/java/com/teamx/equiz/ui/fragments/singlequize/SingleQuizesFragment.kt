package com.teamx.equiz.ui.fragments.singlequize

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.equiz.BR
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentQuizesBinding
import com.teamx.equiz.databinding.FragmentSingleQuizBinding
import com.teamx.equiz.ui.fragments.quizes.QuizesInterface
import com.teamx.equiz.ui.fragments.quizes.QuizesViewModel
import com.teamx.equiz.ui.fragments.quizes.adapter.QuizesAdapter
import com.teamx.equiz.ui.fragments.quizes.adapter.QuizesTitleAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleQuizesFragment : BaseFragment<FragmentSingleQuizBinding, SingleQuizesViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_single_quiz
    override val viewModel: Class<SingleQuizesViewModel>
        get() = SingleQuizesViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions


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

//        mViewDataBinding.txtLogin1.background = ResourcesCompat.getDrawable(resources, R.drawable.txt_food_category,null)


        mViewDataBinding.txtLogin1.setOnClickListener {
            selectedQuize(mViewDataBinding.txtLogin1, true, true)
            selectedQuize(mViewDataBinding.txtLogin112, false, false)
            selectedQuize(mViewDataBinding.txtLogin11542, false, false)
            selectedQuize(mViewDataBinding.txtLogin1154542, false, false)
        }

        mViewDataBinding.txtLogin112.setOnClickListener {
            selectedQuize(mViewDataBinding.txtLogin1, false, false)
            selectedQuize(mViewDataBinding.txtLogin112, true, true)
            selectedQuize(mViewDataBinding.txtLogin11542, false, false)
            selectedQuize(mViewDataBinding.txtLogin1154542, false, false)
        }

        mViewDataBinding.txtLogin11542.setOnClickListener {
            selectedQuize(mViewDataBinding.txtLogin1, false, false)
            selectedQuize(mViewDataBinding.txtLogin112, false, false)
            selectedQuize(mViewDataBinding.txtLogin11542, true, true)
            selectedQuize(mViewDataBinding.txtLogin1154542, false, false)
        }

        mViewDataBinding.txtLogin1154542.setOnClickListener {
            selectedQuize(mViewDataBinding.txtLogin1, false, false)
            selectedQuize(mViewDataBinding.txtLogin112, false, false)
            selectedQuize(mViewDataBinding.txtLogin11542, false, false)
            selectedQuize(mViewDataBinding.txtLogin1154542, true, false)
        }


        mViewDataBinding.textView4654.setOnClickListener {
            findNavController().navigate(R.id.action_quizesFragment_to_quizResultFragment)
        }



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


}