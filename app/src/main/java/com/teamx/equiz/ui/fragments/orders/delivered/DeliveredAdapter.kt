package com.teamx.equiz.ui.fragments.orders.delivered

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.databinding.ItemOrderBinding


class DeliveredAdapter(
    val arrayList: ArrayList<String>) : RecyclerView.Adapter<DeliveredAdapter.DeliveredOrderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveredOrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemDeliveredOrderBinding = ItemOrderBinding.inflate(inflater, parent, false)
        return DeliveredOrderViewHolder(itemDeliveredOrderBinding)

    }


    override fun onBindViewHolder(holder: DeliveredOrderViewHolder, position: Int) {


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class DeliveredOrderViewHolder(itemDeliveredOrderBinding: ItemOrderBinding) :
        RecyclerView.ViewHolder(itemDeliveredOrderBinding.root) {
        val binding = itemDeliveredOrderBinding

    }
}