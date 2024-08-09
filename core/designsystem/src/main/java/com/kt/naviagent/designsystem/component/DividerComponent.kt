package com.kt.naviagent.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Stable
@Composable
fun Divider(modifier: Modifier = Modifier, color: Color) {
    Spacer(
        modifier = modifier
            .padding(vertical = 18.dp)
            .height(0.5.dp)
            .fillMaxWidth()
            .background(color.copy(alpha = 0.5f))
    )
}


@Preview
@Composable
private fun DividerPreview() {
    Divider(color = Color.Gray)
}