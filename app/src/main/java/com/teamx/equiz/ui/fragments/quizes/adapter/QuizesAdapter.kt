package com.teamx.equiz.ui.fragments.quizes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.data.models.quizTitleData.Data
import com.teamx.equiz.databinding.ItemQuziesBinding
import com.teamx.equiz.ui.fragments.quizes.QuizesInterface
import com.teamx.equiz.utils.snackbar

class QuizesAdapter(
    private val quizArrayList: ArrayList<Data>,
    val quizesInterface: QuizesInterface,
    val isDashBoard : Boolean
) :
    RecyclerView.Adapter<QuizesAdapterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuizesAdapterViewHolder {
        return QuizesAdapterViewHolder(
            ItemQuziesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: QuizesAdapterViewHolder, position: Int) {

        val quizData = quizArrayList[position]


        holder.bind.textView2754.text = "${quizData.country} Quiz"
        holder.bind.textView27.text = quizData.topic

        holder.itemView.setOnClickListener {
            if (quizData.played) {
                try {
                    holder.bind.root.snackbar("Already Played This Quiz, Become a Premium User")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                quizesInterface.quizeItem(position)
            }
        }
        if (quizData.isRush) {
            holder.bind.rushBtn.visibility = View.VISIBLE
        } else {
            holder.bind.rushBtn.visibility = View.INVISIBLE

        }

        if (quizData.played) {
//            holder.bind.root.isClickable = false
            holder.bind.isPlayed.visibility = View.VISIBLE
        } else {
            holder.bind.isPlayed.visibility = View.GONE
//            holder.bind.root.isClickable = true
        }



    }

    override fun getItemCount(): Int {

        if(isDashBoard && quizArrayList.size>2){
            return 3
        }
        else{
            return quizArrayList.size
        }
        
//        return quizArrayList.size
    }
}

class QuizesAdapterViewHolder(private var binding: ItemQuziesBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}