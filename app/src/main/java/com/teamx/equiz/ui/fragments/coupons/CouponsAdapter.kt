package com.teamx.equiz.ui.fragments.coupons

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.data.models.coupons.Data
import com.teamx.equiz.databinding.ItemCouponBinding


class CouponsAdapter(
    val arrayList: ArrayList<Data>,
    var coupCallBackCoupon: CallBackCoupon
) : RecyclerView.Adapter<CouponsAdapter.CouponViewHolder>() {
    interface CallBackCoupon {
        fun onClickCoupon(pos: Int, str: String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponsAdapter.CouponViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemCouponBinding = ItemCouponBinding.inflate(inflater, parent, false)
        return CouponsAdapter.CouponViewHolder(itemCouponBinding)

    }

    override fun onBindViewHolder(holder: CouponsAdapter.CouponViewHolder, position: Int) {

        val coupon: Data = arrayList[position]

        holder.binding.textView46.text = coupon.amount.toString()+"% off"


        val o = coupon.createdAt.toString().replaceAfter('T', "").replace("T", "")


        holder.binding.textView47.text = "valid till "+o

        holder.binding.code.text = coupon.code


        holder.itemView.setOnClickListener {}

        holder.binding.code.setOnClickListener {
            Toast.makeText(it.context, "Copied", Toast.LENGTH_SHORT).show()
            coupCallBackCoupon.onClickCoupon(position, coupon.code)
        }


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class CouponViewHolder(itemCouponBinding: ItemCouponBinding) :
        RecyclerView.ViewHolder(itemCouponBinding.root) {
        val binding = itemCouponBinding

    }
}