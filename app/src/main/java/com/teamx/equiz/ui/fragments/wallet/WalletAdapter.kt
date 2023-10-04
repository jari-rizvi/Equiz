package com.teamx.equiz.ui.fragments.wallet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.equiz.data.models.getwalletData.Transaction
import com.teamx.equiz.data.models.wishlistdata.Product
import com.teamx.equiz.databinding.ItemWalletBinding
import com.teamx.equiz.databinding.ItemWishlistBinding


class WalletAdapter(
    val arrayList: ArrayList<Transaction>
) : RecyclerView.Adapter<WalletAdapter.WalletViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemWalletBinding = ItemWalletBinding.inflate(inflater, parent, false)
        return WalletViewHolder(itemWalletBinding)

    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {

        val wallet: Transaction = arrayList[position]

        holder.binding.textView54.text = "You Earned Points"

        holder.binding.textView55.text = "You won points by participating in a quiz"

        holder.binding.textView13.text = wallet.timestamp.dropLast(18)


        holder.itemView.setOnClickListener {}


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class WalletViewHolder(itemWalletBinding: ItemWalletBinding) :
        RecyclerView.ViewHolder(itemWalletBinding.root) {
        val binding = itemWalletBinding

    }
}