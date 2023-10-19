package com.parkeasy.android.domain.model.users

import com.parkeasy.android.data.users.model.UserModel

data class User(
    val id: String = "",
    val admin: Boolean = false,
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val residenceCountry: String = ""
)

fun User.toModel() = UserModel(
    id = id,
    admin = admin,
    email = email,
    firstName = firstName,
    lastName = lastName,
    residenceCountry = residenceCountry
)