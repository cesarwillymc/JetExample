package com.summit.jetexample.ui.home

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import com.summit.jetexample.navigation.*
import com.summit.jetexample.presentation.PollViewModel
import com.summit.jetexample.ui.createpoll.CreatePollMain

@Composable
fun PollMain(viewModel: PollViewModel,navigation:NavigationViewModel, backDispatcher: OnBackPressedDispatcher) {


            Crossfade(navigation.currentScreen) { destination ->
                when (destination) {
                    is Screen.Home -> {
                        PollHome(viewModel = viewModel,onCreatePollClick = {  })
                    }
                    is Screen.CreatePoll -> {
                        CreatePollMain(viewModel)
                    }
                }
            }


}