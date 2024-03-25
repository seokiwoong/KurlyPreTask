package com.kurly.pretask.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SectionTitle(
    modifier: Modifier = Modifier,
    title: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            style = TextStyle(color = Color.White),
            text = title
        )

    }
}


@Preview(showBackground = true)
@Composable
fun SectionTitlePreview() {
    SectionTitle(title = "Section Title")
}