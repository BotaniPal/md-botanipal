package com.botanipal.loginregisterfirebase.ui.register

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class RegisterViewModel : ViewModel() {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Registrasi berhasil
                    // Anda dapat menambahkan logika lanjutan seperti menyimpan data pengguna di Firestore dll.
                    // Contoh: Firebase.firestore.collection("users").document(auth.currentUser.uid).set(user)
                } else {
                    // Registrasi gagal
                    val errorMessage = (task.exception as? FirebaseAuthException)?.message
                    // Handle error
                }
            }
    }
}

