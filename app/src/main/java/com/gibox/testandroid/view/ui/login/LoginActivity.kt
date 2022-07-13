/*
 * Created by Muhamad Syafii
 * Monday, 04/04/2022
 * Copyright (c) 2022 by Gibox Digital Asia.
 * All Rights Reserve
 */

package com.gibox.testandroid.view.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gibox.testandroid.core.data.auth.source.remote.request.LoginRequest
import com.gibox.testandroid.core.session.SessionRepository
import com.gibox.testandroid.databinding.ActivityLoginBinding
import com.gibox.testandroid.util.closeActivity
import com.gibox.testandroid.util.hideKeyboard
import com.gibox.testandroid.util.openActivity
import com.gibox.testandroid.util.showToast
import com.gibox.testandroid.view.ui.listuser.ListUserActivity
import com.gibox.testandroid.view.ui.viewmodel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {


    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    private val viewModel by viewModel<MainViewModel>()

    private val session by inject<SessionRepository>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (session.isUserLogin()){
            openActivity(ListUserActivity::class.java)
            closeActivity()
        } else {
            // login test
            viewModel.requestLogin(LoginRequest("eve.holt@reqres.in","cityslicka"))
            observeLoginRequest()
        }

    }

    private fun observeLoginRequest(){
        viewModel.loginState.observe(this){ state ->
            if (state.isLoading){
                hideKeyboard()
                // TODO: loading true
            }

            if (state.error.isNotBlank()){
                showToast(state.error)
                // TODO: loading false
            }

            if (state.data == null && state.error.isBlank() && !state.isLoading){
                // TODO: empty state
                // TODO: loading false
                showToast("Empty")
            } else {
                state.data?.let { data ->
                    data.token?.let {
                        // TODO: loading false
                        session.loginUser("user",it)
                    }
                }
            }

            if (session.isUserLogin()){
                openActivity(ListUserActivity::class.java)
                closeActivity()
            }
        }
    }
}