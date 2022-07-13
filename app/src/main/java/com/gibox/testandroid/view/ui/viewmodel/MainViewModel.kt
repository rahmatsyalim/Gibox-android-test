/*
 * Created by Muhamad Syafii
 * , 5/4/2022
 * Copyright (c) 2022 by Gibox Digital Asia.
 * All Rights Reserve
 */

package com.gibox.testandroid.view.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gibox.testandroid.core.data.auth.source.remote.request.LoginRequest
import com.gibox.testandroid.core.data.vo.Resource
import com.gibox.testandroid.core.domain.auth.usecase.AuthUseCase
import com.gibox.testandroid.view.ui.login.LoginState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val authUseCase:AuthUseCase):ViewModel() {

    private val _loginState = MutableLiveData(LoginState())
    val loginState: LiveData<LoginState> get() = _loginState

    fun requestLogin(loginRequest:LoginRequest) = viewModelScope.launch {
        authUseCase.doLogin(loginRequest).collect { result ->
            when(result){
                is Resource.Loading -> _loginState.value = LoginState(isLoading = true)
                is Resource.Error -> _loginState.value = LoginState(error = result.message.toString())
                is Resource.Success -> _loginState.value = LoginState(data = result.data)
            }
        }
    }

}