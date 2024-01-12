package com.teamx.equiz.ui.fragments.address


import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentAddressListBinding
import com.teamx.equiz.games.games.arr
import com.teamx.equiz.ui.fragments.address.adapter.AddressesListAdapter
import com.teamx.equiz.ui.fragments.address.adapter.OnAddressListener
import com.teamx.equiz.ui.fragments.address.dataclasses.getAddressList.Data
import com.teamx.equiz.utils.DialogHelperClass
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.util.ArrayList

@AndroidEntryPoint
class AddressListFragment : BaseFragment<FragmentAddressListBinding, AddressViewModel>(),
    OnAddressListener {

    override val layoutId: Int
        get() = R.layout.fragment_address_list
    override val viewModel: Class<AddressViewModel>
        get() = AddressViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions
    lateinit var addressAdapter: AddressesListAdapter
    lateinit var addressArrayList: ArrayList<Data>

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
        mViewDataBinding.btnProceed.setOnClickListener {
            findNavController().navigate(R.id.addressFragment, arguments, options)
        }

        mViewModel.getAddressList()

        if (!mViewModel.addressList.hasActiveObservers()) {
            mViewModel.addressList.observe(requireActivity(), Observer {
                when (it.status) {
                    Resource.Status.LOADING -> {
                    }

                    Resource.Status.NOTVERIFY -> {
                        loadingDialog.dismiss()
                    }

                    Resource.Status.SUCCESS -> {
                        loadingDialog.dismiss()
                        it.data?.let { data ->

                            if (addressArrayList.isEmpty()) {
                                mViewDataBinding.emptyTV.visibility = View.VISIBLE
                                mViewDataBinding.addressRecycler.visibility = View.GONE
                            }
                            mViewDataBinding.emptyTV.visibility = View.GONE
                            mViewDataBinding.addressRecycler.visibility = View.VISIBLE


                            data.data.forEach {

                                addressArrayList.add(it)
                            }
                            addressAdapter.notifyDataSetChanged()

                        }
                    }
                    Resource.Status.AUTH -> { loadingDialog.dismiss()
                        onToSignUpPage()
                    }
                    Resource.Status.ERROR -> {
                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
                    }
                }
                if (isAdded) {
                    mViewModel.addressList.removeObservers(viewLifecycleOwner)
                }
            })
        }


//        if (!mViewModel.addressList.hasActiveObservers()) {
//            mViewModel.addressList.observe(requireActivity()) {
//                when (it.status) {
//                    Resource.Status.LOADING -> {
//                        loadingDialog.show()
//                    }
//
//                    Resource.Status.SUCCESS -> {
//                        loadingDialog.dismiss()
//                        it.data?.let { data ->
//
//                            if (addressArrayList.isEmpty()) {
//                                mViewDataBinding.emptyTV.visibility = View.VISIBLE
//                                mViewDataBinding.addressRecycler.visibility = View.GONE
//                            }
//                            mViewDataBinding.emptyTV.visibility = View.GONE
//                            mViewDataBinding.addressRecycler.visibility = View.VISIBLE
//
//
//                            data.data.forEach {
//
//                                addressArrayList.add(it)
//                            }
//                            addressAdapter.notifyDataSetChanged()
//
//                        }
//                    }
//
//                    Resource.Status.ERROR -> {
//                        loadingDialog.dismiss()
//                        DialogHelperClass.errorDialog(requireContext(), it.message!!)
//                    }
//                }
//                if (isAdded) {
//                    mViewModel.addressList.removeObservers(viewLifecycleOwner)
//                }
//            }
//        }

        addressRecyclerview()
    }

    private fun addressRecyclerview() {
        addressArrayList = ArrayList()

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mViewDataBinding.addressRecycler.layoutManager = linearLayoutManager

        addressAdapter = AddressesListAdapter(addressArrayList, this)
        mViewDataBinding.addressRecycler.adapter = addressAdapter

    }

    override fun oneditClick(position: Int) {
    }

    override fun ondeleteClick(position: Int) {
        val id = addressArrayList[position]._id

        mViewModel.deleteAddress(id)
        if (!mViewModel.deleteAddressResponse.hasActiveObservers()) {
            mViewModel.deleteAddressResponse.observe(requireActivity()) {
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
                            addressArrayList.clear()
                            mViewModel.getAddressList()
                        addressAdapter.notifyDataSetChanged()

                        }
                    }

                    Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
                        onToSignUpPage()
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
                    mViewModel.deleteAddressResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }
    }

}
class AddressesClass : ArrayList<Address2>()

@Keep
open class Address2(
    open val label: String,
    open val address: String,
    open val phoneNumber: String
)