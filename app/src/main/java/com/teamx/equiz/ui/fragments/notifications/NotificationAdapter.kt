package com.teamx.equiz.ui.fragments.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.data.models.coupons.Data
import com.teamx.equiz.data.models.notificationData.NewNotification
import com.teamx.equiz.databinding.ItemCancelledOrderBinding
import com.teamx.equiz.databinding.ItemNotificationsBinding
import com.teamx.equiz.databinding.ItemOrderBinding


class NotificationAdapter(
    val arrayList: ArrayList<NewNotification>
) : RecyclerView.Adapter<NotificationAdapter.TopProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemNotificationBinding = ItemNotificationsBinding.inflate(inflater, parent, false)
        return TopProductViewHolder(itemNotificationBinding)

    }


    override fun onBindViewHolder(holder: TopProductViewHolder, position: Int) {
        val notification: NewNotification = arrayList[position]

        holder.binding.textView16.text = notification.title
        holder.binding.textView18.text = notification.body

        val o = notification.createdAt.toString().replaceAfter('T', "").replace("T", "")

        holder.binding.textView17.text = o


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class TopProductViewHolder(itemNotificationBinding: ItemNotificationsBinding) :
        RecyclerView.ViewHolder(itemNotificationBinding.root) {
        val binding = itemNotificationBinding

    }
}