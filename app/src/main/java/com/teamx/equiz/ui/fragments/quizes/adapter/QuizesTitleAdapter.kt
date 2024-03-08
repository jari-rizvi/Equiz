package com.teamx.equiz.ui.fragments.quizes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.databinding.ItemQuizesTitleBinding
import com.teamx.equiz.ui.fragments.quizes.QuizesInterface
import com.teamx.equiz.ui.fragments.quizes.TitleData

class QuizesTitleAdapter(
    private val addressArrayList: ArrayList<TitleData>,
    val quizesInterface: QuizesInterface
) :
    RecyclerView.Adapter<QuizesTitleAdapterViewHolder>() {

    var previous = 1
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

        if (arrayData.isSelected) {
            previous = position
        }
        holder.bind.txtTitle.text = arrayData.value

        holder.bind.txtTitle.isChecked = arrayData.isSelected

        holder.itemView.setOnClickListener {
            quizesInterface.quizTitle(position, previous)
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