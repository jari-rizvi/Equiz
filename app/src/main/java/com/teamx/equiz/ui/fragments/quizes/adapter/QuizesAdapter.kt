package com.teamx.equiz.ui.fragments.quizes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.databinding.ItemQuziesBinding
import com.teamx.equiz.ui.fragments.quizes.QuizesInterface

class QuizesAdapter(
    private val addressArrayList: ArrayList<String>,
    val quizesInterface: QuizesInterface
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

        val arrayData = addressArrayList[position]

        holder.itemView.setOnClickListener {
            quizesInterface.quizeItem()
        }

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class QuizesAdapterViewHolder(private var binding: ItemQuziesBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}