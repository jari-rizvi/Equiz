package com.teamx.equiz.ui.fragments.subscription

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.databinding.ItemPlanCatBinding
import com.teamx.equiz.ui.fragments.subscription.catPlanById.Plan


class CatPlansAdapter(
    val arrayList: ArrayList<Plan>,
    val onCatPlanListener: OnCatPlanListener
) : RecyclerView.Adapter<CatPlansAdapter.CatPlanViewHolder>() {

    var previous = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatPlanViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemWishListBinding = ItemPlanCatBinding.inflate(inflater, parent, false)
        return CatPlanViewHolder(itemWishListBinding)

    }


    override fun onBindViewHolder(holder: CatPlanViewHolder, position: Int) {

        val list: Plan = arrayList[position]


        if (list.isChecked) {
            previous = position
        }

        holder.binding.textView60.text = list.name
        holder.binding.textView58.text = list.interval
        holder.binding.price.text = list.price.toString()
        holder.binding.checkedTextView.isChecked = list.isChecked

        holder.itemView.setOnClickListener {
            onCatPlanListener.onPlanClick(position,previous)
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class CatPlanViewHolder(itemWishListBinding: ItemPlanCatBinding) :
        RecyclerView.ViewHolder(itemWishListBinding.root) {
        val binding = itemWishListBinding

    }
}

interface OnCatPlanListener {

    fun onPlanClick(position : Int,PrePos:Int)
}
