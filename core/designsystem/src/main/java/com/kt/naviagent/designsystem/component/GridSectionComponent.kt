package com.kt.naviagent.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
fun GridSectionComponent(
    uiData: UiData,
    onWishChange: (UiProduct) -> Unit
) {

    val productList = remember(uiData.productList) {
        uiData.productList
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        LazyVerticalGrid(
            modifier = Modifier.heightIn(max = 1500.dp),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(
                horizontal = 8.dp,
                vertical = 8.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(
                key = {
                    it.uuid
                },
                items = productList
            ) { product ->
                GridSectionItem(
                    name = product.name,
                    originalPrice = product.originalPrice,
                    discountPrice = product.discountPrice,
                    discountRate = product.percent,
                    isWish = product.isWish,
                    image = product.image,
                    onWishChange = {
                        onWishChange(product.copy(isWish = it))
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun GridSectionComponentPreview() {
    GridSectionComponent(
        uiData = UiData(
            title = "title",
            id = 0,
            type = SectionType.Grid,
            productList = (0..<6).map {
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