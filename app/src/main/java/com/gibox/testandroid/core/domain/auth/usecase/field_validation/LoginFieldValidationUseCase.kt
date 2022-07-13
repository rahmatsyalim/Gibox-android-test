package com.gibox.testandroid.core.domain.auth.usecase.field_validation


/**
 * Created by Rahmat Syalim on 2022/07/13
 * rahmatsyalim@gmail.com
 */
interface LoginFieldValidationUseCase {

   fun validateEmail(email: String): FieldValidationState
   fun validatePassword(password: String): FieldValidationState
}