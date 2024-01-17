package com.teamx.equiz.ui.fragments.address


import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.annotation.Keep
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.models.getorderData.ShippingInfo2
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentAddressListBinding
import com.teamx.equiz.ui.fragments.address.adapter.AddressesListAdapter
import com.teamx.equiz.ui.fragments.address.adapter.OnAddressListener
import com.teamx.equiz.ui.fragments.address.dataclasses.getAddressList.Data
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException

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

        mViewDataBinding.addAddress.setOnClickListener {
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


        mViewDataBinding.btnProceed.visibility = View.GONE

//                mViewDataBinding.btnProceed.setOnClickListener {
//                    if (singleAddress == null) {
//                        mViewDataBinding.root.snackbar("Please select address")
//                    } else {
//
//                        val bundle = arguments
//
//                        val couponCode = bundle?.getString("couponCode") ?: ""
//
//
//                        val label = singleAddress?.label ?: ""
//                        val etPhone = singleAddress?.phoneNumber ?: ""
//                        val address = singleAddress?.address ?: ""
//
//                        val params = JsonObject()
//                        try {
//
//
//                            params.add(
//                                "shippingInfo", Gson().toJsonTree(
//                                    ShippingInfo2(
//                                        address = address,
//                                        phoneNumber = etPhone,
//                                        label = label
//                                    )
//                                )
//                            )
//
//                            if (couponCode.isNotEmpty()) {
//                                params.addProperty("couponCode", couponCode)
//                            }
//
//
//                        } catch (e: JSONException) {
//                            e.printStackTrace()
//                        }
//                        if (address.isNullOrEmpty()) {
//                            showToast("Please add Address")
//                        } else {
//                            if (etPhone.isNotEmpty()
//                                && address.isNotEmpty()
//                            ) {
//
//                                mViewModel.createOrder(params)
//                            } else {
//                                showToast("Please add Details")
//                            }
//                        }
//
//
//                        if (!mViewModel.createOrderResponse.hasActiveObservers()) {
//                            mViewModel.createOrderResponse.observe(requireActivity()) {
//                                when (it.status) {
//                                    Resource.Status.LOADING -> {
//                                        loadingDialog.show()
//                                    }
//
//                                    Resource.Status.NOTVERIFY -> {
//                                        loadingDialog.dismiss()
//                                    }
//
//                                    Resource.Status.SUCCESS -> {
//                                        loadingDialog.dismiss()
//                                        it.data?.let { data ->
//
//                                            var bundle = arguments
//                                            if (bundle == null) {
//                                                bundle = Bundle()
//                                            }
//                                            bundle!!.putString("order_id", data.data._id)
//
//
//                                            findNavController().navigate(
//                                                R.id.paymentMethodsFragment,
//                                                bundle,
//                                                options
//                                            )
//                                        }
//                                    }
//
//
//                                    Resource.Status.AUTH -> {
//                                        loadingDialog.dismiss()
//                                        if (isAdded) {
//                                            try {
//                                                onToSignUpPage()
//                                            } catch (e: Exception) {
//                                                e.printStackTrace()
//                                            }
//                                        }
//                                    }
//
//                                    Resource.Status.ERROR -> {
//                                        loadingDialog.dismiss()
//                                        DialogHelperClass.errorDialog(
//                                            requireContext(),
//                                            it.message!!
//                                        )
//                                    }
//                                }
//                                if (isAdded) {
//                                    mViewModel.createOrderResponse.removeObservers(
//                                        viewLifecycleOwner
//                                    )
//                                }
//                            }
//                        }
//
//
//                    }
//                }


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

        val id = addressArrayList[position]._id
        val bundle = Bundle()
        bundle.putString("id", id)
        findNavController().navigate(R.id.addressEditFragment2, bundle, options)

    }

    override fun onCheckClick(position: Int) {

        addressArrayList.forEach {
            it.value = false
        }

        addressArrayList[position].value = true

        singleAddress = addressArrayList.get(position)


        addressAdapter.notifyDataSetChanged()

    }

    private var singleAddress: Data? = null

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

//@Keep
//class AddressesClass : ArrayList<Address2>()
//
//@Keep
//open class Address2(
//    open val label: String,
//    open val address: String,
//    open val phoneNumber: String
//)