package com.summit.jetexample.presentation

import androidx.lifecycle.*
import com.summit.jetexample.data.model.Poll
import com.summit.jetexample.domain.Repo
import com.summit.jetexample.vo.Result
import kotlinx.coroutines.Dispatchers

class PollViewModel(private val repo: Repo) : ViewModel() {

    private val createPollMutable = MutableLiveData<Poll>()

    val fetchAllPolls = liveData<Result<List<Poll>>>(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(repo.getAllPolls())
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun setPoll(poll: Poll){
        createPollMutable.value = poll
    }
    val createPoll = createPollMutable.switchMap {
        liveData<Result<Boolean>>(Dispatchers.IO) {
            emit(Result.Loading())
            try {
                emit(repo.createPoll(it))
            } catch (e: Exception) {
                emit(Result.Failure(e))
            }
        }
    }


}

class PollViewModelFactory(private val repo: Repo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repo::class.java).newInstance(repo)
    }
}