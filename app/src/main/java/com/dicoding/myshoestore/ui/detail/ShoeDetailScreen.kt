package com.dicoding.myshoestore.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.dicoding.myshoestore.model.Shoe
import com.dicoding.myshoestore.model.ShoesData
import com.dicoding.myshoestore.ui.theme.MyShoesStoreTheme

@Composable
fun ShoeDetailScreen(
    shoeId: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val selectedShoe = remember {
        mutableStateOf<Shoe?>(null)
    }

    selectedShoe.value = ShoesData.shoes.find { it.id == shoeId }

    selectedShoe.value?.let { shoe ->
        Column(modifier = modifier.padding(start = 16.dp)) {
            val imageRequest = remember(context) {
                ImageRequest.Builder(context)
                    .data(shoe.photoUrl)
                    .build()
            }
            Image(
                painter = rememberImagePainter(request = imageRequest),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = shoe.name,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = shoe.description,
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = shoe.price,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.secondary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoeDetailScreenPreview() {
    val shoe = ShoesData.shoes.first() // Menampilkan detail sepatu pertama
    MyShoesStoreTheme {
        ShoeDetailScreen(shoeId = shoe.id)
    }
}
