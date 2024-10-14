package com.ingenious.githubapp.presentation.userlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.ingenious.githubapp.domain.model.UserEntity
import kotlinx.coroutines.flow.StateFlow

@Composable
fun UserListScreen(
    state: StateFlow<PagingData<UserEntity>>,
    onUserClicked: (String) -> Unit,
) {
    val listState = rememberLazyListState()
    val pagedItems = state.collectAsLazyPagingItems()

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
                    Text("Error: ${error.error.localizedMessage}")
                }
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
