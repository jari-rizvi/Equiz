package com.teamx.equiz.ui.fragments.orders.processing

import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.R
import com.teamx.equiz.BR
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.FragmentProcessingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProcessingFragment : BaseFragment<FragmentProcessingBinding, ProcessingViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_processing
    override val viewModel: Class<ProcessingViewModel>
        get() = ProcessingViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions

    lateinit var productAdapter: ProcessingAdapter

    lateinit var productArrayList: ArrayList<String>

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

        productRecyclerview()

        productArrayList.add("")
        productArrayList.add("")
        productArrayList.add("")
        productArrayList.add("")

        productAdapter.notifyDataSetChanged()

    }


    private fun productRecyclerview() {
        productArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.ProcessingRecyclerView.layoutManager = linearLayoutManager

        productAdapter = ProcessingAdapter(productArrayList)
        mViewDataBinding.ProcessingRecyclerView.adapter = productAdapter

    }


}