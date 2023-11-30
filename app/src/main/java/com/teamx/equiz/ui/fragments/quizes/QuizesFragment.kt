package com.teamx.equiz.ui.fragments.quizes

import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentQuizesBinding
import com.teamx.equiz.ui.fragments.quizes.adapter.QuizesAdapter
import com.teamx.equiz.ui.fragments.quizes.adapter.QuizesTitleAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizesFragment : BaseFragment<FragmentQuizesBinding, QuizesViewModel>(), QuizesInterface {

    override val layoutId: Int
        get() = R.layout.fragment_quizes
    override val viewModel: Class<QuizesViewModel>
        get() = QuizesViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions

    private lateinit var strArrayList: ArrayList<String>
    private lateinit var quizesTitleAdapter: QuizesTitleAdapter
    private lateinit var quizesAdapter: QuizesAdapter


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
        initializeCategoriesAdapter()


    }

    private fun initializeCategoriesAdapter() {
        strArrayList = ArrayList()

        strArrayList.add("")
        strArrayList.add("")
        strArrayList.add("")
        strArrayList.add("")

        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recCategories.layoutManager = layoutManager1

        quizesTitleAdapter = QuizesTitleAdapter(strArrayList, this)
        mViewDataBinding.recCategories.adapter = quizesTitleAdapter


        val layoutManager2 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recQuizes.layoutManager = layoutManager2

        quizesAdapter = QuizesAdapter(strArrayList, this)
        mViewDataBinding.recQuizes.adapter = quizesAdapter
    }

    override fun quizTitle() {

    }

    override fun quizeItem() {
    }

    /*   override fun quizeItem() {
           findNavController().navigate(R.id.action_quizesFragment_to_singleQuizFragment)
       }*/

}

interface QuizesInterface {
    fun quizTitle()
    fun quizeItem()
}
