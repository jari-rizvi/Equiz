package com.teamx.equiz.ui.fragments.orders.delivered

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.ViewCompat.setBackgroundTintList
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.MainApplication.Companion.context
import com.teamx.equiz.R
import com.teamx.equiz.data.models.getorderData.Data
import com.teamx.equiz.databinding.ItemOrderBinding
import com.teamx.equiz.ui.fragments.orders.OrderListener


class DeliveredAdapter(
    val arrayList: ArrayList<Data>, private val orderListener: OrderListener
) : RecyclerView.Adapter<DeliveredAdapter.DeliveredOrderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveredOrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemDeliveredOrderBinding = ItemOrderBinding.inflate(inflater, parent, false)
        return DeliveredOrderViewHolder(itemDeliveredOrderBinding)

    }


    override fun onBindViewHolder(holder: DeliveredOrderViewHolder, position: Int) {

        val orders: Data = arrayList[position]


        holder.binding.orderId.text = "Order#" + orders._id
        holder.binding.amount.text = orders.totalPoints.toString()+" Points"
        val o = orders.createdAt.toString().replaceAfter('T', "").replace("T", "")

        holder.binding.textView42.text = "Delivered"

        holder.binding.cartBottom.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.Green))


        holder.binding.date.text = o

        holder.itemView.setOnClickListener {
            orderListener.onItemClick(position)
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class DeliveredOrderViewHolder(itemDeliveredOrderBinding: ItemOrderBinding) :
        RecyclerView.ViewHolder(itemDeliveredOrderBinding.root) {
        val binding = itemDeliveredOrderBinding

    }
}