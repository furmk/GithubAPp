package com.ingenious.githubapp.presentation.userdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.ingenious.githubapp.domain.model.UserDetailsEntity
import com.ingenious.githubapp.presentation.userdetails.model.UserDetailsState

@Composable
fun UserDetailsScreen(
    state: UserDetailsState,
    loadUserDetails: () -> Unit,
    navigateBack: () -> Unit
) {

    LaunchedEffect(true) {
        loadUserDetails()
    }

    when (state) {
        is UserDetailsState.Loaded -> UserDetailsContent(state.userDetails)
        is UserDetailsState.Loading -> ProgressContent()
        UserDetailsState.Error -> ErrorContent(navigateBack)
    }
}

@Composable
fun ErrorContent(navigateBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFCCCB))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "An unknown error occurred.\nUnable to obtain user details.",
                color = Color.Red,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD32F2F)
                ),
                onClick = { navigateBack.invoke() },
            ) {
                Text(text = "Navigate back to users list")
            }
        }
    }
}

@Composable
fun ProgressContent() {
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