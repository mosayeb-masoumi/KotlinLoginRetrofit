package com.example.kotlinlogin.models

data class LoginResponse (

        val error: Boolean,
        val message: String,
        val user : User
)
