package com.teamx.equiz.ui.fragments.orders.cancelled

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.MainApplication
import com.teamx.equiz.R
import com.teamx.equiz.data.models.getorderData.Data
import com.teamx.equiz.databinding.ItemCancelledOrderBinding
import com.teamx.equiz.databinding.ItemOrderBinding
import com.teamx.equiz.ui.fragments.orders.OrderListener


class CancelledAdapter(
    val arrayList: ArrayList<Data>,
    private val orderListener: OrderListener
) : RecyclerView.Adapter<CancelledAdapter.TopProductViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTopProductBinding = ItemOrderBinding.inflate(inflater, parent, false)
        return TopProductViewHolder(itemTopProductBinding)

    }


    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: TopProductViewHolder, position: Int) {
        val orders: Data = arrayList[position]


        holder.binding.orderId.text = "Order#"+orders._id
        holder.binding.amount.text = orders.totalPoints.toString()+" Points"
        holder.binding.textView42.text = "Cancelled"
        holder.binding.cartBottom.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                MainApplication.context, R.color.neoRed
            )
        )
        val o = orders.createdAt.toString().replaceAfter('T', "").replace("T", "")

        holder.binding.date.text = o

        holder.itemView.setOnClickListener {
            orderListener.onItemClick(position)
        }



    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class TopProductViewHolder(itemTopProductBinding: ItemOrderBinding) :
        RecyclerView.ViewHolder(itemTopProductBinding.root) {
        val binding = itemTopProductBinding

    }
}