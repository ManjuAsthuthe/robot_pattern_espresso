package com.fredrikbogg.android_chat_app.ui.users

import androidx.lifecycle.*
import com.fredrikbogg.android_chat_app.data.db.entity.User
import com.fredrikbogg.android_chat_app.data.db.repository.DatabaseRepository
import com.fredrikbogg.android_chat_app.ui.DefaultViewModel
import com.fredrikbogg.android_chat_app.data.Event
import com.fredrikbogg.android_chat_app.data.Result
import com.fredrikbogg.android_chat_app.data.db.entity.UserInfo


class UsersViewModelFactory(private val myUserID: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UsersViewModel(myUserID) as T
    }
}

class UsersViewModel(private val myUserID: String) : DefaultViewModel() {
    private val repository: DatabaseRepository = DatabaseRepository()

    private val _selectedUser = MutableLiveData<Event<User>>()
    var selectedUser: LiveData<Event<User>> = _selectedUser
    private val updatedUsersList = MutableLiveData<MutableList<User>>()
    val usersList = MediatorLiveData<List<User>>()

    var userData:List<User>? = null
        get(){
            val list = ArrayList<User>()
            list.add(User(
                UserInfo(
                    id = "12345",
                    displayName = "TestUser",
                    status = "test1"
                )
            ))
            list.add(User(
                UserInfo(
                    id = "54321",
                    displayName = "testaccount",
                    status = "test2"
                )
            ))
            return list
        }

    init {
        usersList.value = userData
        usersList.addSource(updatedUsersList) { mutableList ->
            userData = updatedUsersList.value?.filter { it.info.id != myUserID }
        }
        loadUsers()
    }

    private fun loadUsers() {
        repository.loadUsers { result: Result<MutableList<User>> ->
            onResult(updatedUsersList, result)
        }
    }

    fun selectUser(user: User) {
        _selectedUser.value = Event(user)
    }
}