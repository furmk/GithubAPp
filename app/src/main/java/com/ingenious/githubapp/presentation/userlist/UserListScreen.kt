package com.ingenious.githubapp.presentation.userlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ingenious.githubapp.domain.model.UserEntity

@Composable
fun UserListScreen(
    items: LazyPagingItems<UserEntity>,
    reloadItems: () -> Unit,
    onUserClicked: (String) -> Unit,
) {
    val listState = rememberLazyListState()

    if (items.itemCount == 0) {
        EmptyContent(reloadItems)
    } else {
        UsersContent(listState, items, onUserClicked, reloadItems)
    }
}

@Composable
private fun UsersContent(
    listState: LazyListState,
    pagedItems: LazyPagingItems<UserEntity>,
    onUserClicked: (String) -> Unit,
    reloadItems: () -> Unit
) {
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
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
                    ErrorContent(
                        errorMessage = error.error.localizedMessage,
                        onRetry = reloadItems
                    )
                }
            }
        }
    }
}

@Composable
fun ErrorContent(
    errorMessage: String,
    onRetry: () -> Unit
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

            Button(onClick = {
                onRetry.invoke()
            }) {
                Text(text = "Retry", color = Color.White)
            }
        }
    }
}

@Composable
private fun EmptyContent(
    reloadItems: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "An unexpected error has occurred, or no users could be retrieved.",
                color = Color.Black,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                onClick = { reloadItems.invoke() },
            ) {
                Text(
                    color = Color.Black,
                    text = "Reload users"
                )
            }
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
