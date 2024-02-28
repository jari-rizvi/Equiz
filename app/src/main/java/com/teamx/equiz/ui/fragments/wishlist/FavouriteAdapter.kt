package com.teamx.equiz.ui.fragments.wishlist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamx.equiz.data.models.wishlistdata.Product
import com.teamx.equiz.databinding.ItemWishlistBinding


class FavouriteAdapter(
    val arrayList: ArrayList<Product>, var wishlistListener: WishListListener
) : RecyclerView.Adapter<FavouriteAdapter.WishListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemWishListBinding = ItemWishlistBinding.inflate(inflater, parent, false)
        return WishListViewHolder(itemWishListBinding)

    }

    override fun onBindViewHolder(holder: WishListViewHolder, position: Int) {

        val list: Product = arrayList[position]

        holder.binding.productName.text = list.title


        Log.d("TAG", "onBindViewHolderWishlist: ${list}")



        holder.binding.qty.text = "${list.quantity.toString()} Qty"

        holder.binding.price.text = "${list.point} Pts"


        if (!list.images.isNullOrEmpty()) {
//            Picasso.get().load(list.images.get(0)).into(holder.binding.productImage)
            Glide.with(holder.binding.productImage.context).load(list.images.get(0))
                .into(holder.binding.productImage)
        }

        holder.binding.textView56.setOnClickListener {
            wishlistListener.onClickItem(position)
        }

        holder.itemView.setOnClickListener {
            wishlistListener.onAddToCart(position)
        }


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class WishListViewHolder(itemWishListBinding: ItemWishlistBinding) :
        RecyclerView.ViewHolder(itemWishListBinding.root) {
        val binding = itemWishListBinding

    }
}

interface WishListListener {
    fun onClickItem(position: Int)
    fun onAddToCart(position: Int)
}