package com.ersubhadip.branchinternationalassignment.presentation.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ersubhadip.branchinternationalassignment.ui.theme.Black
import com.ersubhadip.branchinternationalassignment.ui.theme.BluePrimary
import com.ersubhadip.branchinternationalassignment.ui.theme.GrayDark
import com.ersubhadip.branchinternationalassignment.ui.theme.LexendDecaLight
import com.ersubhadip.branchinternationalassignment.ui.theme.LexendDecaRegular
import com.ersubhadip.branchinternationalassignment.ui.theme.LexendDecaSemiBold
import com.ersubhadip.branchinternationalassignment.ui.theme.PrimaryGray
import com.ersubhadip.branchinternationalassignment.ui.theme.White
import com.ersubhadip.branchinternationalassignment.utilities.showLongToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ChatScreen(id: Int, userId: String, body: String, date: String) {

    val viewModel: ChatViewModel = hiltViewModel()

    val isReplyVisible = remember {
        mutableStateOf(false)
    }
    val isReplyFieldVisible = remember {
        mutableStateOf(true)
    }

    val context = LocalContext.current

    viewModel.setId(id)

    LaunchedEffect(key1 = Unit) {
        viewModel.chatViewModelToChatScreenEvents.consumeAsFlow().collectLatest { events ->
            when (events) {
                ViewModelToChatScreenEvents.Success -> {
                    isReplyVisible.value = !isReplyVisible.value
                    isReplyFieldVisible.value = !isReplyFieldVisible.value
                }

                is ViewModelToChatScreenEvents.Failure -> {
                    context.showLongToast(events.errorMessage)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(horizontal = 16.dp)
    ) {
        Column {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Chat with $userId",
                fontSize = 24.sp,
                fontFamily = LexendDecaSemiBold,
                color = Black
            )
            Text(
                text = "Sent On: $date",
                fontSize = 12.sp,
                fontFamily = LexendDecaLight
            )
            Spacer(modifier = Modifier.height(24.dp))
            Divider(thickness = 1.dp, color = Black)
            Spacer(modifier = Modifier.height(120.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(BluePrimary)
                    .padding(horizontal = 6.dp, vertical = 12.dp),
                text = body,
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                fontFamily = LexendDecaRegular,
                color = White
            )
            Spacer(modifier = Modifier.height(20.dp))
            AnimatedVisibility(visible = isReplyVisible.value) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(PrimaryGray)
                        .padding(horizontal = 6.dp, vertical = 12.dp),
                    text = viewModel.replyState.value,
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    fontFamily = LexendDecaRegular,
                    color = White
                )
            }
        }

        AnimatedVisibility(
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            visible = isReplyFieldVisible.value,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    label = {
                        Text(
                            text = "Reply here...",
                            fontFamily = LexendDecaLight,
                            color = GrayDark,
                            fontSize = 12.sp
                        )
                    },
                    textStyle = TextStyle(
                        color = Black,
                        fontFamily = LexendDecaRegular,
                        fontSize = 16.sp
                    ),
                    value = viewModel.replyState.value,
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .weight(1f),
                    onValueChange = viewModel::onReplyChange
                )
                Spacer(modifier = Modifier.width(6.dp))
                Button(
                    onClick = { viewModel.sendReply() },
                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                ) {
                    Text(text = "Reply")
                }
            }
        }
    }
}