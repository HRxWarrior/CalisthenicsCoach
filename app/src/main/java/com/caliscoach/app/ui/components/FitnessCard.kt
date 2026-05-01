package com.caliscoach.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.caliscoach.app.ui.theme.AccentGreen
import com.caliscoach.app.ui.theme.DarkCard

@Composable
fun FitnessCard(modifier: Modifier = Modifier, accent: Color = AccentGreen, onClick: (() -> Unit)? = null, content: @Composable () -> Unit) {
    val shape = RoundedCornerShape(16.dp)
    val mod = modifier.fillMaxWidth().clip(shape).border(1.dp, accent.copy(alpha = 0.3f), shape).background(DarkCard, shape).padding(16.dp)
    if (onClick != null) Box(mod.clickable { onClick() }) { content() }
    else Box(mod) { content() }
}
