package com.ingenious.githubapp.presentation.userlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ingenious.githubapp.R
import com.ingenious.githubapp.domain.model.UserEntity

@Composable
fun UserListScreen(
    items: LazyPagingItems<UserEntity>,
    onUserClicked: (String) -> Unit,
) {
    val listState = rememberLazyListState()

    UsersContent(listState, items, onUserClicked)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UsersContent(
    listState: LazyListState,
    pagedItems: LazyPagingItems<UserEntity>,
    onUserClicked: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(pagedItems.itemCount) { index ->
                val user = requireNotNull(pagedItems[index])
                UserListItem(
                    user = user,
                    onClick = { onUserClicked.invoke(user.login) }
                )
            }
            when {
                pagedItems.loadState.refresh is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }

                pagedItems.loadState.append is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }

                pagedItems.loadState.append is LoadState.Error -> {
                    val error = pagedItems.loadState.append as LoadState.Error
                    item {
                        ErrorContent(errorMessage = error.error.localizedMessage)
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorContent(
    errorMessage: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFFFCCCB)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Error: $errorMessage",
                color = Color.Red,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun UserListItem(
    user: UserEntity,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            fontSize = 15.sp,
            text = user.login
        )
    }
    HorizontalDivider(
        color = Color.LightGray
    )
}
