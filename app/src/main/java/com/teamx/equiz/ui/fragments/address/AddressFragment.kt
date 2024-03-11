package com.teamx.equiz.ui.fragments.address

import android.R
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.hbb20.CountryCodePicker
import com.teamx.equiz.BR
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.databinding.FragmentAddressBinding
import com.teamx.equiz.utils.DialogHelperClass
import com.teamx.equiz.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notifyAll
import org.json.JSONException
import java.util.Locale


@AndroidEntryPoint
class AddressFragment : BaseFragment<FragmentAddressBinding, AddressViewModel>(),
    CountryCodePicker.OnCountryChangeListener {

    override val layoutId: Int
        get() = com.teamx.equiz.R.layout.fragment_address
    override val viewModel: Class<AddressViewModel>
        get() = AddressViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel
    private lateinit var options: NavOptions
    lateinit var addressId: String
    private var countryName = "United Arab Emirates"
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
                enter = com.teamx.equiz.R.anim.enter_from_left
                exit = com.teamx.equiz.R.anim.exit_to_left
                popEnter = com.teamx.equiz.R.anim.nav_default_pop_enter_anim
                popExit = com.teamx.equiz.R.anim.nav_default_pop_exit_anim
            }
        }
//        txtBottomLocation = view.findViewById(R.id.editAddress1)

        var bundle = arguments

        if (bundle == null) {
            bundle = Bundle()
        }

        addressId = bundle.getString("id").toString()
        Log.d("TAG", "addressId: $addressId")


        citiesArray = ArrayList()

//        val khArray = getStringArrayByName(mViewDataBinding.countryCode.defaultCountryName)


        /*getStringArrayByName(mViewDataBinding.countryCode.defaultCountryName)?.let {
            citiesArray.addAll(
                it
            )
        }*/




        onCountrySelected()

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
                            mViewDataBinding.countryCode.setCountryForNameCode(data.data.country)


                            Log.d("TAG", "onViewCrea121212ted: ${data.data.address}")
                        } catch (e: Exception) {
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

        ccp = mViewDataBinding.countryCode
        ccp!!.setOnCountryChangeListener(this)


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
                                com.teamx.equiz.R.id.paymentMethodsFragment,
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
        val city = mViewDataBinding.editAddressCity.text.toString()
        val country = countryName


        if (!label!!.isEmpty() || !address!!.isEmpty() || !phoneNumber!!.isEmpty() || !city!!.isEmpty() || !country!!.isEmpty()) {

            val addressses = Address2(
                label = label,
                address = address,
                phoneNumber = phoneNumber,
                city = city,
                country = countryName.toString()
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
                mViewDataBinding.root.snackbar(getString(com.teamx.equiz.R.string.enter_label))
            }
            return false
        }

        if (mViewDataBinding.etPhone.text.toString().trim().isEmpty()) {
            if (isAdded) {
                mViewDataBinding.root.snackbar(getString(com.teamx.equiz.R.string.enter_phone))
            }

            return false
        }
        if (mViewDataBinding.editAddress1.text.toString().trim().isEmpty()) {
            if (isAdded) {
                mViewDataBinding.root.snackbar(getString(com.teamx.equiz.R.string.enter_address))
            }
            return false
        }
        if (mViewDataBinding.editAddressCity.text.toString().trim().isEmpty()) {
            if (isAdded) {
                mViewDataBinding.root.snackbar(getString(com.teamx.equiz.R.string.enter_city))
            }
            return false
        }

        var phoneNumber: String = mViewDataBinding.etPhone.getText().toString().trim()

        if (phoneNumber.startsWith("0")) {
            // If it starts with 0, replace 0 with +
            phoneNumber = " +" + phoneNumber.substring(1);


            mViewDataBinding.root.snackbar(getString(com.teamx.equiz.R.string.start_with_plus) + phoneNumber)

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
    override fun onCountrySelected() {
        countryName = ccp?.selectedCountryName?:"United Arab Emirates"


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
                    R.layout.simple_dropdown_item_1line,
                    citiesArray
                )
                mViewDataBinding.editAddressCity.threshold = 0
                mViewDataBinding.editAddressCity.setAdapter(citiesAdapter)
                Log.d("countryCode", "getStringArrayByName: $citiesArray")

            }


        }

    }

    fun getCountryCode(countryName: String) =
        Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }

}