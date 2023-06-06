package com.dicoding.myshoestore.ui.home

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.dicoding.myshoestore.R
import com.dicoding.myshoestore.data.ShoeRepository
import com.dicoding.myshoestore.ui.theme.MyShoesStoreTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyShoeStoreApp(
    modifier: Modifier = Modifier,
    viewModel: MyShoesStoreViewModel = viewModel(factory = ViewModelFactory(ShoeRepository()))
) {
    val shoes by viewModel.shoes.collectAsState()
    val query by viewModel.query

    var isSidebarVisible by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                TopAppBar(
                    title = {

                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { isSidebarVisible = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(R.string.menu)
                            )
                        }
                    }
                )
            }
            item {
                SearchBar(
                    query = query,
                    onQueryChange = viewModel::search,
                    modifier = Modifier.background(MaterialTheme.colors.primary)
                )
            }
            items(shoes , key = { it.id}){shoe ->
                ShoeListItem(
                    name = shoe.name,
                    photoUrl = shoe.photoUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItemPlacement(tween(durationMillis = 100))
                )
            }
        }

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                }
            )
        }

        if (isSidebarVisible) {
            Sidebar(
                onClose = { isSidebarVisible = false }
            )
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(stringResource(R.string.search_shoes))
        },
        modifier = modifier
            .padding(start = 8.dp, bottom = 10.dp) // Mengatur margin kiri menjadi 8.dp
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clip(RoundedCornerShape(16.dp))
    )
}
@Composable
fun ShoeListItem(
    name: String,
    photoUrl: String,
    modifier: Modifier = Modifier
) {
    val showSidebar = remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { showSidebar.value = true }
    ) {
        val painter = rememberImagePainter(
            data = photoUrl,
            builder = {
                crossfade(true)
                placeholder(R.drawable.place_holder) // Placeholder image resource
                error(R.drawable.error) // Error image resource
            }
        )

        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .clip(CircleShape)
        )

        Text(
            text = name,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp)
        )
    }

    if (showSidebar.value) {
        Sidebar(
            onClose = { showSidebar.value = false }
        )
    }
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .shadow(10.dp, shape = CircleShape)
            .clip(shape = CircleShape)
            .size(56.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = MaterialTheme.colors.primary
        )
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = stringResource(R.string.scroll_to_top),
        )
    }
}

@Composable
fun Sidebar(
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(200.dp)
            .fillMaxHeight()
    ) {
        Column(modifier = Modifier.padding(top = 50.dp, start = 40.dp )) {
            Text(
                text = "Home",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .clickable { onClose() }
                    .padding(bottom = 10.dp)

            )
            Text(
                text = "Settings",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .clickable { onClose() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyShoesStoreAppPreview() {
    MyShoesStoreTheme() {
        MyShoeStoreApp()
    }
}

@Preview(showBackground = true)
@Composable
fun ShoeListItemPreview() {
    MyShoesStoreTheme() {
        ShoeListItem(
            name = "Ventela",
            photoUrl = ""
        )
    }
}
