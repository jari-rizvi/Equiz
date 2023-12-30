package com.teamx.equiz.ui.fragments.collectPrice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamx.equiz.R
import com.teamx.equiz.ui.fragments.collectPrice.data.WinnerData


class ImageSliderAdapter(private val imageList: List<WinnerData>) :
    RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image_slider_prize, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        val it = imageList.get(position)

        if (it.raffle.prize.image.isNotEmpty()) {

            holder.bind(it.raffle.prize.image, it.raffle.prize.title, it.raffle.prize.description)
        }


    }

    override fun getItemCount(): Int = imageList.size

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val imgTxtSup: TextView = itemView.findViewById(R.id.imgTxtSup)
        private val imgTxtSup2: TextView = itemView.findViewById(R.id.imgTxtSup2)

        fun bind(imageResId: String, title: String, supportText: String) {
//            imageView.setImageResource(imageResId)
            imgTxtSup.text = title
            imgTxtSup2.text = supportText
            if (imageResId.isNotEmpty()) {
//                Picasso.get().load(imageResId).into(imageView)

                Glide.with(imageView.context).load(imageResId).into(imageView)
            }
        }
    }
}