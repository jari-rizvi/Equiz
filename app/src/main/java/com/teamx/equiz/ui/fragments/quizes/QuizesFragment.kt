package com.teamx.equiz.ui.fragments.quizes

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.annotation.Keep
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.quizTitleData.Data
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentQuizesBinding
import com.teamx.equiz.ui.fragments.quizes.adapter.QuizesAdapter
import com.teamx.equiz.ui.fragments.quizes.adapter.QuizesTitleAdapter
import com.teamx.equiz.utils.PrefHelper
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
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack(R.id.dashboardFragment, true)
            findNavController().navigate(R.id.dashboardFragment, arguments, options)
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

//        TOKENER =
//            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NTFlNWZiOGM3NjU2MDdlNzE0NjNiZGYiLCJpc0FkbWluIjp0cnVlLCJpYXQiOjE3MDE2ODg4ODAsImV4cCI6MTcwMTc3NTI4MH0.th7AmVunSuxLeq8XP5oe-JywCZGijWOAtrqPmImIKzM"

        initializeCategoriesAdapter()
        mViewDataBinding.btnback.setOnClickListener {
            findNavController().popBackStack()
        }

        if (!mViewModel.quizTitleResponse.hasActiveObservers()) {
            mViewModel.quizTitle("World", null, "")
            mViewModel.quizTitleResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
//                        loadingDialog.show()
                        mViewDataBinding.shimmerLayout.startShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.VISIBLE
                    }
                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }
                    Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
                        mViewDataBinding.shimmerLayout.stopShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
                            strArrayList.clear()
                        it.data?.let { data ->
                            strArrayList.addAll(data.data)
                        }
                        mViewDataBinding.recQuizes.adapter?.notifyDataSetChanged()
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
//                        loadingDialog.dismiss()
                        mViewDataBinding.shimmerLayout.stopShimmer()
                        mViewDataBinding.shimmerLayout.visibility = View.GONE
                        mViewDataBinding.mainLayout.visibility = View.VISIBLE
                        if (isAdded) {
                            strArrayList.clear()
                            mViewDataBinding.recQuizes.adapter?.notifyDataSetChanged()
                            mViewDataBinding.root.snackbar(it.message!!)
                        }
                    }
                }
                if (isAdded) {
                    mViewModel.quizTitleResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }

            }
        }


    }

    private fun initializeCategoriesAdapter() {
        strArrayList = ArrayList()
        strTitleArrayList = ArrayList()
        var bundle = arguments
        if (bundle == null) {
            bundle = Bundle()
        }

        var country = bundle?.getString("country")

        if (country.isNullOrEmpty()) {
            country = PrefHelper.getInstance(requireContext()).getCountry

        }

        val splitter = country.toString().split(" ")
        if (splitter.size > 1) {
            var tempString = ""
            splitter.forEach {
                tempString += it.first().toString()
            }
            country = tempString
        } else {
            if (country.isNullOrEmpty()) {
                country = "Regional"

            }

        }




        strTitleArrayList.add(TitleData("World", true))
        strTitleArrayList.add(TitleData("$country", false))
        strTitleArrayList.add(TitleData("Premium", false))

        val layoutManager1 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        mViewDataBinding.recCategories.layoutManager = layoutManager1

        quizesTitleAdapter = QuizesTitleAdapter(strTitleArrayList, this)
        mViewDataBinding.recCategories.adapter = quizesTitleAdapter


        val layoutManager2 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        mViewDataBinding.recQuizes.layoutManager = layoutManager2

        quizesAdapter = QuizesAdapter(strArrayList, this,false)
        mViewDataBinding.recQuizes.adapter = quizesAdapter
    }

    override fun quizTitle(position: Int, previPos: Int) {

        val tick = strTitleArrayList[position].value

        val topic = if (tick.equals("World", true)) {
            "General Knowledge"
        } else {
            ""
        }
        Log.d("quizArrayList", "tick: $tick")
        mViewModel.quizTitle("$tick", null, "")
//        strArrayList.forEach{
//            if (it.isSelected)
//            it.isSelected = false
//        }

        strTitleArrayList[previPos].isSelected = false
        strTitleArrayList[position].isSelected = true
        mViewDataBinding.recCategories.adapter?.notifyItemChanged(previPos)
        mViewDataBinding.recCategories.adapter?.notifyItemChanged(position)


//        strTitleArrayList.forEach { it.isSelected = false }
//        strTitleArrayList[position].isSelected = true
//        quizesTitleAdapter.notifyDataSetChanged()
//        mViewModel.quizTitle("World", null, "normal")
    }

    override fun quizeItem(position: Int) {
        val modelQuiz = strArrayList[position]
        var bundle = arguments
        bundle?.putString("modelQuizId", modelQuiz._id)

        if (bundle == null) {
            bundle = Bundle()
        }

        bundle.putString("quiz_id","${modelQuiz._id}")
        bundle.putString("quiz_country", modelQuiz.country)
        bundle.putString("routeQuiz", "quiz")

        findNavController().navigate(R.id.playQuizFragment, bundle,options)
    }

    /*   override fun quizeItem() {
           findNavController().navigate(R.id.action_quizesFragment_to_singleQuizFragment)
       }*/

}

interface QuizesInterface {
    fun quizTitle(position: Int, previPos: Int)
    fun quizeItem(position: Int)
}

@Keep
data class TitleData(
    val value: String,
    var isSelected: Boolean
)
