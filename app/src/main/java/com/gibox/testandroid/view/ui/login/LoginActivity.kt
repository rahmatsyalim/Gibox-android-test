/*
 * Created by Muhamad Syafii
 * Monday, 04/04/2022
 * Copyright (c) 2022 by Gibox Digital Asia.
 * All Rights Reserve
 */

package com.gibox.testandroid.view.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.gibox.testandroid.core.session.SessionRepository
import com.gibox.testandroid.databinding.ActivityLoginBinding
import com.gibox.testandroid.util.closeActivity
import com.gibox.testandroid.util.hideKeyboard
import com.gibox.testandroid.util.openActivity
import com.gibox.testandroid.util.showToast
import com.gibox.testandroid.view.ui.listuser.ListUserActivity
import com.gibox.testandroid.view.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {


   private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

   private val viewModel by viewModel<MainViewModel>()

   private val session by inject<SessionRepository>()

   private val loginButton by lazy { binding.loginButton }

   private val progressBar by lazy { binding.progressBar }


   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(binding.root)

      if (session.isUserLogin() && !session.getTokenUser().isNullOrBlank()) {
         openActivity(ListUserActivity::class.java)
         finish()
      }

      loginButtonSetup()

      observeLoginFieldValidation()
      observeLoginRequest()

   }

   private fun loginButtonSetup() {
      loginButton.setOnClickListener {
         val email = binding.emailEdittextLayout.editText?.text.toString()
         val password = binding.passwordEdittextLayout.editText?.text.toString()
         viewModel.validateLoginField(
            email = email,
            password = password
         )
      }
   }

   private fun observeLoginFieldValidation() {
      viewModel.loginFieldValidationState.observe(this) { state ->

         binding.emailEdittextLayout.isErrorEnabled = !state.emailError.isNullOrBlank()
         binding.passwordEdittextLayout.isErrorEnabled = !state.passwordError.isNullOrBlank()

         state.apply {
            emailError?.let {
               binding.emailEdittextLayout.error = it
            }
            passwordError.let {
               binding.passwordEdittextLayout.error = it
            }
         }
      }
   }

   private fun observeLoginRequest() {
      viewModel.loginState.observe(this) { state ->

         loginButton.isVisible = !state.isLoading
         progressBar.isVisible = state.isLoading

         if (state.isLoading) {
            hideKeyboard()
         }

         if (state.error.isNotBlank()) {
            showToast(state.error)
         }

         state.data?.let { data ->
            data.token?.let {
               session.loginUser("user", it)
               viewModel.clearLoginState()
               openActivity(ListUserActivity::class.java)
               finish()
            }
         }
      }
   }
}