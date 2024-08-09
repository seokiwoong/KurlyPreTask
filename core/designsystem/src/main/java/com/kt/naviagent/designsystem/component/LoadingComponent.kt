package com.kt.naviagent.designsystem.component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier

@Stable
@Composable
fun LoadingView(modifier: Modifier) {
    CircularProgressIndicator(modifier = modifier)
}
