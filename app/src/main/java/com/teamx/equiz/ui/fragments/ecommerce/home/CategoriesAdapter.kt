package com.teamx.equiz.ui.fragments.ecommerce.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.equiz.data.models.categoriesData.Data
import com.teamx.equiz.data.models.categoriesData.GetAllCategoriesData
import com.teamx.equiz.data.models.wishlistdata.Product
import com.teamx.equiz.databinding.ItemCategoriesBinding
import com.teamx.equiz.databinding.ItemWishlistBinding

class CategoriesAdapter(val arrayList: ArrayList<GetAllCategoriesData>, val onTopCategoriesListener: OnTopCategoriesListener) : RecyclerView.Adapter<CategoriesAdapter.TopCategoriesViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopCategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTopCategoriesBinding = ItemCategoriesBinding.inflate(inflater,parent,false)
        return TopCategoriesViewHolder(itemTopCategoriesBinding)
    }

    override fun onBindViewHolder(holder: TopCategoriesViewHolder, position: Int) {
        val categories : GetAllCategoriesData = arrayList[position]

        holder.binding.checkedTextView.text = categories.data[0].name
        holder.binding.checkedTextView.isChecked = categories.data[0].isChecked


        holder.itemView.setOnClickListener {
            onTopCategoriesListener.onTopSellerClick(position)
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class TopCategoriesViewHolder(itemCategoriesBinding: ItemCategoriesBinding) : RecyclerView.ViewHolder(itemCategoriesBinding.root){
        val binding =itemCategoriesBinding

    }
}