package com.teamx.equiz.ui.fragments.ecommerce.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.databinding.ItemCategoriesBinding
import com.teamx.equiz.ui.fragments.ecommerce.data.Category

class CategoriesAdapter(
    val arrayList: ArrayList<Category>,
    val onTopCategoriesListener: OnTopCategoriesListener
) : RecyclerView.Adapter<CategoriesAdapter.TopCategoriesViewHolder>() {

    var prevPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopCategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTopCategoriesBinding = ItemCategoriesBinding.inflate(inflater, parent, false)
        return TopCategoriesViewHolder(itemTopCategoriesBinding)
    }

    override fun onBindViewHolder(holder: TopCategoriesViewHolder, position: Int) {
        val categories: Category = arrayList[position]

        if (categories.isChecked) {
            prevPosition = position
        }

        holder.binding.checkedTextView.text = categories.name
        holder.binding.checkedTextView.isChecked = categories.isChecked


        holder.itemView.setOnClickListener {
            onTopCategoriesListener.onTopSellerClick(position, prevPosition)
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class TopCategoriesViewHolder(itemCategoriesBinding: ItemCategoriesBinding) : RecyclerView.ViewHolder(itemCategoriesBinding.root){
        val binding =itemCategoriesBinding

    }
}