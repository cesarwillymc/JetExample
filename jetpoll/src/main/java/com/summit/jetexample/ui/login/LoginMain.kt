package com.summit.jetexample.ui.login

import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.summit.jetexample.MainActivity
import com.summit.jetexample.data.model.AuthCredentials
import com.summit.jetexample.navigation.*
import com.summit.jetexample.presentation.AuthViewModel
import com.summit.jetexample.ui.register.RegisterMain
import com.summit.jetexample.ui.typography
import com.summit.jetexample.utils.ShowProgress
import com.summit.jetexample.utils.showMessage
import com.summit.jetexample.vo.Result


@Composable
fun LoginMain(viewModel: AuthViewModel, navigation: NavigationViewModel,backDispatcher:OnBackPressedDispatcher){

            backDispatcher.addCallback(object :OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    navigation.onBack()
                }

            })

            Crossfade(navigation.currentScreen) { destination ->
                when (destination) {
                    is Screen.Home -> {
                        LoginHome(viewModel = viewModel,onRegisterClick = {
                            navigation.navigateTo(Screen.Register)
                        })
                    }
                    is Screen.Register -> {
                        RegisterMain(viewModel = viewModel)
                    }
                }
            }


}

@Composable
private fun LoginHome(viewModel: AuthViewModel,onRegisterClick:() -> Unit){
    val context = LocalContext.current
    val loginResult: Result<Boolean> by viewModel.getLoginResult.observeAsState(Result.Success(false))
    when(loginResult){
        is Result.Loading -> {
            ShowProgress()
        }
        is Result.Success -> {
            if ((loginResult as Result.Success<Boolean>).data) {
                val intent = Intent(context, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } else {
                LoginScreen(viewModel,onRegisterClick)
            }
        }
        is Result.Failure -> {
            LoginScreen(viewModel = viewModel,onRegisterClick)
            showMessage(context, (loginResult as Result.Failure<Boolean>).exception.message!!)
        }
    }
}

@Composable
private fun LoginScreen(viewModel: AuthViewModel,onRegisterClick: () -> Unit){
    val username = remember { mutableStateOf(TextFieldValue("")) }
    val password = remember { mutableStateOf(TextFieldValue("")) }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)){
        Text(
            "Ingresar sesion",
            style = typography.h5,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                bottom = 8.dp
            )
        )
        Column{
            Card(elevation = 8.dp){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(leadingIcon = { Icon(imageVector = Icons.Filled.Person, contentDescription = "Person icon") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = 8.dp
                            ),
                        value = username.value,
                        onValueChange = { username.value = it },
                        label = {
                            Text(
                                "Username"
                            )
                        })
                    TextField(
                        leadingIcon = { Icon(imageVector = Icons.Filled.Lock, contentDescription = "Lock icon") },
                        modifier = Modifier.fillMaxWidth(),
                        value = password.value,
                        onValueChange = { password.value = it },
                        label = {
                            Text(
                                "Password"
                            )
                        })
                    Button(modifier = Modifier.padding(bottom = 8.dp, top = 8.dp), onClick = {
                        viewModel.setLoginCredentials(
                            AuthCredentials(
                                username = username.value.text,
                                password = password.value.text
                            )
                        )
                    }) {
                        Text("Login")
                    }
                    Button(onClick = onRegisterClick) {
                        Text("Register")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoginScreen(){
   //TODO mock viewmodel to render preview
   //LoginScreen()
}