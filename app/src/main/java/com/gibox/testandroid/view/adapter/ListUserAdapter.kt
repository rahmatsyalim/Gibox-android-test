package com.gibox.testandroid.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gibox.testandroid.core.data.auth.source.remote.response.DataItem
import com.gibox.testandroid.databinding.ItemUserBinding


/**
 * Created by Rahmat Syalim on 2022/07/13
 * rahmatsyalim@gmail.com
 */
class ListUserAdapter : PagingDataAdapter<DataItem, ListUserAdapter.ListUserViewHolder>(ListUserComparator) {

   private var itemClickListener: ((DataItem) -> Unit)? = null

   override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
      holder.onBind(getItem(position)!!)
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
      return ListUserViewHolder(
         ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
         )
      )
   }

   inner class ListUserViewHolder(private val binding: ItemUserBinding) :
      RecyclerView.ViewHolder(binding.root) {
      fun onBind(user: DataItem) {
         itemView.setOnClickListener {
            itemClickListener?.let { it(user) }
         }
      }
   }

   fun onItemClickListener(listener: (DataItem) -> Unit) {
      itemClickListener = listener
   }

   object ListUserComparator : DiffUtil.ItemCallback<DataItem>() {
      override fun areItemsTheSame(
         oldItem: DataItem,
         newItem: DataItem
      ): Boolean {
         return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(
         oldItem: DataItem,
         newItem: DataItem
      ): Boolean {
         return oldItem == newItem
      }
   }
}