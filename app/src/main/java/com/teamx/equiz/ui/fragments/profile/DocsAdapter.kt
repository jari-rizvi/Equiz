package com.teamx.equiz.ui.fragments.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamx.equiz.MainApplication
import com.teamx.equiz.data.models.editProfile.IdentityDocument
import com.teamx.equiz.databinding.ItemDocsBinding


class DocsAdapter(
    val arrayList: ArrayList<IdentityDocument>
) : RecyclerView.Adapter<DocsAdapter.DocIdentViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocIdentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemDocsBinding = ItemDocsBinding.inflate(inflater, parent, false)
        return DocIdentViewHolder(itemDocsBinding)

    }


    override fun onBindViewHolder(holder: DocIdentViewHolder, position: Int) {

        val list: IdentityDocument = arrayList[position]

        Glide.with(MainApplication.context).load(list.link)
            .into(holder.binding.imageView18);

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class DocIdentViewHolder(itemDocsBinding: ItemDocsBinding) :
        RecyclerView.ViewHolder(itemDocsBinding.root) {
        val binding = itemDocsBinding

    }
}
