package com.example.androidsocialnetwork.ui

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.androidsocialnetwork.data.entities.UserItem
import com.example.androidsocialnetwork.data.services.NetworkService
import com.example.androidsocialnetwork.utils.Action
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class UsersListViewModel(networkService: NetworkService): ViewModel() {

//    private val _users: MutableLiveData<List<UserItem>> = MutableLiveData(emptyList())
//    val users: LiveData<List<UserItem>> = _users

    private val _viewAction: MutableLiveData<UsersListViewActions> = MutableLiveData()
    val viewAction: LiveData<UsersListViewActions> = _viewAction

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    val usersFlow: Flow<PagingData<UserItem>> = networkService.getPagedUsers().cachedIn(viewModelScope)

//    private fun setUsers (u: List<UserItem>) {
//        _users.value = u
//    }

    private fun setIsLoading (l: Boolean) {
        _isLoading.value = l
    }

//    fun getUsers () {
//        viewModelScope.launch {
//            setIsLoading(true)
//            setUsers(networkService.getUsers(110, 20))
//            setIsLoading(false)
//        }
//    }

    fun showProfile (userId: Int) {
        _viewAction.value = UsersListViewActions.ToProfileScreen(userId)
    }

    class Factory @Inject constructor(
        private val networkService: NetworkService
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UsersListViewModel(networkService) as T
        }
    }
}

sealed class UsersListViewActions : Action() {
    class ToProfileScreen(val userId: Int) : UsersListViewActions()
}