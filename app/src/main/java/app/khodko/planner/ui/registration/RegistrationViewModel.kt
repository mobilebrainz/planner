package app.khodko.planner.ui.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.khodko.planner.core.viewmodel.SingleLiveEvent
import app.khodko.planner.data.entity.User
import app.khodko.planner.data.repository.UserRepository
import kotlinx.coroutines.launch

class RegistrationViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _existUser = SingleLiveEvent<Boolean>()
    val existUser: LiveData<Boolean> = _existUser

    private val _loginError = SingleLiveEvent<Boolean>()
    val loginError: LiveData<Boolean> = _loginError

    private val _userId = MutableLiveData<Long>()
    val userId: LiveData<Long> = _userId
    fun setUserId(id: Long) {
        _userId.value = id
    }

    fun save(user: User) {
        viewModelScope.launch {
            val users = userRepository.existUser(name = user.name, email = user.email)
            if (users.isEmpty()) {
                _userId.value = userRepository.insert(user)
            } else {
                _existUser.value = true
            }
        }
    }

}