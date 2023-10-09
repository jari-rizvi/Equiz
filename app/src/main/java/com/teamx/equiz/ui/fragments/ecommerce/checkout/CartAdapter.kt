package com.teamx.equiz.ui.fragments.ecommerce.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.equiz.data.models.getcart.Data
import com.teamx.equiz.databinding.ItemCartBinding

class CartAdapter(var arrayList: ArrayList<Data>, val onCartListener: OnCartListener) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            ItemCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {


        val cart: Data = arrayList[position]
        holder.binding.productName.text = try {
            cart.product.title
        } catch (e: Exception) {
            ""
        }

        holder.binding.productQuantity.text = try {
            "${cart.quantity}"
        } catch (e: Exception) {
            ""
        }
        holder.binding.productprice.text = try {
            "${cart.product.price.toString()}"
        } catch (e: Exception) {
            ""
        }

        try {

            Picasso.get().load(cart.product.images[0].toString()).into(holder.binding.img)
        } catch (e: Exception) {

        }

            holder.binding.btnDelete.setOnClickListener {
                onCartListener?.onRemoveToCartListener(position)
            }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


    class CartViewHolder(var itemCartBinding: ItemCartBinding) :
        RecyclerView.ViewHolder(itemCartBinding.root) {

        val binding = itemCartBinding

    }
}