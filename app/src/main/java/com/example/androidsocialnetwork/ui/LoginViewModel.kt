package com.example.androidsocialnetwork.ui

import androidx.lifecycle.*
import com.example.androidsocialnetwork.data.cookieStorage
import com.example.androidsocialnetwork.data.entities.LoginData
import com.example.androidsocialnetwork.data.services.NetworkService
import com.example.androidsocialnetwork.utils.Action
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel(private val networkService: NetworkService): ViewModel() {

    private val _login: MutableLiveData<String> = MutableLiveData("andrei.indosov@gmail.com")
    val login: LiveData<String> = _login


    private val _password: MutableLiveData<String> = MutableLiveData("37EhB9B54pcUjUa")
    val password: LiveData<String> = _password

    private val _result: MutableLiveData<String> = MutableLiveData("")
    val result: LiveData<String> = _result

    private val _resultAuth: MutableLiveData<String> = MutableLiveData("")
    val resultAuth: LiveData<String> = _resultAuth

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _action: MutableLiveData<LoginViewActions> = MutableLiveData()
    val action: LiveData<LoginViewActions> = _action

    fun setLogin (l: String) {
        _login.value = l
    }

    fun setPassword (p: String) {

        _password.value = p
    }

    fun setResult (r: String) {
        _result.value = r
    }

    fun setResultAuth (r: String) {
        _resultAuth.value = r
    }

    fun setIsLoading (l: Boolean) {
        _isLoading.value = l
    }

    fun authMe () {
        viewModelScope.launch {
            setIsLoading(true)
            setResultAuth(networkService.authMe().toString())
            setIsLoading(false)
        }
    }

    fun login () {
        viewModelScope.launch {
            setIsLoading(true)
            setResult(networkService.login(LoginData(login.value?:"", password.value?:"", false)).toString())
            authMe()
            setIsLoading(false)
            _action.value = LoginViewActions.ToUserList()
        }
    }

    fun logout () {
        viewModelScope.launch {
            setIsLoading(true)
            networkService.logout()
            cookieStorage.clear()
            setIsLoading(false)
        }
    }

    class Factory @Inject constructor(
        private val networkService: NetworkService
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(networkService) as T
        }
    }
}

sealed class LoginViewActions:Action() {
    class ToUserList:LoginViewActions()
}