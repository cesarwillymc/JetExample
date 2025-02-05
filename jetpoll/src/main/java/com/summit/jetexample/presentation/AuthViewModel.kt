package com.summit.jetexample.presentation

import androidx.lifecycle.*
import com.summit.jetexample.data.model.AuthCredentials
import com.summit.jetexample.domain.LoginRepo
import com.summit.jetexample.vo.Result
import kotlinx.coroutines.Dispatchers

class AuthViewModel(val repo: LoginRepo):ViewModel() {

    private val loginCredentialsData = MutableLiveData<AuthCredentials>()
    private val registerCredentialsData = MutableLiveData<AuthCredentials>()


    fun setLoginCredentials(loginCredentials: AuthCredentials){
        loginCredentialsData.value = loginCredentials
    }

    fun setRegisterCredentials(registerCredentials:AuthCredentials){
        registerCredentialsData.value = registerCredentials
    }

    val getLoginResult = loginCredentialsData.switchMap { authCredentials ->
    liveData<Result<Boolean>> (Dispatchers.IO){
            emit(Result.Loading())
            try{
                emit(repo.login(authCredentials))
            }catch (e:Exception){
                emit(Result.Failure(e))
            }
        }
    }

    val getRegisterResult: LiveData<Result<Boolean>> = registerCredentialsData.switchMap { authCredentials ->
        liveData<Result<Boolean>>(Dispatchers.IO) {
            emit(Result.Loading())
            try{
                emit(repo.register(authCredentials))
            }catch (e:Exception){
                emit(Result.Failure(e))
            }
        }
    }
}

class LoginViewModelFactory(private val repo:LoginRepo):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LoginRepo::class.java).newInstance(repo)
    }
}