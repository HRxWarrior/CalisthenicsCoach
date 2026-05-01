package com.caliscoach.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.caliscoach.app.ui.theme.AccentGreen
import com.caliscoach.app.ui.theme.TextDim
import com.caliscoach.app.ui.theme.TextSecondary

@Composable
fun SimpleBarChart(data: List<Pair<String, Int>>, maxVal: Int, barColor: Color = AccentGreen) {
    val effectiveMax = if (maxVal > 0) maxVal else 1
    Row(Modifier.fillMaxWidth().height(120.dp).padding(horizontal = 4.dp), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.Bottom) {
        data.forEach { (label, value) ->
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                Text("$value", color = TextSecondary, fontSize = 10.sp)
                Spacer(Modifier.height(4.dp))
                val h = (value.toFloat() / effectiveMax * 80).coerceIn(4f, 80f)
                Box(Modifier.width(20.dp).height(h.dp).clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)).background(barColor))
                Spacer(Modifier.height(4.dp))
                Text(label, color = TextDim, fontSize = 9.sp)
            }
        }
    }
}
