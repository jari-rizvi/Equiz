package com.teamx.equiz.ui.fragments.ecommerce.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.data.models.categoriesData.Data
import com.teamx.equiz.databinding.ItemCategoriesBinding

class CategoriesAdapter(val arrayList: ArrayList<Data>, val onTopCategoriesListener: OnTopCategoriesListener) : RecyclerView.Adapter<CategoriesAdapter.TopCategoriesViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopCategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTopCategoriesBinding = ItemCategoriesBinding.inflate(inflater,parent,false)
        return TopCategoriesViewHolder(itemTopCategoriesBinding)
    }

    override fun onBindViewHolder(holder: TopCategoriesViewHolder, position: Int) {
        val categories : Data = arrayList[position]

        holder.binding.checkedTextView.text = categories.name
        holder.binding.checkedTextView.isChecked = categories.isChecked


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