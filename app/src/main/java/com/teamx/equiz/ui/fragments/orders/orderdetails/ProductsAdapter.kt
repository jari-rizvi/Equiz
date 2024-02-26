package com.teamx.equiz.ui.fragments.orders.orderdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamx.equiz.data.models.orderDetailData.ProductDetail
import com.teamx.equiz.databinding.ItemOrderDetailBinding


class ProductsAdapter(
    val arrayList: ArrayList<ProductDetail>,
    private var productCancelListener: ProductCancelListener
) : RecyclerView.Adapter<ProductsAdapter.OrderDetailViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemOrderDetailBinidng = ItemOrderDetailBinding.inflate(inflater, parent, false)
        return OrderDetailViewHolder(itemOrderDetailBinidng)

    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        val product: ProductDetail = arrayList[position]


        holder.binding.textView54.text = product.product.title
        try {
            val doubleValue = product.product.point
            val intValue = doubleValue.toInt()



            holder.binding.textView13.text = intValue.toString() + " Points"
        } catch (e: Exception) {
        }
        val doubleValue1 = product.quantity
        val intValue1 = doubleValue1.toInt()


        holder.binding.textView55.text = "Qty " + intValue1.toString()
//        Picasso.get().load(arrayList[position].product.images[0]).into(holder.binding.img)
        Glide.with(holder.binding.img.context).load(arrayList[position].product.images[0])
            .into(holder.binding.img)
        holder.itemView.setOnClickListener {
        }

        holder.binding.btnCncl.setOnClickListener {
            productCancelListener.onCancelItemClick(position)
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

interface ProductCancelListener {
    fun onCancelItemClick(position: Int)
}