package com.example.futboleros.screens

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.futboleros.data.dao.UserDao
import com.example.futboleros.data.entity.UserEntity

class LoginViewModel(private val userDao: UserDao): ViewModel(){

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess : LiveData<Boolean> = _isSuccess

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password


    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable : LiveData<Boolean> = _loginEnable


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun onLoginChanged(email: String, password: String){


        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)

    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()


    private fun isValidPassword(password: String): Boolean = password.length >= 4

    fun resetSuccess(){
        _isSuccess.value = false
        _errorMessage.value = null
    }


    suspend fun onLoginSelected() {
        val currentEmail = _email.value
        val currentPassword = _password.value

        if (currentEmail == null || currentPassword == null) return

        _isLoading.value = true
        _errorMessage.value = null

        try {
            // Validamos contra la base de datos Room
            val user = userDao.getUserByEmailAndPassword(currentEmail, currentPassword)
            
            if (user != null) {
                _isSuccess.value = true
            } else {
                _errorMessage.value = "Usuario o contraseña incorrectos"
                _isSuccess.value = false
            }

        } catch (error: Exception) {
            Log.e("LoginViewModel", "Error al validar usuario en Room.", error)
            _errorMessage.value = "Error en el sistema"
            _isSuccess.value = false
        } finally {
            _isLoading.value = false
        }
    }

    class Factory(private val userDao: UserDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(userDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }



}

