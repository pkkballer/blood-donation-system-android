package com.jama.kenyablooddonationsystem.viewModels.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import com.jama.kenyablooddonationsystem.models.UserModel
import com.jama.kenyablooddonationsystem.repository.firebase.firebaseAuth.AuthenticationRepository
import kotlinx.coroutines.launch
import java.lang.RuntimeException

class AuthViewModel: ViewModel() {
    var showProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    var errorMap: MutableLiveData<Map<String, Any>> = MutableLiveData(mutableMapOf("message" to "", "isError" to false))
    var loginUser: MutableLiveData<Boolean> = MutableLiveData(false)

    private val authRepo = AuthenticationRepository()

    fun signIn(email: String, password: String) {
        showProgress.value = true
        errorMap.value = mutableMapOf("message" to "", "isError" to false)
        viewModelScope.launch {
           try {
               if (email.isEmpty() || password.isEmpty()) throw RuntimeException("Email and password must be filled in")
               authRepo.signIn(email, password)
               showProgress.value = false
               checkIfUserExists()
           } catch (e: FirebaseAuthException) {
               showProgress.value = false
               errorMap.value = mutableMapOf("message" to e.message.toString(), "isError" to true)
           } catch (e: RuntimeException) {
               showProgress.value = false
               errorMap.value = mutableMapOf("message" to e.message.toString(), "isError" to true)
           }
        }
    }

    fun signUp(userModel: UserModel) {
        showProgress.value = true
        errorMap.value = mutableMapOf("message" to "", "isError" to false)
        viewModelScope.launch {
            try{
                userModel.toMap().map {
                    if (it.value == "") throw RuntimeException("Please fill in all details")
                }
                authRepo.signUp(userModel)
                showProgress.value = false
                checkIfUserExists()
            } catch (e: FirebaseAuthException) {
                showProgress.value = false
                errorMap.value = mutableMapOf("message" to e.message.toString(), "isError" to true)
            } catch (e: RuntimeException) {
                showProgress.value = false
                errorMap.value = mutableMapOf("message" to e.message.toString(), "isError" to true)
            }
        }
    }

    fun checkIfUserExists() {
        loginUser.value = authRepo.checkIfUserExists()
    }
}