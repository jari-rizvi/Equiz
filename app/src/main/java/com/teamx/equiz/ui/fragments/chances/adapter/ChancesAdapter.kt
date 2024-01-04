package com.teamx.equiz.ui.fragments.chances.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamx.equiz.databinding.ItemChancesBinding
import com.teamx.equiz.ui.fragments.chances.data.ChancesTransaction


class ChancesAdapter(
    val arrayList: ArrayList<ChancesTransaction>,
    private val onTopProductListener: OnChanceListener
) : RecyclerView.Adapter<ChancesAdapter.TopChanceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopChanceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTopProductBinding = ItemChancesBinding.inflate(inflater, parent, false)
        return TopChanceViewHolder(itemTopProductBinding)

    }

    override fun onBindViewHolder(holder: TopChanceViewHolder, position: Int) {


        val product: ChancesTransaction = arrayList[position]

        try {


            holder.binding.textView54.text = product.userId

            holder.binding.textView56.text = "+${product.chances}"
//        holder.binding.textView55.text = product.quizId.title
            holder.binding.textView551.text = product.timestamp.replaceAfter("T", "")


            if (!product.quizId.icon.isNullOrEmpty()) {

//        Picasso.get().load(product.quizId.icon.toString()).into(holder.binding.container)

                Glide.with(holder.binding.container.context).load(product.quizId.icon.toString())
                    .into(holder.binding.container)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }


        holder.itemView.setOnClickListener {
            onTopProductListener.onChanceClick(position)
        }



    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class TopChanceViewHolder(itemProductBinding: ItemChancesBinding) :
        RecyclerView.ViewHolder(itemProductBinding.root) {
        val binding = itemProductBinding

    }
}