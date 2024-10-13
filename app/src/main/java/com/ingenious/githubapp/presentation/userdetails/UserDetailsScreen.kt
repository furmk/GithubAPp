package com.ingenious.githubapp.presentation.userdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.ingenious.githubapp.domain.model.UserDetailsEntity
import com.ingenious.githubapp.presentation.model.UserDetailsState

@Composable
fun UserDetailsScreen(
    state: UserDetailsState,
    loadUserDetails: () -> Unit,
) {

    LaunchedEffect(true) {
        loadUserDetails()
    }

    when (state) {
        is UserDetailsState.Loaded -> UserDetailsContent(state.userDetails)
        is UserDetailsState.Loading -> Progress()
    }
}

@Composable
fun Progress() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.Center),
            strokeWidth = 8.dp
        )
    }
}

@Composable
fun UserDetailsContent(userDetails: UserDetailsEntity) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularImageWithLoading(url = userDetails.avatarUrl)
        if (userDetails.email.isNotEmpty() || userDetails.name.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    text = userDetails.name
                )

                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = userDetails.email
                )
            }
        }
        Text(
            modifier = Modifier.padding(10.dp),
            text = userDetails.location,
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic
        )
    }
}

@Composable
fun CircularImageWithLoading(url: String) {

    val painter = rememberAsyncImagePainter(model = url)
    val painterState by painter.state.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier
            .size(170.dp)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        when (painterState) {
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator()
            }

            is AsyncImagePainter.State.Success -> {
                Image(
                    painter = painter,
                    contentDescription = "Circular Image",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )
            }

            else -> Unit
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowUserDetailsPreview() {
    val userDetails = UserDetailsEntity(
        login = "furmk",
        name = "Kacper",
        location = "Cracow",
        email = "furmk@exampl.com",
        avatarUrl = "https://polskifm.com/wp-content/uploads/wisla-krakow-2.jpg"
    )
    val state = UserDetailsState.Loaded(userDetails)

    UserDetailsScreen(
        state,
    ) {}
}