package com.teamx.equiz.ui.fragments.cards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.databinding.ItemCardsBinding
import com.teamx.equiz.ui.fragments.ecommerce.paymentMethods.OnTopSellerListener

class CardsAdapter(
    val arrayList: ArrayList<com.teamx.equiz.ui.fragments.cards.modelcards.PaymentMethod>,
    private val onTopSellerListener: OnTopSellerListener
) : RecyclerView.Adapter<CardsAdapter.PaymentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemCardsViewedBinding = ItemCardsBinding.inflate(inflater, parent, false)
        return PaymentViewHolder(itemCardsViewedBinding)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {

        val payment = arrayList[position]

        holder.binding.paymentName.text = try {
            "**** **** **** ${payment.card.last4}"
        }catch (e:Exception){
            ""
        }

        holder.binding.paymentType.isChecked = payment.default

        holder.binding.paymentType.setOnClickListener {
            onTopSellerListener.onTopSellerClick(position,"")
        }


    }


    override fun getItemCount(): Int {
        return arrayList.size
    }

    class PaymentViewHolder(itemCardsViewedBinding: ItemCardsBinding) :
        RecyclerView.ViewHolder(itemCardsViewedBinding.root) {

        val binding = itemCardsViewedBinding

    }

}