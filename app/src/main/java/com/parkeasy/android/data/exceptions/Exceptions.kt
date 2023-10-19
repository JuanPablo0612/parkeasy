package com.parkeasy.android.data.exceptions

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.parkeasy.android.R

fun Exception.getMessageId() = when (this) {
    is FirebaseAuthInvalidUserException -> R.string.error_user_does_not_exist
    is FirebaseAuthInvalidCredentialsException -> R.string.error_wrong_password
    is FirebaseAuthUserCollisionException -> R.string.error_user_already_exists
    else -> R.string.error_unknown
}