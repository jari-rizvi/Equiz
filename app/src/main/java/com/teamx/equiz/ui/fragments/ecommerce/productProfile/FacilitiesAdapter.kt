package com.teamx.equiz.ui.fragments.ecommerce.productProfile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamx.equiz.data.models.getProductById.Facility
import com.teamx.equiz.databinding.ItemFacilitiesBinding


class FacilitiesAdapter(private val arrayList : ArrayList<Facility>):RecyclerView.Adapter<FacilitiesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilitiesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemFacilitiesBinding = ItemFacilitiesBinding.inflate(inflater,parent,false)
        return FacilitiesViewHolder(itemFacilitiesBinding)
    }

    override fun onBindViewHolder(holder: FacilitiesViewHolder, position: Int) {


        if (!arrayList[position].icon.isNullOrEmpty()){
            Glide.with(holder.binding.img).load(arrayList[position].icon.toString()).into(holder.binding.img)
        }


        holder.itemView.setOnClickListener {


        }
    }

    override fun getItemCount(): Int {


        return arrayList.size


    }


}

class FacilitiesViewHolder(itemFacilitiesBinding: ItemFacilitiesBinding): RecyclerView.ViewHolder(itemFacilitiesBinding.root){

    val binding = itemFacilitiesBinding
}