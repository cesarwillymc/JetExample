package com.summit.jetexample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import com.summit.jetexample.state.livedata.data.UserDataSource
import com.summit.jetexample.state.livedata.domain.RepoImpl
import com.summit.jetexample.state.livedata.presentation.UserViewModel
import com.summit.jetexample.state.livedata.presentation.UserViewModelFactory


/**
 * [EN]
 * How to run the samples:
 * Just call the Composable methods of the packages inside setContent
 * Example: setContent { Toolbar() } ---> this will trigger the Toolbar() composable
 * inside the toolbar package where this UI code resides
 */

/**
 * [ES]
 * Como correr ejemplos:
 * Solo llama a la funcion composable de cualquiera de los paquetes del proyecto
 * Ejemplo: setContent { Toolbar() } ---> esto va a lanzar el composable del Toolbar()
 */

class MainActivity : AppCompatActivity() {

    // [EN] Viewmodel to provide to UserScreen demo
    // [ES] Viewmodel para proveer a UserScreen demo
    private val userViewModel by viewModels<UserViewModel> { UserViewModelFactory(RepoImpl(UserDataSource())) }

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // [EN] Call here what you want to try out
            // [ES] Llama aqui que es lo que quieres probar
            // Example: Toolbar()
        }
    }
}














