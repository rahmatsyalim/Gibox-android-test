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
import com.gibox.testandroid.databinding.ActivityListUserBinding
import com.gibox.testandroid.view.ui.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.viewModel

class ListUserActivity : AppCompatActivity() {

    private val binding by lazy { ActivityListUserBinding.inflate(layoutInflater) }

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

    private fun observeListUser(){
        lifecycleScope.launchWhenStarted {
            viewModel.listUser.collectLatest { pagingData ->
                // TODO: submit data to adapter 
            }
        }
    }
}