package com.onusant.apitask.presentation.property

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.onusant.apitask.model.Property
import io.ktor.http.content.CachingProperty
import kotlinx.coroutines.Dispatchers

@Composable
fun PropertyScreen(
    onBackClick: () -> Unit,
    onPropertyRedirect: (id: Int) -> Unit,
    viewModel: PropertyViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val properties = viewModel.state.value.properties
    val loading = viewModel.state.value.loading

    fun onRefresh() {
        viewModel.syncProperties()
    }

    fun onPropertyClick(id: Int) {
        viewModel.addPropertyToRecents(id)
        onPropertyRedirect(id)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Toolbar(onBack = { onBackClick() }, onRefresh = { onRefresh() })
        if (loading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.Transparent)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.Black,
                    strokeWidth = 2.dp
                )
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth().background(Color(0xFFF3F3F3)).weight(1f),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(15.dp)
        ) {
            items(items = properties) {
                Property(context = context, property = it, onClick = { onPropertyRedirect(it.id) })
            }
        }
    }

}

@Composable
private fun Toolbar(onBack: () -> Unit, onRefresh: () -> Unit) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp, horizontal = 15.dp),
        ) {
            IconButton(
                onClick = { onBack() },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(24.dp)
            ) {
                Image(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
            Text(
                text = "Properties",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Center)
            )
            IconButton(
                onClick = { onRefresh() },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(24.dp)
            ) {
                Image(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFF0F0F0))
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Property(context: Context, property: Property, onClick: () -> Unit) {
    val imageUrl = "https://a.khelogame.xyz/${property.media_path}"
    val imageRequest = ImageRequest.Builder(context)
        .data(imageUrl)
        .crossfade(true)
        .dispatcher(Dispatchers.IO)
        .diskCacheKey(imageUrl)
        .memoryCacheKey(imageUrl)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, spotColor = Color(0xFF8A8A8A), shape = RoundedCornerShape(15.dp))
            .background(Color.White, RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(15.dp))
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF3F3F3)),
        ) {
            AsyncImage(
                model = imageRequest,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.7777778f),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "${property.area} sq. ft",
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp)
                    .background(Color(0xAAFFFFFF), RoundedCornerShape(40.dp))
                    .padding(vertical = 1.dp, horizontal = 8.dp)
            )
        }
        Text(
            text = property.property_name,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
        )
        Text(
            text = property.created_at,
            fontSize = 12.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )
        Text(
            text = "Addr.: ${property.address}",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .background(Color(0xFFF5F5F5), RoundedCornerShape(40.dp))
                .padding(vertical = 3.dp, horizontal = 10.dp),
            lineHeight = 15.sp
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Chip(property.pincode, Icons.Default.LocationOn)
            Chip(property.price, Icons.Default.CheckCircle)
            Chip(property.property_categories, Icons.Default.Star)
            Chip(property.property_type, Icons.Default.Share)
            Chip(property.state, Icons.Default.LocationOn)
            Chip(property.vehicle, Icons.Default.ShoppingCart)
        }
    }
}

@Composable
fun Chip(label: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .background(Color(0xFFEFEFEF), RoundedCornerShape(40.dp))
            .padding(horizontal = 10.dp, vertical = 1.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            colorFilter = ColorFilter.tint(Color(0xFF2ADD24))
        )
        Text(
            text = label,
            fontSize = 13.sp
        )
    }
}