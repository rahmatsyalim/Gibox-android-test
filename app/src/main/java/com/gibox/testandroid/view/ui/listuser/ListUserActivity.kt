/*
 * Created by Muhamad Syafii
 * Monday, 04/04/2022
 * Copyright (c) 2022 by Gibox Digital Asia.
 * All Rights Reserve
 */

package com.gibox.testandroid.view.ui.listuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.gibox.testandroid.databinding.ActivityListUserBinding
import com.gibox.testandroid.util.showToast
import com.gibox.testandroid.view.adapter.ListUserAdapter
import com.gibox.testandroid.view.adapter.ListUserLoadStateAdapter
import com.gibox.testandroid.view.ui.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class ListUserActivity : AppCompatActivity() {

   private val binding by lazy { ActivityListUserBinding.inflate(layoutInflater) }

   private val viewModel by viewModel<MainViewModel>()

   private val listUserAdapter by lazy { ListUserAdapter() }

   private val listUserRecyclerView by lazy { binding.listUserRecyclerView }

   private val swipeRefresh by lazy { binding.swipeRefresh }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(binding.root)

      setRecyclerView()

   }

   private fun ListUserAdapter.setLoadStateListener() {
      lifecycleScope.launchWhenStarted {
         loadStateFlow.collectLatest { loadState ->
            loadState.init(
               loadingState = { visible ->
                  swipeRefresh.isRefreshing = visible
               },
               emptyState = { visible ->
                  // TODO: empty visibility
               },
               errorMsg = { message ->
                  // TODO: error message
               }
            )
            if (loadState.append.endOfPaginationReached){
               showToast("All contents loaded")
            }
         }
      }
   }

   private fun setRecyclerView() {
      listUserAdapter.apply {

         listUserRecyclerView.adapter =
            withLoadStateFooter(ListUserLoadStateAdapter(this))

         onItemClickListener { user ->
               showToast("Navigate to detail by id = ${user.id}")
            }

         observeListUser()

         setLoadStateListener()

         swipeRefresh.setOnRefreshListener {
            refresh()
         }
      }

   }

   private fun ListUserAdapter.observeListUser() {
      lifecycleScope.launchWhenStarted {
         viewModel.listUser.collectLatest { pagingData ->
            submitData(pagingData)
         }
      }
   }

   private fun CombinedLoadStates.init(
      loadingState: (Boolean) -> Unit,
      emptyState: (Boolean) -> Unit,
      errorMsg: (String) -> Unit
   ) {
      loadingState(refresh is LoadState.Loading)

      emptyState(
         source.append is LoadState.NotLoading
            && source.append.endOfPaginationReached
            && listUserAdapter.itemCount == 0
      )

      val errorState =
         source.append as? LoadState.Error
            ?: source.prepend as? LoadState.Error
            ?: source.refresh as? LoadState.Error
            ?: append as? LoadState.Error
            ?: prepend as? LoadState.Error
            ?: refresh as? LoadState.Error

      errorState?.let {
         errorMsg(it.error.message.toString())
      }
   }
}