package com.teamx.equiz.ui.fragments.loaderboard.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamx.equiz.R
import com.teamx.equiz.data.models.topWinnerData.Game

class LoaderMultiViewAdapter(
    private val dataSet: ArrayList<Game>
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
        val data = dataSet[position]

        when (true/*dataSet[position].bool*/) {
            true -> {
                val holderClass2 = holder as OptionalViewHolder

                if (position == 0) {
                    holderClass2.textView15456?.visibility = View.VISIBLE
                    holderClass2.textView16?.visibility = View.GONE
                } else {
                    holderClass2.textView15456?.visibility = View.GONE
                    holderClass2.textView16?.visibility = View.VISIBLE
                }

//                holderClass2.hatlyIcon54545454.isChecked = true

                holderClass2.textView16?.text = "${position + 1}"
                holderClass2.score?.text = data.userId.wallet.toString()

                holderClass2.name?.text = data.userId.name.toString()

                try {
                    if (data.userId.image.isNotEmpty()) {
//                    Picasso.get().load(data.image).into(holderClass2.hatlyIcon5454)

                        Glide.with(holderClass2.equizIcon5454!!.context).load(data.userId.image)
                            .into(holderClass2.equizIcon5454!!).onLoadFailed(
                                ContextCompat.getDrawable(
                                    holderClass2.equizIcon5454!!.context,
                                    R.drawable.baseline_person
                                )
                            )
                    }
                } catch (e: Exception) {

                }


            }

            false -> {
                val holderClass1 = holder as RequiredViewHolder

                if (position == 0) {
                    holderClass1.textView15456?.visibility = View.VISIBLE
                    holderClass1.textView16?.visibility = View.GONE
                } else {
                    holderClass1.textView15456?.visibility = View.GONE
                    holderClass1.textView16?.visibility = View.VISIBLE
                }
                try {

                    holderClass1.equizIcon54545454?.isChecked = false

                    holderClass1.textView16?.text = "${position + 1}"
                    holderClass1.score?.text = data.userId.wallet.toString()

                    holderClass1.name?.text = data.userId.name.toString()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                try {

//                    Picasso.get().load(data.image).into(holderClass1.hatlyIcon5454)
                    Glide.with(holderClass1.equizIcon5454!!.context).load(data.userId.image)
                        .into(holderClass1.equizIcon5454!!)
                } catch (e: Exception) {
                    e.printStackTrace()
                }


            }
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (true/*dataSet[position].bool*/) {
            VIEW_TYPE_1
        } else {
            VIEW_TYPE_2
        }

    }

    internal class RequiredViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var textView15456: ImageView? = null
        var equizIcon5454: ImageView? = null
        var equizIcon54545454: AppCompatCheckedTextView? = null
        var textView16: TextView? = null
        var name: TextView? = null
        var score: TextView? = null


        init {
            textView15456 = itemView.findViewById(R.id.textView15456)
            equizIcon54545454 = itemView.findViewById(R.id.hatlyIcon54545454)
            equizIcon5454 = itemView.findViewById(R.id.hatlyIcon5454)
            textView16 = itemView.findViewById(R.id.textView16)
            name = itemView.findViewById(R.id.textViewName)
            score = itemView.findViewById(R.id.score)

        }
    }


    internal class OptionalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView15456: ImageView? = null
        var equizIcon5454: ImageView? = null
        var equizIcon54545454: AppCompatCheckedTextView? = null
        var textView16: TextView? = null
        var name: TextView? = null
        var score: TextView? = null


        init {
            textView15456 = itemView.findViewById(R.id.textView15456)
            equizIcon5454 = itemView.findViewById(R.id.hatlyIcon5454)
            equizIcon54545454 = itemView.findViewById(R.id.hatlyIcon54545454)
            textView16 = itemView.findViewById(R.id.textView16)
            name = itemView.findViewById(R.id.textViewName)
            score = itemView.findViewById(R.id.score)
        }
    }
}
