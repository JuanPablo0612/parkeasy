package com.parkeasy.android.ui.register

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.parkeasy.android.R
import com.parkeasy.android.ui.navigation.Screen

/**
 * This is a Composable function called "RegisterScreen" that is used to display a
 * registration screen in a Jetpack Compose UI. It takes two parameters:
 * "viewModel" of type RegisterViewModel and "navController" of type
 * NavController.
 *
 * The function is annotated with "@OptIn(ExperimentalMaterial3Api::class)" which
 * indicates that it uses experimental Material3 components.
 *
 * Inside the function, the "uiState" is obtained from the "viewModel" to observe
 * the state of the registration process. The "scrollBehavior" and "focusManager"
 * are also obtained from their respective providers.
 *
 * A "LaunchedEffect" is used to navigate to the main screen when the registration
 * is successful. It listens to changes in the "uiState.isSuccess" property and
 * navigates to the main screen using the "navController" when it becomes true.
 *
 * The UI of the registration screen is built using the "Scaffold" composable. It
 * consists of a "LargeTopAppBar" at the top, followed by a "Box" that contains
 * the registration form.
 *
 * The registration form is built using a "Column" composable. It contains several
 * "OutlinedTextField" composables for input fields such as email, password,
 * repeat password, first name, and last name. Each input field has its own
 * validation logic and error messages.
 *
 * A "TextButton" is used to show a dropdown menu for selecting the residence
 * country. The selected country is displayed as the button text, and the dropdown
 * menu is shown when the button is clicked.
 *
 * A "Button" is used to trigger the registration process. It is enabled only when
 * all the input fields are valid. When clicked, it calls the "onRegister"
 * function of the view model.
 *
 * A "TextButton" is used to navigate to the login screen. It checks the previous
 * destination in the navigation stack and navigates accordingly.
 *
 * Below the registration form, there is a section for social login options.
 * Currently, only a Google login option is displayed as an "OutlinedCard" with
 * the Google logo and text.
 *
 * If there is an error during the registration process, an "AlertDialog" is shown
 * with the error message. It can be dismissed by clicking the "OK" button.
 *
 * Overall, this function creates a registration screen UI with input fields,
 * validation logic, navigation, and error handling.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(viewModel: RegisterViewModel = hiltViewModel(), navController: NavController) {
    val uiState = viewModel.uiState
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
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

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(text = stringResource(id = R.string.register_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .verticalScroll(state = rememberScrollState())
            ) {
                Text(
                    text = stringResource(id = R.string.register_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(alignment = Alignment.Start)
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = { viewModel.onEmailTextChanged(it) },
                    label = {
                        Text(text = stringResource(id = R.string.register_email_field))
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Email, contentDescription = null)
                    },
                    enabled = !uiState.isLoading,
                    isError = !uiState.isValidEmail,
                    supportingText = {
                        if (!uiState.isValidEmail) {
                            Text(text = stringResource(id = R.string.register_email_error))
                        }
                    },
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
                        Text(text = stringResource(id = R.string.register_password_field))
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
                            Text(text = stringResource(id = R.string.register_password_error))
                        }
                    },
                    visualTransformation = if (!uiState.showPassword) PasswordVisualTransformation() else VisualTransformation.None,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }),
                    modifier = Modifier.fillMaxWidth(0.9f)
                )

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = uiState.repeatPassword,
                    onValueChange = { viewModel.onRepeatPasswordTextChanged(it) },
                    label = {
                        Text(text = stringResource(id = R.string.register_confirm_password_field))
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Password, contentDescription = null)
                    },
                    enabled = !uiState.isLoading,
                    isError = !uiState.isValidRepeatPassword,
                    supportingText = {
                        if (!uiState.isValidRepeatPassword) {
                            Text(text = stringResource(id = R.string.register_confirm_password_error))
                        }
                    },
                    visualTransformation = if (!uiState.showPassword) PasswordVisualTransformation() else VisualTransformation.None,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }),
                    modifier = Modifier.fillMaxWidth(0.9f)
                )

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = uiState.firstName,
                    onValueChange = { viewModel.onFirstNameTextChanged(it) },
                    label = {
                        Text(text = stringResource(id = R.string.register_first_name_field))
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null)
                    },
                    enabled = !uiState.isLoading,
                    isError = !uiState.isValidFirstName,
                    supportingText = {
                        if (!uiState.isValidFirstName) {
                            Text(text = stringResource(id = R.string.register_first_name_error))
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }),
                    modifier = Modifier.fillMaxWidth(0.9f)
                )

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = uiState.lastName,
                    onValueChange = { viewModel.onLastNameTextChanged(it) },
                    label = {
                        Text(text = stringResource(id = R.string.register_last_name_field))
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null)
                    },
                    enabled = !uiState.isLoading,
                    isError = !uiState.isValidLastName,
                    supportingText = {
                        if (!uiState.isValidLastName) {
                            Text(text = stringResource(id = R.string.register_last_name_error))
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }),
                    modifier = Modifier.fillMaxWidth(0.9f)
                )

                Spacer(modifier = Modifier.height(5.dp))

                Box {
                    TextButton(onClick = { viewModel.onShowResidenceCountriesChanged() }) {
                        val buttonText = uiState.selectedResidenceCountry
                            ?: stringResource(id = R.string.register_residence_country_menu)
                        val icon =
                            if (uiState.showResidenceCountries) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown

                        Text(text = buttonText)

                        Spacer(modifier = Modifier.width(5.dp))

                        Icon(imageVector = icon, contentDescription = null)
                    }

                    DropdownMenu(
                        expanded = uiState.showResidenceCountries,
                        onDismissRequest = { viewModel.onShowResidenceCountriesChanged() }
                    ) {
                        uiState.residenceCountries.forEach {
                            DropdownMenuItem(
                                text = {
                                    Text(text = it)
                                },
                                onClick = { viewModel.onSelectedResidenceCountryChanged(it) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                val enableButton =
                    uiState.isValidEmail && uiState.isValidPassword && uiState.isValidRepeatPassword && uiState.isValidFirstName && uiState.isValidLastName && uiState.selectedResidenceCountry != null

                Button(
                    onClick = { viewModel.onRegister() },
                    enabled = enableButton
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Text(
                            text = stringResource(id = R.string.register_button),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(0.9f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

                TextButton(
                    onClick = {
                        if (navController.previousBackStackEntry!!.destination.route == Screen.Login.route) {
                            navController.navigateUp()
                        } else {
                            navController.navigate(Screen.Login.route)
                        }
                    }
                ) {
                    Text(text = stringResource(id = R.string.register_login_option))
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(R.string.register_social_option),
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedCard(modifier = Modifier.fillMaxWidth(0.7f)) {
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
    }

    if (uiState.errorMessageId != null) {
        AlertDialog(
            onDismissRequest = { viewModel.resetRegisterResult() },
            text = { Text(text = stringResource(id = uiState.errorMessageId)) },
            confirmButton = {
                TextButton(onClick = { viewModel.resetRegisterResult() }) {
                    Text(text = stringResource(id = android.R.string.ok))
                }
            }
        )
    }
}