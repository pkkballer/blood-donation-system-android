package com.jama.kenyablooddonationsystem.repository.firebase.firebaseAuth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.jama.kenyablooddonationsystem.models.SignUpModel
import kotlinx.coroutines.tasks.await

class AuthenticationRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseUserRef = database.getReference("users")
    private val databaseDonorDetailsRef = database.getReference("donationDetails")

    suspend fun signIn(email: String, password: String): AuthResult? {
        return auth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun signUp(signUpModel: SignUpModel) {
        val firebaseUser = auth.createUserWithEmailAndPassword(signUpModel.email, signUpModel.password).await()
        signUpModel.uid = firebaseUser.user!!.uid
        databaseUserRef.child(firebaseUser.user!!.uid).setValue(signUpModel.toMap()).await()
        databaseDonorDetailsRef.child(firebaseUser.user!!.uid).setValue(signUpModel.toDonorDetails()).await()
    }

    fun checkIfUserExists(): Boolean {
        return auth.currentUser != null
    }

    fun signOut() {
        auth.signOut()
    }
}