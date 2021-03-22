package com.summit.jetexample.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.summit.jetexample.data.model.Poll
import com.summit.jetexample.presentation.PollViewModel
import com.summit.jetexample.vo.*
import com.summit.jetexample.data.model.User
import com.summit.jetexample.utils.ShowError
import com.summit.jetexample.utils.ShowProgress

@Composable
fun PollHome(viewModel: PollViewModel, onCreatePollClick: () -> Unit){
    val pollResult: Result<List<Poll>> by viewModel.fetchAllPolls.observeAsState(Result.Success(emptyList()))
    when (pollResult) {
        is Result.Loading -> ShowProgress()
        is Result.Success -> {
            PollScreen(
                    pollList = (pollResult as Result.Success<List<Poll>>).data,
                    user = User("Gaston", "https://avatars2.githubusercontent.com/u/24615408?s=460&u=8a985792aa795ada276b4d567baba1c732ab02fb&v=4"),
                    onCreatePollClick = onCreatePollClick)
        }
        is Result.Failure -> ShowError((pollResult as Result.Failure<List<Poll>>).exception)
    }
}

@Composable
private fun PollScreen(pollList:List<Poll>, user: User, onCreatePollClick: () -> Unit){
    Column(modifier = Modifier.fillMaxHeight()) {
        CreatePollComponent(modifier = Modifier.fillMaxWidth().padding(start = 32.dp,end = 32.dp,top = 32.dp).weight(1f),username = user.userName, onCreatePollClick = onCreatePollClick, userphoto = user.userPhoto)
        PollListComponent(modifier = Modifier.padding(bottom = 32.dp),pollList = pollList)
    }
}

