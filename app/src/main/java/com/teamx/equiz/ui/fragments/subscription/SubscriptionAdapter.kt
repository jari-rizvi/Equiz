package com.teamx.equiz.ui.fragments.subscription

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamx.equiz.MainApplication.Companion.context
import com.teamx.equiz.databinding.ItemSubscriptionBinding
import com.teamx.equiz.ui.fragments.subscription.catPlansData.Data
import com.teamx.equiz.utils.PrefHelper


class SubscriptionAdapter(
    val arrayList: ArrayList<Data>,
    var onSubsClick: onSubsClick
) : RecyclerView.Adapter<SubscriptionAdapter.SubscriptionViewHolder>() {
    var e_rate = 1
    var cc = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemSubscriptionBinding = ItemSubscriptionBinding.inflate(inflater, parent, false)
        return SubscriptionViewHolder(itemSubscriptionBinding)

    }


    override fun onBindViewHolder(holder: SubscriptionViewHolder, position: Int) {

        val subs: Data = arrayList[position]

        var prefCar2 = PrefHelper.getInstance(holder.itemView.context).getExchangeRate()
        if (prefCar2 == null) {
            prefCar2 = PrefHelper.getInstance(holder.itemView.context).getExchangeRate()
        }
        prefCar2?.let {
            cc = it.CC
            e_rate = it.rate
        }

        Glide.with(context).load(subs.image).into(holder.binding.imageView7)

        Log.d("TAG", "onBindViewHolder: ${subs.image}")


        holder.binding.textView58.text = subs.title
        subs.plans.filter { it.isBase }.forEach {
            val price = it.price.toInt()
            holder.binding.textView6111.text = (e_rate * price).toString()
            holder.binding.textView61.text = cc
            holder.binding.textView611.text = "/${it.interval}"


        }


        holder.itemView.setOnClickListener {
            onSubsClick.onSubItemClick(position)
        }


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class SubscriptionViewHolder(itemSubscriptionBinding: ItemSubscriptionBinding) :
        RecyclerView.ViewHolder(itemSubscriptionBinding.root) {
        val binding = itemSubscriptionBinding

    }
}

interface onSubsClick {
    fun onSubItemClick(position: Int)
}