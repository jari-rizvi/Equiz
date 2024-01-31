package com.teamx.equiz.ui.fragments.ecommerce.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamx.equiz.data.models.getProducts.Data
import com.teamx.equiz.databinding.ItemProductBinding


class ProductAdapter(
    val arrayList: ArrayList<Data>,
    private val onTopProductListener: OnProductListener
) : RecyclerView.Adapter<ProductAdapter.TopProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTopProductBinding = ItemProductBinding.inflate(inflater, parent, false)
        return TopProductViewHolder(itemTopProductBinding)

    }

    override fun onBindViewHolder(holder: TopProductViewHolder, position: Int) {


        val product: Data = arrayList[position]

        holder.binding.productName.text = product.title

        holder.binding.productPrice.text = "${product.point} Points"
        holder.binding.wishListAddBtn.isChecked = product.isFavorite

//       val quanti = product.qty ?: 1
        /*      if ( product.qty < 1) {
                  product.qty = 1
              }*/


//        Picasso.get().load(product.images[0].toString()).into(holder.binding.img)
        Glide.with(holder.binding.img.context).load(product.images.get(0).toString())
            .into(holder.binding.img)
        /* if(product.isFav){
             Log.d("true", "onBindViewHolder: ${product.isFav}")
             holder.binding.btnFav.setImageResource(R.drawable.wishlist_selected)
         }
         else{
             holder.binding.btnFav.setImageResource(R.drawable.wishlist_circle)
             Log.d("true", "onBindViewHolder: ${product.isFav}")

         }*/

        holder.binding.wishListAddBtn.setOnClickListener {
            onTopProductListener.onAddFavClick(position, product.isFavorite)
        }

        holder.itemView.setOnClickListener {
            onTopProductListener.onproductClick(position)
        }

        /*   holder.binding.btnSchedule.setOnClickListener {
               onTopProductListener.onScheduleClick(position)
           }

           holder.binding.btnFav.setOnClickListener {
               onTopProductListener.onAddFavClick(position,product.isFav)
           }*/


        /*     holder.binding.btnAdd.setOnClickListener {
                 onCartListener?.onAddClickListener(position)
             }

             holder.binding.btnSub.setOnClickListener {
                 onCartListener?.onSubClickListener(position)
             }
             holder.binding.btnBuy.setOnClickListener {
                 product.variation!!.id?.let { it1 -> onCartListener?.onAddToCartListener(it1) }
             }*/

    }

    override fun getItemCount(): Int {
        if(arrayList.size > 4){
            return 4
        }
        else{
            return arrayList.size
        }
    }

    class TopProductViewHolder(itemProductBinding: ItemProductBinding) :
        RecyclerView.ViewHolder(itemProductBinding.root) {
        val binding = itemProductBinding

    }
}