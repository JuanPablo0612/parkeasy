package com.parkeasy.android.data.users.model

import com.parkeasy.android.domain.model.users.User

/**
 * UserModel class.
 */
data class UserModel(
    val id: String,
    val admin: Boolean,
    val email: String,
    val firstName: String,
    val lastName: String,
    val residenceCountry: String
) {
    /**
     * Constructs a new instance of the UserModel class with the specified parameters.
     *
     * @param id the unique identifier of the user (default value is an empty string)
     * @param admin indicates whether the user is an admin or not (default value is false)
     * @param email the email address of the user (default value is an empty string)
     * @param firstName the first name of the user (default value is an empty string)
     * @param lastName the last name of the user (default value is an empty string)
     * @param residenceCountry the country of residence of the user (default value is an empty string)
     */
    constructor() : this(
        id = "",
        admin = false,
        email = "",
        firstName = "",
        lastName = "",
        residenceCountry = ""
    )
}

/**
 * Converts a UserModel object to a User object in the domain layer.
 *
 * Parameters:
 * - None
 *
 * Returns:
 * - User: The converted User object.
 *
 * Throws:
 * - None
 */
fun UserModel.toDomain() = User(
    id = id,
    admin = admin,
    email = email,
    firstName = firstName,
    lastName = lastName,
    residenceCountry = residenceCountry
)