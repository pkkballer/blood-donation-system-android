package com.jama.kenyablooddonationsystem.viewModels.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseAuth.AuthenticationRepository
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    var showProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    var hasError: MutableLiveData<Boolean> = MutableLiveData(false)

    private val authRepo = AuthenticationRepository()

    fun signIn(email: String, password: String) {
        showProgress.value = true
        viewModelScope.launch {
           try {
               val result = authRepo.signIn(email, password)
               println(result?.user?.email)
               showProgress.value = false
           } catch (e: FirebaseAuthException) {
               println(e.message)
           }
        }
    }


}