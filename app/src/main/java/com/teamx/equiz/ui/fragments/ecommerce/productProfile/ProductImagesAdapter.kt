package com.teamx.equiz.ui.fragments.ecommerce.productProfile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.teamx.equiz.data.models.bannerData.Data
import com.teamx.equiz.databinding.ItemProductsImagesBinding
import java.net.URL


class ProductImagesAdapter(private val arrayList : ArrayList<com.teamx.equiz.data.models.getProductById.Data>):RecyclerView.Adapter<FeatureProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemFeatureProductBinding = ItemProductsImagesBinding.inflate(inflater,parent,false)
        return FeatureProductViewHolder(itemFeatureProductBinding)
    }

    override fun onBindViewHolder(holder: FeatureProductViewHolder, position: Int) {

//        Picasso.get().load(arrayList[position].images[0]).into(holder.binding.imageView)

        if (!arrayList[position].images[0].isNullOrEmpty()){
            Glide.with(holder.binding.imageView.context).load(arrayList[position].images[0].toString()).into(holder.binding.imageView)
        }


        holder.itemView.setOnClickListener {


        }
    }

    override fun getItemCount(): Int {


        return arrayList.size


    }


}

class FeatureProductViewHolder(itemFeatureProductBinding: ItemProductsImagesBinding): RecyclerView.ViewHolder(itemFeatureProductBinding.root){

    val binding = itemFeatureProductBinding
}