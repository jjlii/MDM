package com.example.domain.login

data class UserResponse(val id : String,
                        val email : String,
                        val name : String,
                        val favourites : MutableList<String>)