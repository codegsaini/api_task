package com.onusant.apitask.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onTaskOneClick: () -> Unit,
    onTaskTwoClick: () -> Unit
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp),
            verticalArrangement = Arrangement.spacedBy(50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .background(Color(0xFFEFEFEF), CircleShape)
                    .clip(CircleShape)
                    .clickable { onTaskOneClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Task 1\nPOST API",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
                    .background(Color(0xFFEFEFEF), CircleShape)
                    .clip(CircleShape)
                    .clickable { onTaskTwoClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Task 2\nGET API",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "-by Gaurav Saini",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "See source code at:",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
            ClickableText(
                text = AnnotatedString(
                    text = "https://github.com/codegsaini/api_task",
                    spanStyle = SpanStyle(
                        color = Color(0xFF0000EE),
                        textDecoration = TextDecoration.Underline
                    )
                ),
                onClick = {
                    uriHandler.openUri("https://github.com/codegsaini/api_task")
                }
            )
        }
    }
}