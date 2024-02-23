package com.teamx.equiz.ui.fragments.wallet

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.MainApplication.Companion.context
import com.teamx.equiz.R
import com.teamx.equiz.data.models.getwalletData.Transaction
import com.teamx.equiz.databinding.ItemWalletBinding


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



//        holder.binding.textView55.text = "You won points by participating in a quiz"

//        holder.binding.textView55.text = "${ wallet.pointType}"

        val formattedNumber = String.format("%.2f", wallet.points)

        if(wallet.pointType == "spend"){
            holder.binding.textView54.text = "Order"
            holder.binding.textView55.text = "You spent wallet points"
            holder.binding.textView56.text = "-"+formattedNumber+" Pts"
            holder.binding.textView56.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        else/*(wallet.pointType == "spend")*/{
            holder.binding.textView54.text = "Top-up"
             holder.binding.textView55.text = "You have gained"
             holder.binding.textView56.text = "+"+formattedNumber+" Pts"
             holder.binding.textView56.setTextColor(ContextCompat.getColor(context, R.color.Green));
         }


        holder.binding.textView13.text = wallet.timestamp.dropLast(14)
        Log.d("TAG", " wallet.timestamp: ${wallet.timestamp}")


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