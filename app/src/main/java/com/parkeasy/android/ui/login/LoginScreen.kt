package com.parkeasy.android.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.parkeasy.android.R
import com.parkeasy.android.ui.navigation.Screen

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel(), navController: NavController) {
    val uiState = viewModel.uiState
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = uiState.isSuccess) {
        if (uiState.isSuccess != null && uiState.isSuccess) {
            navController.navigate(Screen.Main.route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier.align(alignment = Alignment.Start)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.login_title),
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(5.dp))

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.primary)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = stringResource(id = R.string.login_subtitle),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(alignment = Alignment.Start)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailTextChanged(it) },
                label = {
                    Text(text = stringResource(id = R.string.login_email_field))
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = null)
                },
                isError = !uiState.isValidEmail,
                supportingText = {
                    if (!uiState.isValidEmail) {
                        Text(text = stringResource(id = R.string.login_email_error))
                    }
                },
                enabled = !uiState.isLoading,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }),
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.onPasswordTextChanged(it) },
                label = {
                    Text(text = stringResource(id = R.string.login_password_field))
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Password, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = { viewModel.onPasswordVisibilityChanged() }) {
                        val icon =
                            if (uiState.showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility

                        Icon(imageVector = icon, contentDescription = null)
                    }
                },
                enabled = !uiState.isLoading,
                isError = !uiState.isValidPassword,
                supportingText = {
                    if (!uiState.isValidPassword) {
                        Text(text = stringResource(id = R.string.login_password_error))
                    }
                },
                visualTransformation = if (!uiState.showPassword) PasswordVisualTransformation() else VisualTransformation.None,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.clearFocus()
                }),
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            Spacer(modifier = Modifier.height(10.dp))

            val enableButton = uiState.isValidEmail && uiState.isValidPassword

            Button(
                onClick = { viewModel.onLogin() },
                enabled = !uiState.isLoading && enableButton,
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        text = stringResource(id = R.string.login_button),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(0.9f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            TextButton(
                onClick = {
                    if (navController.previousBackStackEntry!!.destination.route == Screen.Register.route) {
                        navController.navigateUp()
                    } else {
                        navController.navigate(route = Screen.Register.route)
                    }
                }
            ) {
                Text(text = stringResource(id = R.string.login_register_option))
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.login_social_option),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedCard(modifier = Modifier.fillMaxWidth(0.6f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google_logo),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = stringResource(id = R.string.social_option_google),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }

    if (uiState.errorMessageId != null) {
        AlertDialog(
            onDismissRequest = { viewModel.resetLoginResult() },
            text = { Text(text = stringResource(id = uiState.errorMessageId)) },
            confirmButton = {
                Button(onClick = { viewModel.resetLoginResult() }) {
                    Text(text = stringResource(id = android.R.string.ok))
                }
            }
        )
    }
}