package com.gibox.testandroid.view.ui.login

import com.gibox.testandroid.core.domain.auth.model.LoginEntityDomain

data class LoginState(
   val isLoading: Boolean = false,
   val error: String = "",
   val data: LoginEntityDomain? = null
)