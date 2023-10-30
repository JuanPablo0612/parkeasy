package com.parkeasy.android.domain.model.users

import com.parkeasy.android.data.users.model.UserModel

/**
 * User class.
 */
data class User(
    val id: String = "",
    val admin: Boolean = false,
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val residenceCountry: String = ""
)

/**
 * Converts a User object to a UserModel object.
 *
 * Parameters:
 * - None
 *
 * Returns:
 * - A UserModel object representing the converted User object.
 *
 * Example Usage:
 * ```
 * val user = User(
 * id = 1,
 * admin = true,
 * email = "john.doe@example.com",
 * firstName = "John",
 * lastName = "Doe",
 * residenceCountry = "USA"
 * )
 *
 * val userModel = user.toModel()
 * ```
 *
 * In the above example, the `toModel()` method is called on a User object to
 * convert it to a UserModel object. The resulting UserModel object will have the
 * same values for the id, admin, email, firstName, lastName, and residenceCountry
 * properties as the original User object.
 */
fun User.toModel() = UserModel(
    id = id,
    admin = admin,
    email = email,
    firstName = firstName,
    lastName = lastName,
    residenceCountry = residenceCountry
)