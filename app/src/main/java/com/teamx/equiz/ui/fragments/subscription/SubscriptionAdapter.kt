package com.teamx.equiz.ui.fragments.subscription

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.MainApplication.Companion.context
import com.teamx.equiz.R
import com.teamx.equiz.databinding.ItemSubscriptionBinding
import com.teamx.equiz.ui.fragments.subscription.plansData.Data


class SubscriptionAdapter(
    val arrayList: ArrayList<Data>,
) : RecyclerView.Adapter<SubscriptionAdapter.SubscriptionViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemSubscriptionBinding = ItemSubscriptionBinding.inflate(inflater, parent, false)
        return SubscriptionViewHolder(itemSubscriptionBinding)

    }


    override fun onBindViewHolder(holder: SubscriptionViewHolder, position: Int) {

        val subs: Data = arrayList[position]


       /* holder.binding.imageView7 .text = "Order#" + orders._id




        holder.itemView.setOnClickListener {
            orderListener.onItemClick(position)
        }*/

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class SubscriptionViewHolder(itemSubscriptionBinding: ItemSubscriptionBinding) :
        RecyclerView.ViewHolder(itemSubscriptionBinding.root) {
        val binding = itemSubscriptionBinding

    }
}