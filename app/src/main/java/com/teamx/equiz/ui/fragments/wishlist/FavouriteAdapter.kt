package com.teamx.equiz.ui.fragments.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.equiz.data.models.wishlistdata.Product
import com.teamx.equiz.databinding.ItemWishlistBinding


class FavouriteAdapter(
    val arrayList: ArrayList<Product>
) : RecyclerView.Adapter<FavouriteAdapter.WishListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemWishListBinding = ItemWishlistBinding.inflate(inflater, parent, false)
        return WishListViewHolder(itemWishListBinding)

    }

    override fun onBindViewHolder(holder: WishListViewHolder, position: Int) {

        val list: Product = arrayList[position]

        holder.binding.productName.text = list.slug

        holder.binding.qty.text = list.quantity.toString()

        holder.binding.price.text = list.price.toString()

        Picasso.get().load(list.icon).into(holder.binding.productImage)

        holder.itemView.setOnClickListener {}


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class WishListViewHolder(itemWishListBinding: ItemWishlistBinding) :
        RecyclerView.ViewHolder(itemWishListBinding.root) {
        val binding = itemWishListBinding

    }
}