package com.example.alquigo.data.repository

import com.example.alquigo.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getCurrentUser() = auth.currentUser

    suspend fun register(user: User, contrasena: String): Result<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(user.email, contrasena).await()
            val uid = result.user?.uid ?: throw Exception("Error al obtener UID")
            
            val userData = user.copy(uid = uid)
            firestore.collection("usuarios").document(uid).set(userData).await()
            
            Result.success(uid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(email: String, contrasena: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, contrasena).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
