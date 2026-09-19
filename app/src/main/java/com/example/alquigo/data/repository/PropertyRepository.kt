package com.example.alquigo.data.repository

import com.example.alquigo.data.model.Property
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PropertyRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val collection = firestore.collection("propiedades")

    suspend fun saveProperty(property: Property): Result<Unit> {
        return try {
            val docRef = collection.document()
            val propertyWithId = property.copy(id = docRef.id)
            docRef.set(propertyWithId).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAllProperties(): Result<List<Property>> {
        return try {
            val snapshot = collection.get().await()
            val properties = snapshot.toObjects(Property::class.java)
            Result.success(properties)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPropertiesByType(tipo: String): Result<List<Property>> {
        return try {
            val snapshot = collection.whereEqualTo("tipo", tipo).get().await()
            val properties = snapshot.toObjects(Property::class.java)
            Result.success(properties)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
