package com.teamx.equiz.ui.fragments.ecommerce.paymentMethods

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.data.models.PaymentMethod
import com.teamx.equiz.databinding.ItemPaymentBinding

class PaymentAdapter(
    val arrayList: ArrayList<PaymentMethod>,
    private val onTopSellerListener: OnTopSellerListener
) : RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder>() {

    private var row_index = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemPaymentViewedBinding = ItemPaymentBinding.inflate(inflater, parent, false)
        return PaymentViewHolder(itemPaymentViewedBinding)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val payment: PaymentMethod = arrayList[position]
        holder.binding.paymentName.text = payment.paymentName
        holder.binding.paymentImage.setImageResource(payment.paymentImage)
        holder.binding.paymentType.isChecked = row_index == position
        holder.binding.paymentType.isChecked = payment.value

        if (payment.paymentName.contains("card", true)) {
            holder.binding.paymentImage2.visibility = View.INVISIBLE
            holder.binding.tvAddCard.visibility = View.INVISIBLE
        } else {
            holder.binding.paymentImage2.visibility = View.INVISIBLE
            holder.binding.tvAddCard.visibility = View.INVISIBLE
            holder.binding.tvAddCard.text = ""
        }

//        val StipeCardVal = PrefHelper.getInstance(context).getStripeName


//        if (StipeCardVal.isNullOrEmpty()) {
//            holder.binding.tvAddCard.text = ""
//
//        } else {
//            holder.binding.tvAddCard.text = "xxxx-xxxx-xxxx-" + StipeCardVal.toString()
//        }


        holder.binding.paymentImage2.setOnClickListener {
//            onTopSellerListener.onTopSellerClick(position, payment.paymentName)

        }

        holder.itemView.setOnClickListener {
            onTopSellerListener.onTopSellerSelectClick(position, payment.paymentName)
//            row_index = position
//
//            notifyDataSetChanged()
        }

    }


    override fun getItemCount(): Int {
        return arrayList.size
    }

    class PaymentViewHolder(itemPaymentViewedBinding: ItemPaymentBinding) :
        RecyclerView.ViewHolder(itemPaymentViewedBinding.root) {

        val binding = itemPaymentViewedBinding


    }

}