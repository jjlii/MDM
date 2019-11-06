package com.example.domain.user

import com.sun.xml.internal.ws.developer.Serialization
import java.io.Serializable

data class UserResponse(val id : String,
                        val email : String,
                        val name : String,
                        val favourites : MutableList<String>) : Serializable