package com.onusant.apitask.presentation.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.onusant.apitask.data.repository.PreferenceRepository
import com.onusant.apitask.datastore
import com.onusant.apitask.model.Property
import com.onusant.apitask.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@Composable
fun HomeScreen(
    onRedirectToPropertiesScreen: () -> Unit,
    onLogoutSuccess: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    val preferences = PreferenceRepository(context.datastore)
    val userJson = preferences.getPreference(stringPreferencesKey("user"))
        .collectAsState(initial = null).value

    val recents = viewModel.state.value.recents

    fun onLogout() {
        coroutine.launch {
            preferences.setPreference(stringPreferencesKey("user"), "")
            onLogoutSuccess()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        UserInfo(context = context, userJson = userJson, onLogoutRequest = { onLogout() })
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White)
        ) {
            item {
                val image = ImageRequest.Builder(context)
                    .data("https://www.bankrate.com/2022/04/14092711/what-is-tangible-personal-property.jpg")
                    .dispatcher(Dispatchers.IO)
                    .diskCacheKey("")
                    .memoryCacheKey("")
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.7777778f)
                        .padding(15.dp)
                        .shadow(10.dp, spotColor = Color.Black, shape = RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onRedirectToPropertiesScreen() }
                ) {
                    AsyncImage(
                        model = image,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0x99000000))
                            .padding(20.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(
                            text = "See Properties",
                            fontSize = 55.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            lineHeight = 60.sp
                        )
                    }
                }
            }
            
            item {
                Text(
                    text = "Recents",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 20.dp)
                )
            }

            items(recents) {
                RecentItem(context = context, property = it)
            }

        }
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 50.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "-by Gaurav Saini",
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold,
//                textAlign = TextAlign.Center,
//            )
//            Spacer(modifier = Modifier.height(20.dp))
//            Text(
//                text = "See source code at:",
//                fontSize = 16.sp,
//                textAlign = TextAlign.Center,
//            )
//            ClickableText(
//                text = AnnotatedString(
//                    text = "https://github.com/codegsaini/api_task",
//                    spanStyle = SpanStyle(
//                        color = Color(0xFF0000EE),
//                        textDecoration = TextDecoration.Underline
//                    )
//                ),
//                onClick = {
//                    uriHandler.openUri("https://github.com/codegsaini/api_task")
//                }
//            )
//        }
    }
}

@Composable
fun UserInfo(context: Context, userJson: String?, onLogoutRequest: () -> Unit) {
    if (userJson.isNullOrEmpty()) return

    val user = Json.decodeFromString<User>(userJson)

    val imageUrl = "https://i.pinimg.com/originals/0d/64/98/0d64989794b1a4c9d89bff571d3d5842.jpg"
    val userImage = ImageRequest.Builder(context)
        .data(imageUrl)
        .dispatcher(Dispatchers.IO)
        .diskCacheKey(imageUrl)
        .memoryCacheKey(imageUrl)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AsyncImage(
            model = userImage,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        Text(
            text = user.username,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = { onLogoutRequest() }) {
            Icon(imageVector = Icons.TwoTone.Clear, contentDescription = null)
        }
    }
}

@Composable
fun RecentItem(context: Context, property: Property) {
    val image = ImageRequest.Builder(context)
        .data(property.media_path)
        .dispatcher(Dispatchers.IO)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .diskCacheKey(property.media_path)
        .memoryCacheKey(property.media_path)
        .build()
    Row {
        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .fillMaxHeight()
        )
        Column {
            Text(
                text = property.property_name
            )
            Text(
                text = property.address
            )
        }
    }
}