package com.example.domain.user

import java.io.Serializable

data class UserResponse(val id : String,
                        var email : String,
                        var name : String,
                        var favourites : MutableList<String>,
                        var notificationToken : String) : Serializable