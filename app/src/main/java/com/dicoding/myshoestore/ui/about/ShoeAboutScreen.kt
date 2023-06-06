package com.dicoding.myshoestore.ui.about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun ShoeAboutScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "Muchamad Rizal Gusnanda Atmaja",
                style = MaterialTheme.typography.h5,
                color = Color.Black
            )
            Text(
                text = "muchamadrizalgusnanda@gmail.com",
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        AsyncImage(
            model = "https://media.licdn.com/dms/image/D4D03AQGbH9r2eiwsiw/profile-displayphoto-shrink_200_200/0/1681183083858?e=1691625600&v=beta&t=Ae_juztUdakdqjZemBpsKq1hetZhq5kb2dHKGEmTyeU",
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }
    }
}