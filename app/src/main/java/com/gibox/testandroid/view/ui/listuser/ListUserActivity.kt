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
import androidx.paging.LoadState
import com.gibox.testandroid.databinding.ActivityListUserBinding
import com.gibox.testandroid.util.showToast
import com.gibox.testandroid.view.adapter.ListUserAdapter
import com.gibox.testandroid.view.adapter.ListUserLoadStateAdapter
import com.gibox.testandroid.view.ui.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
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

      observeListUser()

      setLoadStateListener()

      swipeRefresh.setOnRefreshListener {
         listUserAdapter.refresh()
      }

   }

   private fun setLoadStateListener() {
      lifecycleScope.launchWhenStarted {
         listUserAdapter.loadStateFlow.collectLatest { loadState ->
            swipeRefresh.isRefreshing = loadState.refresh is LoadState.Loading
            loadState.apply {
               if (refresh is LoadState.NotLoading && listUserAdapter.itemCount < 1){
                  // TODO: empty state
               }
               if (refresh is LoadState.Error){
                  // TODO: error state
               }
            }
         }
      }
   }

   private fun setRecyclerView() {
      val concatAdapter =
         listUserAdapter.withLoadStateFooter(ListUserLoadStateAdapter(listUserAdapter))
      listUserAdapter.onItemClickListener { user ->
         showToast("Navigate to detail by id = ${user.id}")
      }
      listUserRecyclerView.adapter = concatAdapter
   }

   private fun observeListUser() {
      lifecycleScope.launchWhenStarted {
         viewModel.listUser.collectLatest { pagingData ->
            listUserAdapter.submitData(pagingData)
         }
      }
   }
}