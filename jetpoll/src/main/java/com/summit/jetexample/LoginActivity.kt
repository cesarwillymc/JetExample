package com.summit.jetexample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.summit.jetexample.data.LoginDataSource
import com.summit.jetexample.domain.LoginRepoImpl
import com.summit.jetexample.navigation.NavigationViewModel
import com.summit.jetexample.presentation.AuthViewModel
import com.summit.jetexample.presentation.LoginViewModelFactory
import com.summit.jetexample.ui.JetExampleTheme
import com.summit.jetexample.ui.login.LoginMain

class LoginActivity: AppCompatActivity() {

    private val viewModel by viewModels<AuthViewModel> { LoginViewModelFactory(LoginRepoImpl(
        LoginDataSource()
    )) }
    private val navigationViewModel by viewModels<NavigationViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                LoginMain(viewModel = viewModel,navigation = navigationViewModel,backDispatcher = onBackPressedDispatcher)


        }
    }
    override fun onBackPressed() {
        if (!navigationViewModel.onBack()) {
            super.onBackPressed()
        }
    }
}