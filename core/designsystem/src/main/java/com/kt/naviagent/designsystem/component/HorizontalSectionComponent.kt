package com.kt.naviagent.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kt.naviagent.core.domain.data.SectionType
import com.kt.naviagent.core.domain.data.UiData
import com.kt.naviagent.core.domain.data.UiProduct


@Stable
@Composable
fun HorizontalSectionComponent(
    uiData: UiData,
    onWishChange: (UiProduct) -> Unit
) {
    val productList = remember(uiData.productList) {
        uiData.productList
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(
            horizontal = 8.dp,
            vertical = 8.dp
        )
    ) {
        items(items = productList,
            key = { it.uuid }) { product ->
            HorizontalSectionItem(
                name = product.name,
                originalPrice = product.originalPrice,
                discountPrice = product.discountPrice,
                discountRate = product.percent,
                image = product.image,
                isWish = product.isWish
            ) {
                onWishChange.invoke(product.copy(isWish = it))
            }
        }
    }
}


@Preview
@Composable
fun HorizontalSectionComponentPreview() {
    HorizontalSectionComponent(
        uiData = UiData(
            title = "title",
            id = 0,
            type = SectionType.Horizontal,
            productList = (0..10).map {
                UiProduct(
                    id = it.toLong(),
                    name = "name",
                    image = "image",
                    originalPrice = "3000원",
                    discountPrice = "1500원",
                    percent = "30%",
                    isWish = false
                )
            }.toList()
        ),
        onWishChange = {}
    )

}