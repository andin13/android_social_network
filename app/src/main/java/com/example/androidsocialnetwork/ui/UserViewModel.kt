package com.example.androidsocialnetwork.ui

import androidx.lifecycle.*
import com.example.androidsocialnetwork.data.entities.Profile
import com.example.androidsocialnetwork.data.services.NetworkService
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel(private val networkService: NetworkService) : ViewModel() {

    private val _profile: MutableLiveData<Profile> = MutableLiveData()
    val profile: LiveData<Profile> = _profile

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private fun setIsLoading (l: Boolean) {
        _isLoading.value = l
    }

    private fun setProfile (p: Profile) {
        _profile.value = p
    }

    fun getProfile (userId: Int) {
        viewModelScope.launch {
            setIsLoading(true)
            (networkService.getProfile(userId))?.let { setProfile(it) }
            setIsLoading(false)
        }
    }

    class Factory @Inject constructor(
        private val networkService: NetworkService
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserViewModel(networkService) as T
        }
    }
}