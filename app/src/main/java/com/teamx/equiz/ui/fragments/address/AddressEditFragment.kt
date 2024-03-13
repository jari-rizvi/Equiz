package com.teamx.equiz.ui.fragments.address

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.gson.JsonObject
import com.hbb20.CountryCodePicker
import com.stripe.android.core.model.getCountryCode
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentEditAddressBinding
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import java.util.Locale


@AndroidEntryPoint
class   AddressEditFragment : BaseFragment<FragmentEditAddressBinding, AddressViewModel>(),CountryCodePicker.OnCountryChangeListener{

    override val layoutId: Int
        get() = R.layout.fragment_edit_address
    override val viewModel: Class<AddressViewModel>
        get() = AddressViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions

    lateinit var addressId: String
    private var countryName = ""
    private var ccp: CountryCodePicker? = null
    lateinit var citiesArray: ArrayList<String>
    lateinit var citiesAdapter: ArrayAdapter<String>


    private fun getStringArrayByName(arrayName: String): Array<String>? {
        val resId = resources.getIdentifier(arrayName, "array", requireContext().packageName)
        return if (resId != 0) {
            resources.getStringArray(resId)
        } else {
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
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


        ccp = mViewDataBinding.countryCode
        ccp!!.setOnCountryChangeListener(this)

        var bundle = arguments

        if (bundle == null) {
            bundle = Bundle()
        }


        addressId = bundle.getString("id").toString()
        Log.d("TAG", "addressId: $addressId")

        mViewModel.addresById(addressId)

            mViewModel.addresByIdResponse.observe(requireActivity()) {
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
                            try {
                                mViewDataBinding.editAddress1.setText(data.data.address)
                                mViewDataBinding.etLabel.setText(data.data.label)
                                mViewDataBinding.etPhone.setText(data.data.phoneNumber)
                                mViewDataBinding.editAddressCity.setText(data.data.city)
                                val coded = getCountryCode(data.data.country)
                                ccp!!.setCountryForNameCode(coded)

                                Log.d("countryCode", "countryCode: ${coded}")
                                Log.d("TAG", "onViewCrea121212ted: ${data.data}")
                            }
                            catch (e:Exception){
                                Log.d("TAG", "onViewCrea121212ted: ${e.message}")
                            }


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
                    mViewModel.addresByIdResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }

        }

        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }



        mViewDataBinding.btnProceed.setOnClickListener {
            isValidate()

        }
        citiesArray = ArrayList()
        onCountrySelected()

    }

    fun getCountryCode(countryName: String) =
        Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }


    override fun onCountrySelected() {
        countryName = ccp?.selectedCountryName?:"United Arab Emirates"
        Log.d("TAG", "onCountrySelected: ${ccp!!.selectedCountryName}")

        getCountryCode(countryName)

        citiesArray.clear()
//        citiesAdapter.clear()
        getCountryCode(countryName)?.let {
            Log.d("countryCode", "onCountrySelected: $it")
            getStringArrayByName(it)?.let {

                citiesArray.addAll(
                    it
                )
                citiesAdapter = ArrayAdapter<String>(
                    requireActivity(),
                    android.R.layout.simple_dropdown_item_1line,
                    citiesArray
                )
                mViewDataBinding.editAddressCity.threshold = 0
                mViewDataBinding.editAddressCity.setAdapter(citiesAdapter)
                Log.d("countryCode", "getStringArrayByName: $citiesArray")

            }


        }

    }

    fun ApiCall() {

        val address = mViewDataBinding.editAddress1.text.toString()
        val phoneNumber = mViewDataBinding.etPhone.text.toString()
        val city = mViewDataBinding.editAddressCity.text.toString()


        if (!address!!.isEmpty() || !phoneNumber!!.isEmpty()) {
            val params = JsonObject()
            try {
                params.addProperty("address", address)
                params.addProperty("addressId", addressId)
                params.addProperty("phoneNumber", phoneNumber)
                params.addProperty("city", city)
                params.addProperty("country", countryName)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            mViewModel.updateAddress(params)

            if (!mViewModel.updateAddressResponse.hasActiveObservers()) {
                mViewModel.updateAddressResponse.observe(requireActivity()) {
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
                                findNavController().popBackStack()

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
                        mViewModel.updateAddressResponse.removeObservers(
                            viewLifecycleOwner
                        )
                    }
                }
            }
        }
    }

    fun isValidate(): Boolean {
        if (mViewDataBinding.etLabel.text.toString().trim().isEmpty()) {
            if (isAdded) {
                mViewDataBinding.root.snackbar(getString(R.string.enter_label))
            }
            return false
        }

        if (mViewDataBinding.etPhone.text.toString().trim().isEmpty()) {
            if (isAdded) {
                mViewDataBinding.root.snackbar(getString(R.string.enter_phone))
            }
            return false
        }
        if (mViewDataBinding.editAddress1.text.toString().trim().isEmpty()) {
            if (isAdded) {
                mViewDataBinding.root.snackbar(getString(R.string.enter_address))
            }
            return false
        }

        ApiCall()
        return true
    }


}