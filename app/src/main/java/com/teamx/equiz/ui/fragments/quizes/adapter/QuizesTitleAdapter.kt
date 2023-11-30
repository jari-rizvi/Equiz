package com.teamx.equiz.ui.fragments.quizes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.databinding.ItemQuizesTitleBinding
import com.teamx.equiz.ui.fragments.quizes.QuizesInterface

class QuizesTitleAdapter(
    private val addressArrayList: ArrayList<String>,
    val quizesInterface: QuizesInterface
) :
    RecyclerView.Adapter<QuizesTitleAdapterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuizesTitleAdapterViewHolder {
        return QuizesTitleAdapterViewHolder(
            ItemQuizesTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: QuizesTitleAdapterViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        holder.itemView.setOnClickListener {
            quizesInterface.quizTitle()
        }

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class QuizesTitleAdapterViewHolder(private var binding: ItemQuizesTitleBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}