package com.kurly.pretask.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SectionTitle(
    modifier: Modifier = Modifier,
    title: String,
    showDivider: Boolean = false
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        if (showDivider) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = Color.Gray)
            )
        }
        Text(
            modifier = Modifier.padding(8.dp),
            text = title
        )

    }
}


@Preview(showBackground = true)
@Composable
fun SectionTitlePreview() {
    Column {
        SectionTitle(title = "Section Title")
        SectionTitle(title = "Section Title", showDivider = true)
    }
}