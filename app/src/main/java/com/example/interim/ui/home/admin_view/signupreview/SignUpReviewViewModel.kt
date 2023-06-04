package com.example.interim.ui.home.admin_view.signupreview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interim.models.Employer
import com.example.interim.services.UsersService

class SignUpReviewViewModel: ViewModel() {

    var userService: UsersService = UsersService()

    private val _users = MutableLiveData<List<Employer>>().apply {
        value = userService.getUnverifiedEmployers()
    }

    val users: LiveData<List<Employer>> = _users

    fun refresh() {
        _users.value = userService.getUnverifiedEmployers()
    }

    fun verifyUser(item: Employer) {
        userService.verifyEmployer(item.getId())
        refresh()
    }

    fun refuseUser(item: Employer) {
        userService.deleteEmployer(item.getId())
        refresh()
    }


}