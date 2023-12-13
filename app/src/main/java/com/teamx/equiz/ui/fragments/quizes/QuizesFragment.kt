package com.teamx.equiz.ui.fragments.quizes

import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.constants.NetworkCallPoints.Companion.TOKENER
import com.teamx.equiz.data.models.quizTitleData.Data
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentQuizesBinding
import com.teamx.equiz.ui.fragments.quizes.adapter.QuizesAdapter
import com.teamx.equiz.ui.fragments.quizes.adapter.QuizesTitleAdapter
import com.teamx.equiz.utils.snackbar
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

    private lateinit var strArrayList: ArrayList<Data>
    private lateinit var strTitleArrayList: ArrayList<TitleData>
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

        TOKENER =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NTFlNWZiOGM3NjU2MDdlNzE0NjNiZGYiLCJpc0FkbWluIjp0cnVlLCJpYXQiOjE3MDE2ODg4ODAsImV4cCI6MTcwMTc3NTI4MH0.th7AmVunSuxLeq8XP5oe-JywCZGijWOAtrqPmImIKzM"

        initializeCategoriesAdapter()


        if (!mViewModel.quizTitleResponse.hasActiveObservers()) {
            mViewModel.quizTitle("World", null, "normal")
            mViewModel.quizTitleResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
//                        loadingDialog.show()
                        mViewDataBinding.shimmerLayout.startShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.VISIBLE
                    }

                    Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
                        mViewDataBinding.shimmerLayout.stopShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
                        it.data?.let { data ->
                            strArrayList.clear()
                            strArrayList.addAll(data.data)
                            quizesAdapter.notifyDataSetChanged()
                        }
                    }

                    Resource.Status.ERROR -> {
//                        loadingDialog.dismiss()
                        mViewDataBinding.shimmerLayout.stopShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        if (isAdded) {
                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                    }
                }
            }
        }


    }

    private fun initializeCategoriesAdapter() {
        strArrayList = ArrayList()
        strTitleArrayList = ArrayList()
        strTitleArrayList.add(TitleData("World", true))
        strTitleArrayList.add(TitleData("World", false))
        strTitleArrayList.add(TitleData("World", false))

        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recCategories.layoutManager = layoutManager1

        quizesTitleAdapter = QuizesTitleAdapter(strTitleArrayList, this)
        mViewDataBinding.recCategories.adapter = quizesTitleAdapter


        val layoutManager2 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recQuizes.layoutManager = layoutManager2

        quizesAdapter = QuizesAdapter(strArrayList, this)
        mViewDataBinding.recQuizes.adapter = quizesAdapter
    }

    override fun quizTitle(position: Int) {
        strTitleArrayList.forEach { it.isSelected = false }
        strTitleArrayList[position].isSelected = true
        quizesTitleAdapter.notifyDataSetChanged()
        mViewModel.quizTitle("World", null, "normal")
    }

    override fun quizeItem(position: Int) {
        val modelQuiz = strArrayList[position]
        val bundle = Bundle()
        bundle.putString("modelQuizId", modelQuiz._id)
        findNavController().navigate(R.id.action_quizesFragment_to_singleQuizFragment, bundle)
    }

    /*   override fun quizeItem() {
           findNavController().navigate(R.id.action_quizesFragment_to_singleQuizFragment)
       }*/

}

interface QuizesInterface {
    fun quizTitle(position: Int)
    fun quizeItem(position: Int)
}

data class TitleData(
    val value: String,
    var isSelected: Boolean
)
