package com.teamx.equiz.ui.fragments.chances


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentChancesBinding
import com.teamx.equiz.ui.fragments.chances.adapter.ChancesAdapter
import com.teamx.equiz.ui.fragments.chances.adapter.OnChanceListener
import com.teamx.equiz.ui.fragments.chances.data.ChancesTransaction
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChancesFragment : BaseFragment<FragmentChancesBinding, ChancesViewModel>(), OnChanceListener {

    override val layoutId: Int
        get() = R.layout.fragment_chances
    override val viewModel: Class<ChancesViewModel>
        get() = ChancesViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions


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

        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }





        mViewModel.getChances()

        if (!mViewModel.getChancesResponse.hasActiveObservers()) {
            mViewModel.getChancesResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }

                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        chancesAdapter.arrayList.clear()
                        it.data?.let { data ->
                            data.chancesTransaction.forEach {
                                chancesAdapter.arrayList.add(it)
                                Log.d("TAG", "onViewCreated1212121212: $it")

                            }


                        }

                        mViewDataBinding.recyclerView.adapter = chancesAdapter
                        mViewDataBinding.recyclerView.adapter?.notifyDataSetChanged()
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
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }
                }
                if (isAdded) {
                    mViewModel.getChancesResponse.removeObservers(viewLifecycleOwner)
                }
            }
        }

        chancesRec()
    }


    lateinit var chancesAdapter: ChancesAdapter
    lateinit var chancesModelData: ArrayList<ChancesTransaction>

    private fun chancesRec() {
        chancesModelData = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context,  RecyclerView.VERTICAL, false)
        mViewDataBinding.recyclerView.layoutManager = linearLayoutManager

        chancesAdapter = ChancesAdapter(chancesModelData, this)
        mViewDataBinding.recyclerView.adapter = chancesAdapter

    }

    override fun onChanceClick(position: Int) {


    }

}