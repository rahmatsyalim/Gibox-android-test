package com.gibox.testandroid.core.domain.auth.usecase.field_validation


/**
 * Created by Rahmat Syalim on 2022/07/13
 * rahmatsyalim@gmail.com
 */
data class FieldValidationState(
   val success: Boolean = false,
   val errorMsg: String? = null
)
