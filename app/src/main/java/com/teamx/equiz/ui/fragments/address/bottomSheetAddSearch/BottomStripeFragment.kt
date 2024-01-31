package com.teamx.equiz.ui.fragments.address.bottomSheetAddSearch

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.JsonObject
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.stripe.android.view.CardInputListener
import com.stripe.android.view.CardInputWidget
import com.teamx.equiz.R
import com.teamx.equiz.ui.fragments.address.adapter.AddressListAdapter
import com.teamx.equiz.ui.fragments.address.adapter.ProductPreviewInterface
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException

@AndroidEntryPoint
class BottomStripeFragment : BottomSheetDialogFragment() {


    private var bottomSheetStripeListener: BottomSheetStripeListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet_stripe, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardInputWidget: CardInputWidget = view.findViewById(R.id.cardInputWidget)
        val btnPay: Button = view.findViewById(R.id.btnPay)

        btnPay.setOnClickListener {


            val cardParams = cardInputWidget.paymentMethodCreateParams

            if (cardParams == null) {
                Toast.makeText(requireContext(), "Please add details", Toast.LENGTH_SHORT).show()
            } else {
                bottomSheetStripeListener?.onBtnPay(cardInputWidget)
            }
        }


    }


    fun initInterface(listner: BottomSheetStripeListener) {
        bottomSheetStripeListener = listner

    }

}



interface BottomSheetStripeListener {
    fun onBtnPay(cardInputWidget: CardInputWidget)
}