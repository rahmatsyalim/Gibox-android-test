package com.gibox.testandroid.view.ui.login


/**
 * Created by Rahmat Syalim on 2022/07/13
 * rahmatsyalim@gmail.com
 */
data class LoginFieldState(
   val email: String = "",
   val emailError: String? = null,
   val password: String = "",
   val passwordError: String? = null,
   val validated: Boolean = false
)
