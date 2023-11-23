package com.teamx.equiz.ui.fragments.coupons


import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.coupons.Data
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentCouponsBinding
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoupnsFragment : BaseFragment<FragmentCouponsBinding, CouponsViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_coupons
    override val viewModel: Class<CouponsViewModel>
        get() = CouponsViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    lateinit var couponsAdapter: CouponsAdapter
    lateinit var couponsArrayList: ArrayList<Data>
    private lateinit var options: NavOptions

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

  /*      mViewDataBinding.textView9.setOnClickListener {
            findNavController().navigate(R.id.action_walletFragment_to_referralFragment)
        }*/


        mViewModel.getCoupons()

        if (!mViewModel.getcouponsResponse.hasActiveObservers()) {
            mViewModel.getcouponsResponse.observe(requireActivity()) {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        loadingDialog.show()
                    }
                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->
                            data.data.forEach {
                                couponsArrayList.add(it)
                            }

                            couponsAdapter.notifyDataSetChanged()


                        }
                    }

                    Resource.Status.ERROR -> {
                        loadingDialog.dismiss()
                        DialogHelperClass.errorDialog(
                            requireContext(),
                            it.message!!
                        )
                    }
                }
                if (isAdded) {
                    mViewModel.getcouponsResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }

        couponsRecyclerview()
    }

    private fun couponsRecyclerview() {
        couponsArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.recyCoupons.layoutManager = linearLayoutManager

        couponsAdapter = CouponsAdapter(couponsArrayList)
        mViewDataBinding.recyCoupons.adapter = couponsAdapter

    }

}