package com.teamx.equiz.ui.fragments.orders.orderdetails

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.teamx.equiz.MainApplication
import com.teamx.equiz.R
import com.teamx.equiz.data.models.getorderData.Data
import com.teamx.equiz.data.models.orderDetailData.ProductDetail
import com.teamx.equiz.databinding.ItemCancelledOrderBinding
import com.teamx.equiz.databinding.ItemOrderBinding
import com.teamx.equiz.databinding.ItemOrderDetailBinding
import com.teamx.equiz.ui.fragments.orders.OrderListener


class ProductsAdapter(
    val arrayList: ArrayList<ProductDetail>
) : RecyclerView.Adapter<ProductsAdapter.OrderDetailViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemOrderDetailBinidng = ItemOrderDetailBinding.inflate(inflater, parent, false)
        return OrderDetailViewHolder(itemOrderDetailBinidng)

    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        val product: ProductDetail = arrayList[position]


        holder.binding.textView54.text =product.product.title
        holder.binding.textView54.text =product.totalPrice.toString()
        holder.binding.textView55.text =product.quantity.toString()
//        Picasso.get().load(arrayList[position].product.images[0]).into(holder.binding.img)
        Glide.with(holder.binding.img.context).load(arrayList[position].product.images[0]).into(holder.binding.img)
        holder.itemView.setOnClickListener {
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class OrderDetailViewHolder(itemOrderDetailBinidng: ItemOrderDetailBinding) :
        RecyclerView.ViewHolder(itemOrderDetailBinidng.root) {
        val binding = itemOrderDetailBinidng

    }
}