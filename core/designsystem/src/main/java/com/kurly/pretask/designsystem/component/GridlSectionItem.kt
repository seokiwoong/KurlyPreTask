package com.kurly.pretask.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GridSectionItem(
    modifier: Modifier = Modifier,
    name: String,
    image: String? = null,
    originalPrice: String,
    discountPrice: String?,
    discountRate: String?,
    isWish: Boolean,
    onWishChange: (Boolean) -> Unit = {}
) {
    HorizontalSectionItem(
        modifier = modifier,
        name = name,
        image = image,
        originalPrice = originalPrice,
        discountPrice = discountPrice,
        discountRate = discountRate,
        isWish = isWish,
        onWishChange = onWishChange
    )
}

@Preview
@Composable
private fun GridItemPreview() {
    var isWish by remember {
        mutableStateOf(false)
    }
    Column {
        GridSectionItem(
            name = "[샐러딩] 레디믹스 스탠다드 150g",
            image = "",
            originalPrice = "2000원",
            discountPrice = "1000원",
            discountRate = "50%",
            isWish = isWish,
            onWishChange = {
                isWish = it
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        GridSectionItem(
            name = "[샐러딩] 레디믹스 스탠다드 150g",
            image = "",
            originalPrice = "2000원",
            discountPrice = null,
            discountRate = null,
            isWish = isWish,
            onWishChange = {
                isWish = it
            }
        )
    }
}