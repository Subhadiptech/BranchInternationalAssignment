package com.ersubhadip.branchinternationalassignment.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ersubhadip.branchinternationalassignment.R
import com.ersubhadip.branchinternationalassignment.data.pojos.ChatItem
import com.ersubhadip.branchinternationalassignment.navigation.Destinations
import com.ersubhadip.branchinternationalassignment.ui.theme.Black
import com.ersubhadip.branchinternationalassignment.ui.theme.BlueLight
import com.ersubhadip.branchinternationalassignment.ui.theme.BluePrimary
import com.ersubhadip.branchinternationalassignment.ui.theme.GrayDark
import com.ersubhadip.branchinternationalassignment.ui.theme.LexendDecaLight
import com.ersubhadip.branchinternationalassignment.ui.theme.LexendDecaRegular
import com.ersubhadip.branchinternationalassignment.ui.theme.LexendDecaSemiBold
import com.ersubhadip.branchinternationalassignment.ui.theme.White
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow

@Composable
@Preview
fun HomeScreen(navController: NavController) {

    val viewModel: HomeViewModel = hiltViewModel()

    val resp = viewModel.chats.collectAsState()

    val listOfChats = remember {
        mutableStateOf(emptyList<ChatItem>())
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.homeViewModelToHomeScreenEvents.consumeAsFlow().collectLatest { events ->
            when (events) {
                is ViewModelToHomeScreenEvents.Success -> {
                    listOfChats.value = resp.value
                }

                is ViewModelToHomeScreenEvents.Failure -> {
                    listOfChats.value = emptyList()
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
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Inbox",
                    fontSize = 32.sp,
                    fontFamily = LexendDecaSemiBold,
                    color = Black
                )
                Spacer(modifier = Modifier.height(24.dp))
                Divider(thickness = 1.dp, color = Black)
                Spacer(modifier = Modifier.height(24.dp))
            }

            if (listOfChats.value.isNotEmpty()) {
                items(listOfChats.value) {
                    ChatCard(
                        sender = it.user_id,
                        body = it.body,
                        id = it.id,
                        date = viewModel.formatDate(it.timestamp),
                        threadID = it.thread_id,
                        navController = navController
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            } else {
                item {
                    Text(
                        text = "No Chats found",
                        color = GrayDark,
                        fontFamily = LexendDecaRegular,
                        fontSize = 16.sp
                    )
                }
            }

        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatCard(
    sender: String?,
    body: String?,
    id: Int?,
    threadID: Int?,
    date: String,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(8.dp))
            .background(BlueLight)
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable {
                navController.navigate(
                    Destinations.Chat.withArgs(
                        "$threadID",
                        sender ?: "",
                        date,
                        body ?: ""
                    )
                )
            }
    ) {
        Column {
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .clip(RoundedCornerShape(500.dp))
                    .background(BluePrimary)
                    .padding(8.dp),
                text = "$id",
                color = White,
                fontSize = 8.sp,
                fontFamily = LexendDecaSemiBold,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(500.dp))
                        .weight(1f),
                    painter = painterResource(id = R.drawable.ic_dummy_profile),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Column(modifier = Modifier.weight(4f)) {
                    Text(
                        text = sender ?: "Unknown",
                        color = BluePrimary,
                        fontFamily = LexendDecaSemiBold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        maxLines = 1,
                        modifier = Modifier.basicMarquee(),
                        text = body ?: "",
                        color = Black,
                        fontFamily = LexendDecaRegular,
                        fontSize = 12.sp
                    )
                }
            }
            Text(
                modifier = Modifier.align(Alignment.End),
                text = date,
                color = GrayDark,
                fontSize = 10.sp,
                fontFamily = LexendDecaLight,
            )
        }
    }
}