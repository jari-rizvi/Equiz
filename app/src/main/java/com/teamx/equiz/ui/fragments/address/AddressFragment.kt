package com.teamx.equiz.ui.fragments.address

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentAddressBinding
import com.teamx.equiz.ui.fragments.address.dataclasses.ShippingInfo
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.LocationPermission
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import java.io.IOException
import java.util.Locale

@AndroidEntryPoint
class AddressFragment : BaseFragment<FragmentAddressBinding, AddressViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_address
    override val viewModel: Class<AddressViewModel>
        get() = AddressViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel


    private lateinit var options: NavOptions

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

        mViewDataBinding.btnback.setOnClickListener { findNavController().popBackStack() }


        mViewDataBinding.imgLocation.setOnClickListener {

            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )


        }
        mViewDataBinding.btnProceed.setOnClickListener {


            val country = mViewDataBinding.country.text.toString()
            val city = mViewDataBinding.city.text.toString()
            val etPostal = mViewDataBinding.etPostal.text.toString()
            val etState = mViewDataBinding.etState.text.toString()
            val etName = mViewDataBinding.etName.text.toString()
            val etPhone = mViewDataBinding.etPhone.text.toString()
            val address = mViewDataBinding.editAddress1.text.toString()

            val params = JsonObject()
            try {


                params.add(
                    "shippingInfo", Gson().toJsonTree(
                        ShippingInfo(
                            address = address,
                            phoneNumber = etPhone,
                            postalCode = etPostal,
                            city = city,
                            state = etState,
                            country = country,
                        )
                    )
                )

//                params.addProperty("couponCode", "EXTRA69-365-448-1043")

            } catch (e: JSONException) {
                e.printStackTrace()
            }
            if (address.isNullOrEmpty()) {
                showToast("Please add Address")
            } else {
                if (country.isNotEmpty()
                    && city.isNotEmpty()
                    && etPostal.isNotEmpty()
                    && etState.isNotEmpty()
                    && etPhone.isNotEmpty()
                    && address.isNotEmpty()
                ) {

                    mViewModel.createOrder(params)
                }else{
                    showToast("Please add Details")
                }
            }
        }


        if (!mViewModel.createOrderResponse.hasActiveObservers()) {
            mViewModel.createOrderResponse.observe(requireActivity()) {
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

                            var bundle = arguments
                            if (bundle == null) {
                                bundle = Bundle()
                            }
                            bundle.putString("order_id", data.data._id)


                            findNavController().navigate(
                                R.id.paymentMethodsFragment,
                                bundle,
                                options
                            )
                        }
                    }
                    Resource.Status.AUTH -> { loadingDialog.dismiss()
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
                    mViewModel.createOrderResponse.removeObservers(
                        viewLifecycleOwner
                    )
                }
            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.N)
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        if (LocationPermission.requestPermission(requireContext())){
           requestLocation()


        }else{
            Log.d("allowLocation", "locationPermissionRequest: not working")
        }
    }



    @SuppressLint("MissingPermission")
    private fun requestLocation() {

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            // Handle the location result
             if (location != null) {
                 val getAddress = getAddressFromLocation(LatLng(location.latitude, location.longitude))


                 Log.e("requestLocation", "getAddress, ${getAddress}")
            } else {

            }
        }.addOnFailureListener { exception: Exception ->
            // Handle exceptions
            Log.e("requestLocation", "Error getting location, ${exception.message}")

        }

    }


    private fun getAddressFromLocation(latLng: LatLng) {

        val geocoder = Geocoder(requireActivity(), Locale.getDefault())

        try {
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

            if (addresses?.isNotEmpty() == true) {
                val address = addresses[0]
                val addressLine = address.getAddressLine(0)
                val city = address.locality
                val state = address.adminArea
                val country = address.countryName
                val postalCode = address.postalCode


                val addressStr = "$addressLine, $city\n$state, $country, $postalCode"

                mViewDataBinding.editAddress1.setText(addressStr)
                mViewDataBinding.country.setText(country)
                mViewDataBinding.city.setText(city)
                mViewDataBinding.etState.setText(state)
                mViewDataBinding.etPostal.setText(postalCode)

                // Do something with the address information
                Log.d("requestLocation", "addresses: $addresses")
            } else {
                // No address found
                Log.d("requestLocation", "No address found for the given location")


            }
        } catch (e: IOException) {
            // Handle IOException
            Log.e("requestLocation", "Error getting address", e)
        }

    }
}