package com.teamx.equiz.ui.fragments.address.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.databinding.ItemAddressBinding
import com.teamx.equiz.ui.fragments.address.dataclasses.getAddressList.Data

class AddressesListAdapter(
    private val addressArrayList: ArrayList<Data>,
    private val onAddressListener: OnAddressListener
) : RecyclerView.Adapter<AddressViewHolder>() {
    private var row_index = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        return AddressViewHolder(
            ItemAddressBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {


        val addressList: Data = addressArrayList[position]


        holder.bind.username.isChecked = row_index == position
        holder.bind.username.isChecked = addressList.value

        holder.bind.username.text = try {
            addressList.label

        } catch (e: Exception) {
            ""
        }
        holder.bind.txtdeliveryAddressHouse.text = try {
            addressList.address
        } catch (e: Exception) {
            ""
        }

        holder.bind.postal.text = try {
            addressList?.phoneNumber
        } catch (e: Exception) {
            ""
        }


        holder.bind.addressDelete.setOnClickListener {
            onAddressListener.ondeleteClick(position)
        }
        holder.bind.addressEditIcon.setOnClickListener {
            onAddressListener.oneditClick(position)

        }

    }

    override fun getItemCount(): Int {
        return addressArrayList.size
    }
}

class AddressViewHolder(private var binding: ItemAddressBinding) :
    RecyclerView.ViewHolder(binding.root) {

    val bind = binding

}

interface OnAddressListener {

    fun oneditClick(position : Int)

    fun ondeleteClick(position : Int)

}