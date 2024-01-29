package com.teamx.equiz.ui.fragments.address

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentAddressBinding
import com.teamx.equiz.games.games.arr
import com.teamx.equiz.ui.fragments.address.bottomSheetAddSearch.BottomSheetAddressFragment
import com.teamx.equiz.ui.fragments.address.bottomSheetAddSearch.BottomSheetListener
import com.teamx.equiz.ui.fragments.address.dataclasses.ShippingInfo
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.LocationPermission
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    private lateinit var googleMap: GoogleMap
    private var isMapBeingDragged = true
    private lateinit var bottomSheetAddSearchFragment: BottomSheetAddressFragment
    private lateinit var options: NavOptions
    private var myJob: Job? = null
    lateinit var addressId: String

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
//        txtBottomLocation = view.findViewById(R.id.editAddress1)

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

                                Log.d("TAG", "onViewCrea121212ted: ${data.data.address}")
                            }
                            catch (e:Exception){}


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

//        bottomSheetAddSearchFragment = BottomSheetAddressFragment()
//        bottomSheetAddSearchFragment.setBottomSheetListener(this)


        mViewDataBinding.btnProceed.setOnClickListener {
            isValidate()

        }

        mViewDataBinding.editAddress1.setOnClickListener {

          /*  if (!bottomSheetAddSearchFragment.isAdded) {
                bottomSheetAddSearchFragment.show(
                    parentFragmentManager,
                    bottomSheetAddSearchFragment.tag
                )
            }*/
        }
