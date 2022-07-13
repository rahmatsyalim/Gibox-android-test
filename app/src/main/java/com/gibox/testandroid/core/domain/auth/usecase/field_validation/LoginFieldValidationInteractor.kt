package com.gibox.testandroid.core.domain.auth.usecase.field_validation

import android.util.Patterns


/**
 * Created by Rahmat Syalim on 2022/07/13
 * rahmatsyalim@gmail.com
 */
class LoginFieldValidationInteractor : LoginFieldValidationUseCase {

   override fun validateEmail(email: String): FieldValidationState {
      if (email.isBlank()) {
         return FieldValidationState(
            success = false,
            errorMsg = "Email tidak boleh kosong"
         )
      }
      if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
         return FieldValidationState(
            success = false,
            errorMsg = "Format email salah"
         )
      }
      return FieldValidationState(
         success = true
      )
   }

   override fun validatePassword(password: String): FieldValidationState {
      if (password.isBlank()) {
         return FieldValidationState(
            success = false,
            errorMsg = "Password tidak boleh kosong"
         )
      }
      return FieldValidationState(
         success = true
      )
   }
}