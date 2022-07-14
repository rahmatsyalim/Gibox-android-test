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
import androidx.paging.cachedIn
import com.gibox.testandroid.core.data.auth.source.remote.request.LoginRequest
import com.gibox.testandroid.core.data.vo.Resource
import com.gibox.testandroid.core.domain.auth.usecase.AuthUseCase
import com.gibox.testandroid.core.domain.auth.usecase.field_validation.LoginFieldValidationUseCase
import com.gibox.testandroid.view.ui.login.LoginFieldState
import com.gibox.testandroid.view.ui.login.LoginState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
   private val authUseCase: AuthUseCase,
   private val loginFieldValidationUseCase: LoginFieldValidationUseCase
) : ViewModel() {

   private val _loginState = MutableLiveData(LoginState())
   val loginState: LiveData<LoginState> get() = _loginState

   private val _loginFieldValidationState = MutableLiveData(LoginFieldState())
   val loginFieldValidationState: LiveData<LoginFieldState> get() = _loginFieldValidationState

   fun requestLogin(loginRequest: LoginRequest) = viewModelScope.launch {
      authUseCase.doLogin(loginRequest).collect { result ->
         when (result) {
            is Resource.Loading -> _loginState.value = LoginState(isLoading = true)
            is Resource.Error -> _loginState.value = LoginState(error = result.message.toString())
            is Resource.Success -> _loginState.value = LoginState(data = result.data)
         }
      }
   }

   fun clearLoginState(){
      _loginState.value = LoginState(data = null)
   }

   fun validateLoginField(email: String, password: String) {
      val validateEmail = loginFieldValidationUseCase.validateEmail(email)
      val validatePassword = loginFieldValidationUseCase.validatePassword(password)
      val hasError = listOf(validateEmail, validatePassword).any { !it.success }
      if (hasError) {
         _loginFieldValidationState.value = LoginFieldState(
            emailError = validateEmail.errorMsg,
            passwordError = validatePassword.errorMsg
         )
      } else {
         _loginFieldValidationState.value = LoginFieldState(
            email = email,
            password = password,
            validated = true
         )
         requestLogin(LoginRequest(
            email = email,
            password = password
         ))
      }
   }

   val listUser = authUseCase.getUserList().cachedIn(viewModelScope)

}