//        mViewDataBinding.btnProceed.setOnClickListener {
//
//
////            val country = mViewDataBinding.country.text.toString()
////            val city = mViewDataBinding.city.text.toString()
////            val etPostal = mViewDataBinding.etPostal.text.toString()
////            val etState = mViewDataBinding.etState.text.toString()
////            val etName = mViewDataBinding.etName.text.toString()
//            val etPhone = mViewDataBinding.etPhone.text.toString()
//            val address = mViewDataBinding.editAddress1.text.toString()
//
//            val params = JsonObject()
//            try {
//
//
//                params.add(
//                    "shippingInfo", Gson().toJsonTree(
//                        ShippingInfo(
//                            address = address,
//                            phoneNumber = etPhone,
//                            postalCode = "etPostal",
//                            city = "city",
//                            state = "etState",
//                            country = "country",
//                        )
//                    )
//                )
//
////                params.addProperty("couponCode", "EXTRA69-365-448-1043")
//
//            } catch (e: JSONException) {
//                e.printStackTrace()
//            }
//            if (address.isNullOrEmpty()) {
//                showToast("Please add Address")
//            } else {
//                if (etPhone.isNotEmpty()
//                    && address.isNotEmpty()
//                ) {
//
//                    mViewModel.createOrder(params)
//                } else {
//                    showToast("Please add Details")
//                }
//            }
//        }


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
                            bundle!!.putString("order_id", data.data._id)


                            findNavController().navigate(
                                R.id.paymentMethodsFragment,
                                bundle,
                                options
                            )
                        }
                    }

                    Resource.Status.AUTH -> {
                        loadingDialog.dismiss()
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


    fun ApiCall() {

        val label = mViewDataBinding.etLabel.text.toString()
        val address = mViewDataBinding.editAddress1.text.toString()
        val phoneNumber = mViewDataBinding.etPhone.text.toString()


        if (!label!!.isEmpty() || !address!!.isEmpty() || !phoneNumber!!.isEmpty()) {

            val addressses = Address2(
                label = label,
                address = address,
                phoneNumber = phoneNumber
            )
            Log.d("TAG", "labellabellabel: $label")
            val params = JsonObject()
            try {
                params.add(
                    "addresses", Gson().toJsonTree(addressses)
                )
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            mViewModel.addAddress(params)
            if (!mViewModel.addAddressResponse.hasActiveObservers()) {
                mViewModel.addAddressResponse.observe(requireActivity()) {
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
                        mViewModel.addAddressResponse.removeObservers(
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

        var phoneNumber: String = mViewDataBinding.etPhone.getText().toString().trim()

        if (phoneNumber.startsWith("0")) {
            // If it starts with 0, replace 0 with +
            phoneNumber = " +" + phoneNumber.substring(1);


            mViewDataBinding.root.snackbar(getString(R.string.start_with_plus)+phoneNumber)

            return false
        }



        ApiCall()
        return true
    }

//    @RequiresApi(Build.VERSION_CODES.N)
//    val locationPermissionRequest = registerForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions()
//    ) { permissions ->
//
//        if (LocationPermission.requestPermission(requireContext())) {
//            requestLocation()
//
//
//        } else {
//            Log.d("allowLocation", "locationPermissionRequest: not working")
//        }
//    }


    @SuppressLint("MissingPermission")
//    private fun requestLocation() {
//
//        val fusedLocationClient =
//            LocationServices.getFusedLocationProviderClient(requireActivity())
//
//        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
//            // Handle the location result
//            if (location != null) {
//                val getAddress =
//                    getAddressFromLocation(LatLng(location.latitude, location.longitude))
//
//
//                Log.e("requestLocation", "getAddress, ${getAddress}")
//            } else {
//
//            }
//        }.addOnFailureListener { exception: Exception ->
//            // Handle exceptions
//            Log.e("requestLocation", "Error getting location, ${exception.message}")
//
//        }
//
//    }


//    private fun getAddressFromLocation(latLng: LatLng) {
//
//        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
//
//        try {
//            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
//
//            if (addresses?.isNotEmpty() == true) {
//                val address = addresses[0]
//                val addressLine = address.getAddressLine(0)
//                val city = address.locality
//                val state = address.adminArea
//                val country = address.countryName
//                val postalCode = address.postalCode
//
//
//                val addressStr = "$addressLine, $city\n$state, $country, $postalCode"
//
//                mViewDataBinding.editAddress1.setText(addressStr)
////                mViewDataBinding.country.setText(country)
////                mViewDataBinding.city.setText(city)
////                mViewDataBinding.etState.setText(state)
////                mViewDataBinding.etPostal.setText(postalCode)
//
//                // Do something with the address information
//                Log.d("requestLocation", "addresses: $addresses")
//            } else {
//                // No address found
//                Log.d("requestLocation", "No address found for the given location")
//
//
//            }
//        } catch (e: IOException) {
//            // Handle IOException
//            Log.e("requestLocation", "Error getting address", e)
//        }
//
//    }



//    override fun onMapReady(p0: GoogleMap) {
//        googleMap = p0
//
////        getLocationObserver()
//
//        googleMap.setOnCameraIdleListener {
//            myJob?.cancel()
//            myJob = GlobalScope.launch(Dispatchers.Main) {
//                if (isMapBeingDragged) {
//
//                    val currentLatLng = googleMap.cameraPosition.target
////                    mViewDataBinding.locationLoader.visibility = View.VISIBLE
//                    val result = withContext(Dispatchers.IO) {
//                        getAddressFromLocation(currentLatLng)
//                    }
//                    latLngFinal = currentLatLng
////                    mViewDataBinding.locationLoader.visibility = View.GONE
////                    updateUi(result, "", "", "", addressLabel)
//                }
//                isMapBeingDragged = true
//            }
//        }
//    }


//    private lateinit var txtBottomLocation: TextView
//    private var latLngFinal: LatLng? = null
//
//    private fun updateUi(
//        result: String,
//        appartment: String,
//        building: String,
//        additionalInfo: String,
//        label: String
//    ) {
//        mViewDataBinding.editAddress1.setText(result)
//        txtBottomLocation.text = result
//
//
//    }

    private var addressLabel = "Home"

//    override fun onBottomSheetDataReceived(data: String, latLng: LatLng) {
//        isMapBeingDragged = false
//
//        updateUi(data, "", "", "", addressLabel)
//        bottomSheetAddSearchFragment.dismiss()
//
//        latLngFinal = latLng
//
////        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng), 1000, null)
//
////        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
//
//        Log.e("requestLocation", "data, $data")
//        Log.e("requestLocation", "latLng, $latLng")
//    }


}