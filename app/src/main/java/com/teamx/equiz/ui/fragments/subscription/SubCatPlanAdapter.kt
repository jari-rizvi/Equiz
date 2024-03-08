package com.teamx.equiz.ui.fragments.subscription

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.databinding.ItemPlanCatBinding
import com.teamx.equiz.databinding.ItemSubCatPlansBinding
import com.teamx.equiz.ui.fragments.subscription.catPlanById.Attribute
import com.teamx.equiz.ui.fragments.subscription.catPlanById.Plan


class SubCatPlanAdapter(
    val arrayList: ArrayList<Attribute>
) : RecyclerView.Adapter<SubCatPlanAdapter.SubCatPlanViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCatPlanViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemSubCaBinding = ItemSubCatPlansBinding.inflate(inflater, parent, false)
        return SubCatPlanViewHolder(itemSubCaBinding)

    }


    override fun onBindViewHolder(holder: SubCatPlanViewHolder, position: Int) {

        val list: Attribute = arrayList[position]

        holder.binding.desc.text = list.description



    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class SubCatPlanViewHolder(itemSubCaBinding: ItemSubCatPlansBinding) :
        RecyclerView.ViewHolder(itemSubCaBinding.root) {
        val binding = itemSubCaBinding

    }
}

