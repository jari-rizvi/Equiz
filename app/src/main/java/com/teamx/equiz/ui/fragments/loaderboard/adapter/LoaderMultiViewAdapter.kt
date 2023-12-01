package com.teamx.equiz.ui.fragments.loaderboard.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.R

class LoaderMultiViewAdapter(
    private val dataSet: ArrayList<Boolean>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_1 = 0
        private const val VIEW_TYPE_2 = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_1 -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_loader_odd,
                    parent,
                    false
                )
                OptionalViewHolder(view)

            }

            VIEW_TYPE_2 -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_loader_even,
                    parent,
                    false
                )
                RequiredViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        when (dataSet[position]) {
            true -> {
                val holderClass2 = holder as OptionalViewHolder

                if (position == 0){
                    holderClass2.textView15456.visibility = View.VISIBLE
                    holderClass2.textView16.visibility = View.GONE
                }else{
                    holderClass2.textView15456.visibility = View.GONE
                    holderClass2.textView16.visibility = View.VISIBLE
                }

                holderClass2.hatlyIcon54545454.isChecked = true

                holderClass2.textView16.text = "${position + 1}"



            }

            false -> {
                val holderClass1 = holder as RequiredViewHolder

                if (position == 0){
                    holderClass1.textView15456.visibility = View.VISIBLE
                    holderClass1.textView16.visibility = View.GONE
                }else{
                    holderClass1.textView15456.visibility = View.GONE
                    holderClass1.textView16.visibility = View.VISIBLE
                }

                holderClass1.hatlyIcon54545454.isChecked = false

                holderClass1.textView16.text = "${position + 1}"


            }
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataSet[position]) {
            VIEW_TYPE_1
        } else {
            VIEW_TYPE_2
        }

    }

    internal class RequiredViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val textView15456: ImageView
        val hatlyIcon54545454: AppCompatCheckedTextView
        val textView16: TextView

        init {
            textView15456 = itemView.findViewById(R.id.textView15456)
            hatlyIcon54545454 = itemView.findViewById(R.id.hatlyIcon54545454)
            textView16 = itemView.findViewById(R.id.textView16)
        }
    }


    internal class OptionalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView15456: ImageView
        val hatlyIcon54545454: AppCompatCheckedTextView
        val textView16: TextView


        init {
            textView15456 = itemView.findViewById(R.id.textView15456)
            hatlyIcon54545454 = itemView.findViewById(R.id.hatlyIcon54545454)
            textView16 = itemView.findViewById(R.id.textView16)
        }
    }
}
