package com.summit.jetexample

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.summit.jetexample.data.DataSource
import com.summit.jetexample.domain.RepoImpl
import com.summit.jetexample.navigation.NavigationViewModel
import com.summit.jetexample.ui.home.PollMain
import com.summit.jetexample.presentation.PollViewModel
import com.summit.jetexample.presentation.PollViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.summit.jetexample.ui.JetExampleTheme

/**
 * [EN]
 * JetPoll is an app that let you create polls with options that others can select
 * Currently WIP
 */

/**
 * [ES]
 * JetPoll es una aplicación en la cual puedes crear encuestas con opciones que otros pueden responder
 * Actualmente en construccion
 */

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PollViewModel> { PollViewModelFactory(RepoImpl(DataSource())) }
    private val navigationViewModel by viewModels<NavigationViewModel>()
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentUser = FirebaseAuth.getInstance().currentUser
        setContent {

                if(currentUser != null){
                    PollMain(viewModel = viewModel,navigation = navigationViewModel,backDispatcher = onBackPressedDispatcher)
                }else{
                    startActivity(Intent(this,LoginActivity::class.java))
                }


        }
    }
    override fun onBackPressed() {
        if (!navigationViewModel.onBack()) {
            super.onBackPressed()
        }
    }
}