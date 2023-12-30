package com.teamx.equiz.ui.fragments.coupons


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
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
import androidx.activity.addCallback
@AndroidEntryPoint
class CoupnsFragment : BaseFragment<FragmentCouponsBinding, CouponsViewModel>(),CouponsAdapter.CallBackCoupon  {

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
        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }

        mViewModel.getCoupons()

        if (!mViewModel.getcouponsResponse.hasActiveObservers()) {
            mViewModel.getcouponsResponse.observe(requireActivity()) {
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
                        /*    data.data.forEach {
                                couponsArrayList.add(it)
                            }*/

                            mViewDataBinding.shimmerLayout.visibility = View.GONE
                            mViewDataBinding.root.visibility = View.VISIBLE

                            mViewDataBinding.shimmerLayout.stopShimmer()

                            data.data.forEach {
                                if (it != null) {
                                    couponsArrayList.add(it)
                                }
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

        couponsAdapter = CouponsAdapter(couponsArrayList,this)
        mViewDataBinding.recyCoupons.adapter = couponsAdapter

    }

    override fun onClickCoupon(pos: Int, str: String) {
        val clipboard: ClipboardManager? =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("io", str)
        clipboard!!.setPrimaryClip(clip)
    }

}