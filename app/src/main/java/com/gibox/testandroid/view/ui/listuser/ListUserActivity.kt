/*
 * Created by Muhamad Syafii
 * Monday, 04/04/2022
 * Copyright (c) 2022 by Gibox Digital Asia.
 * All Rights Reserve
 */

package com.gibox.testandroid.view.ui.listuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.gibox.testandroid.core.session.SessionRepository
import com.gibox.testandroid.databinding.ActivityListUserBinding
import com.gibox.testandroid.databinding.BottomSheetLogoutBinding
import com.gibox.testandroid.util.openActivity
import com.gibox.testandroid.util.showToast
import com.gibox.testandroid.view.adapter.ListUserAdapter
import com.gibox.testandroid.view.adapter.ListUserLoadStateAdapter
import com.gibox.testandroid.view.ui.login.LoginActivity
import com.gibox.testandroid.view.ui.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ListUserActivity : AppCompatActivity() {

   private val binding by lazy { ActivityListUserBinding.inflate(layoutInflater) }

   private val viewModel by viewModel<MainViewModel>()

   private val listUserAdapter by lazy { ListUserAdapter() }

   private val listUserRecyclerView by lazy { binding.listUserRecyclerView }

   private val swipeRefresh by lazy { binding.swipeRefresh }

   private val session by inject<SessionRepository>()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(binding.root)

      setRecyclerView()

      binding.moreImageView.setOnClickListener {
         showBottomSheet()
      }

   }

   private fun ListUserAdapter.setLoadStateListener() {
      this@ListUserActivity.lifecycleScope.launchWhenStarted {
         loadStateFlow.collectLatest { loadState ->
            loadState.init(
               loadingState = { visible ->
                  swipeRefresh.isRefreshing = visible
               },
               emptyState = { visible ->
                  binding.noDataTextView.isVisible = visible
               },
               errorMsg = { visible, message ->
                  if (visible == true) {
                     binding.noDataTextView.visibility = View.VISIBLE
                  }
                  binding.noDataTextView.text = message
               }
            )
            if (loadState.append.endOfPaginationReached) {
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
      this@ListUserActivity.lifecycleScope.launchWhenStarted {
         viewModel.fetchListUser().collectLatest { pagingData ->
            submitData(pagingData)
         }
      }
   }

   private fun CombinedLoadStates.init(
      loadingState: (Boolean) -> Unit,
      emptyState: (Boolean) -> Unit,
      errorMsg: (Boolean?, String?) -> Unit
   ) {
      loadingState(refresh is LoadState.Loading)

      emptyState(
         source.append is LoadState.NotLoading
            && source.append.endOfPaginationReached
            && listUserAdapter.itemCount == 0
      )

      val errorState =
         source.refresh as? LoadState.Error
            ?: refresh as? LoadState.Error

      errorState?.let {
         errorMsg(true, it.error.message + "\n\nPull to refresh")
      }
   }

   private fun showBottomSheet() {
      val bindingBottomSheet = BottomSheetLogoutBinding.inflate(LayoutInflater.from(this))
      val dialog = BottomSheetDialog(this)
      dialog.setContentView(bindingBottomSheet.root)
      bindingBottomSheet.logoutTextView.setOnClickListener {
         logout()
      }
      dialog.show()
   }

   private fun logout() {
      session.logoutUser()
      openActivity(LoginActivity::class.java)
      finish()
   }
}