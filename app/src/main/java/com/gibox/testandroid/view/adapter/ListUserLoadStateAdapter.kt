package com.gibox.testandroid.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gibox.testandroid.R
import com.gibox.testandroid.databinding.PagingFooterBinding


/**
 * Created by Rahmat Syalim on 2022/07/13
 * rahmatsyalim@gmail.com
 */
class ListUserLoadStateAdapter(
   private val adapter: ListUserAdapter
) : LoadStateAdapter<ListUserLoadStateAdapter.PagingFooterItemViewHolder>() {

   inner class PagingFooterItemViewHolder(
      private val binding: PagingFooterBinding,
      private val retry: () -> Unit
   ) : RecyclerView.ViewHolder(binding.root) {

      init {
         binding.retryButton.setOnClickListener {
            retry()
         }
      }

      fun bind(loadState: LoadState) {
         binding.apply {
            progressBar.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState is LoadState.Error
            errorMsg.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
            errorMsg.text = (loadState as? LoadState.Error)?.error?.message
         }
      }
   }

   override fun onBindViewHolder(holder: PagingFooterItemViewHolder, loadState: LoadState) {
      holder.bind(loadState)
   }

   override fun onCreateViewHolder(
      parent: ViewGroup,
      loadState: LoadState
   ): PagingFooterItemViewHolder {
      return PagingFooterItemViewHolder(
         PagingFooterBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.paging_footer, parent, false)
         )
      ) {
         adapter.retry()
      }
   }
}