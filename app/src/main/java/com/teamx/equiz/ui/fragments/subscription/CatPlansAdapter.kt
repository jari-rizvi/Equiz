package com.teamx.equiz.ui.fragments.subscription

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.databinding.ItemPlanCatBinding
import com.teamx.equiz.ui.fragments.subscription.catPlanById.Plan
import com.teamx.equiz.utils.PrefHelper


class CatPlansAdapter(
    val arrayList: ArrayList<Plan>,
    val onCatPlanListener: OnCatPlanListener
) : RecyclerView.Adapter<CatPlansAdapter.CatPlanViewHolder>() {

    var previous = 1
    var e_rate = 1
    var cc = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatPlanViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemWishListBinding = ItemPlanCatBinding.inflate(inflater, parent, false)
        return CatPlanViewHolder(itemWishListBinding)

    }


    override fun onBindViewHolder(holder: CatPlanViewHolder, position: Int) {

        val list: Plan = arrayList[position]

        var prefCar2 = PrefHelper.getInstance(holder.itemView.context).getExchangeRate()
        if (prefCar2 == null) {
            prefCar2 = PrefHelper.getInstance(holder.itemView.context).getExchangeRate()
        }
        prefCar2?.let {
            cc = it.CC
            e_rate = it.rate
        }


        if (list.isChecked) {
            previous = position
        }
        holder.binding.textView61.text = cc

        val price = list.price.toInt()
        holder.binding.price.text = (e_rate * price).toString()


        holder.binding.textView60.text = list.name
        holder.binding.textView58.text = "${list.intervalCount} ${list.interval}"
//        holder.binding.price.text = list.price.toString()
        holder.binding.checkedTextView.isChecked = list.isChecked

        holder.itemView.setOnClickListener {
            onCatPlanListener.onPlanClick(position, previous)
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

    fun onPlanClick(position: Int, PrePos: Int)
}
