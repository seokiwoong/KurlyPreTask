package com.kt.naviagent.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
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
fun VerticalSectionComponent(
    uiData: UiData,
    onWishChange: (UiProduct) -> Unit
) {
    val product = remember(uiData.productList) {
        uiData.productList.first()
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp
            ),
    ) {
        VerticalSectionItem(
            name = product.name,
            originalPrice = product.originalPrice,
            discountPrice = product.discountPrice,
            discountRate = product.percent,
            image = product.image,
            isWish = product.isWish,
            onWishChange = {
                onWishChange.invoke(product.copy(isWish = it))
            }
        )
    }
}


@Preview
@Composable
fun VerticalSectionComponentPreview() {
    LazyColumn {
        items(100) {
            VerticalSectionComponent(
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
                            discountPrice = "",
                            percent = "",
                            isWish = false
                        )
                    }.toList()
                ),
                onWishChange = {}
            )
        }
    }


}