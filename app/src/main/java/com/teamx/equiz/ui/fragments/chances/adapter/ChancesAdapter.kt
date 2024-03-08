package com.teamx.equiz.ui.fragments.chances.adapter


import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamx.equiz.databinding.ItemChancesBinding
import com.teamx.equiz.ui.fragments.chances.data.ChancesTransaction
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class ChancesAdapter(
    val arrayList: ArrayList<ChancesTransaction>,
    private val onTopProductListener: OnChanceListener
) : RecyclerView.Adapter<ChancesAdapter.TopChanceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopChanceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTopProductBinding = ItemChancesBinding.inflate(inflater, parent, false)
        return TopChanceViewHolder(itemTopProductBinding)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TopChanceViewHolder, position: Int) {


        val product: ChancesTransaction = arrayList[position]

        try {


            holder.binding.textView54.text = product.quizId.title



            holder.binding.textView56.text = "+${product.chances}"

            if(product.chances == 0){
                holder.binding.textView55.text =
                    "Oops! You did not earn any chance"
            }
            else{

            if (product.chanceType == "Raffle") {
                Log.d("TAG", "onBindViewHolder1212122: ${product.chanceType}")
                holder.binding.textView55.text =
                    "Congratulations! You've won chances in the raffle!"
            }

            if (product.chanceType == "Referral") {
                holder.binding.textView55.text =
                    "Great news! You've earned chances through a referral bonus!"
            }
            if (product.chanceType == "Quiz") {
                holder.binding.textView55.text =
                    "Awesome! You've won a chance by taking part in a quiz!"
            }

            }

            val timestamp = product.timestamp
            val instant = Instant.parse(timestamp)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault())
            val formattedDate = formatter.format(instant)


            holder.binding.textView551.text = formattedDate


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

