package com.ersubhadip.branchinternationalassignment.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ersubhadip.branchinternationalassignment.navigation.Destinations
import com.ersubhadip.branchinternationalassignment.ui.theme.BluePrimary
import com.ersubhadip.branchinternationalassignment.ui.theme.White
import com.ersubhadip.branchinternationalassignment.utilities.showShortToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.loginViewModelToLoginScreenEvents.consumeAsFlow().collectLatest { events ->
            when (events) {
                is ViewModelToLoginScreenEvents.InvalidResponseError -> context.showShortToast(
                    events.reason
                )

                ViewModelToLoginScreenEvents.NavigateToHomeScreen -> navController.navigate(
                    Destinations.Home.route
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                label = { Text(text = "User Name") },
                value = viewModel.usernameState.value,
                modifier = Modifier
                    .padding(horizontal = 6.dp),
                onValueChange = viewModel::onUsernameChange
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                label = { Text(text = "Password") },
                value = viewModel.passwordState.value,
                modifier = Modifier
                    .padding(horizontal = 6.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                onValueChange = viewModel::onPasswordChange
            )
            Spacer(modifier = Modifier.height(56.dp))
            Button(
                onClick = viewModel::userLogin,
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
            ) {
                Text(text = "Login")
            }
        }
    }
}