package com.parkeasy.android.data.users.model

import com.parkeasy.android.domain.model.users.User

data class UserModel(
    val id: String,
    val admin: Boolean,
    val email: String,
    val firstName: String,
    val lastName: String,
    val residenceCountry: String
) {
    constructor() : this(
        id = "",
        admin = false,
        email = "",
        firstName = "",
        lastName = "",
        residenceCountry = ""
    )
}

fun UserModel.toDomain() = User(
    id = id,
    admin = admin,
    email = email,
    firstName = firstName,
    lastName = lastName,
    residenceCountry = residenceCountry
)