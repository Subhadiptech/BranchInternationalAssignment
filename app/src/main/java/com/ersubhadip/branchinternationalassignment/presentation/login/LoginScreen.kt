package com.ersubhadip.branchinternationalassignment.presentation.login

import LoadingButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ersubhadip.branchinternationalassignment.navigation.Destinations
import com.ersubhadip.branchinternationalassignment.ui.theme.Black
import com.ersubhadip.branchinternationalassignment.ui.theme.BluePrimary
import com.ersubhadip.branchinternationalassignment.ui.theme.LexendDecaRegular
import com.ersubhadip.branchinternationalassignment.ui.theme.LexendDecaSemiBold
import com.ersubhadip.branchinternationalassignment.ui.theme.White
import com.ersubhadip.branchinternationalassignment.utilities.showShortToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {

    val context = LocalContext.current

    val isButtonLoading = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.loginViewModelToLoginScreenEvents.consumeAsFlow().collectLatest { events ->
            when (events) {
                is ViewModelToLoginScreenEvents.InvalidResponseError -> context.showShortToast(
                    events.reason
                )

                ViewModelToLoginScreenEvents.NavigateToHomeScreen -> navController.navigate(
                    Destinations.Home.route
                ) {
                    popUpTo(Destinations.Login.route) {
                        inclusive = true
                    }
                }
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
            Text(
                text = "Quick Chat",
                fontFamily = LexendDecaSemiBold,
                fontSize = 32.sp,
                color = BluePrimary
            )
            Spacer(modifier = Modifier.height(56.dp))
            OutlinedTextField(
                label = { Text(text = "User Name", fontFamily = LexendDecaRegular) },
                value = viewModel.usernameState.value,
                modifier = Modifier
                    .padding(horizontal = 6.dp),
                textStyle = TextStyle(
                    color = Black,
                    fontFamily = LexendDecaRegular,
                    fontSize = 16.sp
                ),
                onValueChange = viewModel::onUsernameChange
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                label = { Text(text = "Password", fontFamily = LexendDecaRegular) },
                value = viewModel.passwordState.value,
                textStyle = TextStyle(
                    color = Black,
                    fontFamily = LexendDecaRegular,
                    fontSize = 16.sp
                ),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .padding(horizontal = 6.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = viewModel::onPasswordChange
            )
            Spacer(modifier = Modifier.height(56.dp))
            LoadingButton(
                onClick = {
                    isButtonLoading.value = true
                    viewModel.userLogin()
                },
                isLoading = isButtonLoading.value
            )
        }
    }
}