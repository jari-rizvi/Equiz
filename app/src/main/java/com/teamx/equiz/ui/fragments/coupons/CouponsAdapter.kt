package com.teamx.equiz.ui.fragments.coupons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.data.models.coupons.Data
import com.teamx.equiz.databinding.ItemCouponBinding


class CouponsAdapter(
    val arrayList: ArrayList<Data>
) : RecyclerView.Adapter<CouponsAdapter.CouponViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponsAdapter.CouponViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemCouponBinding = ItemCouponBinding.inflate(inflater, parent, false)
        return CouponsAdapter.CouponViewHolder(itemCouponBinding)

    }

    override fun onBindViewHolder(holder: CouponsAdapter.CouponViewHolder, position: Int) {

        val coupon: Data = arrayList[position]

        holder.binding.textView46.text = coupon.amount.toString()+" % off"

        holder.binding.textView47.text = "valid till "+coupon.endDate

        holder.binding.code.text = coupon.code


        holder.itemView.setOnClickListener {}


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class CouponViewHolder(itemCouponBinding: ItemCouponBinding) :
        RecyclerView.ViewHolder(itemCouponBinding.root) {
        val binding = itemCouponBinding

    }
}