package com.ersubhadip.branchinternationalassignment.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ersubhadip.branchinternationalassignment.ui.theme.White
import org.koin.java.KoinJavaComponent.inject


@Preview
@Composable
fun LoginScreen() {
    val viewModel: LoginViewModel by inject(LoginViewModel::class.java)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
//        Image(modifier = Modifier.fillMaxSize(), painter = painterResource(id = R.drawable.ic_login_bg), contentDescription = null, contentScale = ContentScale.Fit)
    }
}