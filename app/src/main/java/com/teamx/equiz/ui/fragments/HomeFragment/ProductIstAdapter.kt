package com.teamx.equiz.ui.fragments.HomeFragment

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.R
import com.teamx.equiz.data.models.ProductModel
import com.teamx.equiz.databinding.ItemProductBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class ProductIstAdapter(val onItemClickListener: OnItemClickListener) :
    ListAdapter<ProductModel, ProductIstAdapter.ViewHolder>(DiffCallBack()) {

    var lastSelectedPostion = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductIstAdapter.ViewHolder {
        return ViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ProductIstAdapter.ViewHolder, position: Int) {
        holder.bind.productModel = getItem(position)
        if (!TextUtils.isEmpty(getItem(position).image)) {
            Picasso.get().load(getItem(position).image).into(holder.bind.thumbNail,object :Callback{
                override fun onSuccess() {
                    holder.bind.spinKit.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    holder.bind.spinKit.visibility = View.GONE
                }

            })
        } else {
            Picasso.get().load(R.mipmap.ic_launcher).into(holder.bind.thumbNail)
        }

        holder.itemView.setOnClickListener {
            lastSelectedPostion = position
            onItemClickListener.onItemClick(position, getItem(position))
        }
    }

    class ViewHolder(private var binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val bind = binding
    }

    class DiffCallBack : DiffUtil.ItemCallback<ProductModel>() {

        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.category.equals(newItem.category) &&
                    oldItem.description.equals(newItem.description) &&
                    oldItem.image.equals(newItem.image) &&
                    oldItem.price.equals(newItem.price) &&
                    oldItem.title.equals(newItem.title)
        }

    }

    interface OnItemClickListener {
        fun onItemClick(itemPosition: Int, musicModel: ProductModel)
    }

}