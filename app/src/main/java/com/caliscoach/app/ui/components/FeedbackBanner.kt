package com.caliscoach.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.caliscoach.app.ui.theme.AccentGreen
import com.caliscoach.app.ui.theme.AccentRed

@Composable
fun FeedbackBanner(message: String, isGood: Boolean) {
    val color = if (isGood) AccentGreen else AccentRed
    Box(Modifier.fillMaxWidth().background(color.copy(alpha = 0.15f), RoundedCornerShape(12.dp)).border(1.dp, color.copy(alpha = 0.4f), RoundedCornerShape(12.dp)).padding(12.dp), contentAlignment = Alignment.Center) {
        Text(message, color = color, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}
