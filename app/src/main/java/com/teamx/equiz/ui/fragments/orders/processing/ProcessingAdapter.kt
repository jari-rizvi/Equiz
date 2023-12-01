package com.teamx.equiz.ui.fragments.orders.processing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.data.models.getorderData.Data
import com.teamx.equiz.databinding.ItemOrderBinding


class ProcessingAdapter(
    val arrayList: ArrayList<Data>) : RecyclerView.Adapter<ProcessingAdapter.TopProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTopProductBinding = ItemOrderBinding.inflate(inflater, parent, false)
        return TopProductViewHolder(itemTopProductBinding)

    }


    override fun onBindViewHolder(holder: TopProductViewHolder, position: Int) {

        val orders: Data = arrayList[position]


        holder.binding.orderId.text = "Order#"+orders._id
        holder.binding.amount.text = orders.totalPoints.toString()
        val o = orders.createdAt.toString().replaceAfter('T', "").replace("T", "")

        holder.binding.date.text = o
//        holder.binding.productName.text = orders.


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class TopProductViewHolder(itemTopProductBinding: ItemOrderBinding) :
        RecyclerView.ViewHolder(itemTopProductBinding.root) {
        val binding = itemTopProductBinding

    }
}