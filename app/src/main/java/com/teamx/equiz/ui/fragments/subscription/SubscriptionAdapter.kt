package com.teamx.equiz.ui.fragments.subscription

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamx.equiz.MainApplication.Companion.context
import com.teamx.equiz.databinding.ItemSubscriptionBinding
import com.teamx.equiz.ui.fragments.subscription.catPlansData.Data


class SubscriptionAdapter(
    val arrayList: ArrayList<Data>,
    var onSubsClick: onSubsClick
) : RecyclerView.Adapter<SubscriptionAdapter.SubscriptionViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemSubscriptionBinding = ItemSubscriptionBinding.inflate(inflater, parent, false)
        return SubscriptionViewHolder(itemSubscriptionBinding)

    }


    override fun onBindViewHolder(holder: SubscriptionViewHolder, position: Int) {

        val subs: Data = arrayList[position]

        Glide.with(context).load(subs.image).into(holder.binding.imageView7)

        Log.d("TAG", "onBindViewHolder: ${subs.image}")


        holder.binding.textView58.text = subs.title
        subs.plans.filter { it.isBase }.forEach {
            holder.binding.textView61.text =
                it.price.toString()
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