package com.kurly.pretask.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kurly.pretask.core.domain.data.SectionType
import com.kurly.pretask.core.domain.data.UiData
import com.kurly.pretask.core.domain.data.UiProduct


@Composable
fun HorizontalSectionComponent(
    uiData: UiData,
    onWishChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        uiData.productList?.let { productList ->
            LazyRow(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                contentPadding = PaddingValues(
                    horizontal = 8.dp,
                    vertical = 8.dp
                )
            ) {
                items(items = productList,
                    key = { it.id }) { product ->
                    HorizontalSectionItem(
                        name = product.name,
                        originalPrice = product.originalPrice,
                        discountPrice = product.discountPrice,
                        discountRate = product.discountPrice,
                        isWish = product.isWish,
                        onWishChange = onWishChange
                    )
                }
            }
        } ?: LoadingView(modifier = Modifier.align(Alignment.Center))
    }
}


@Preview
@Composable
fun HorizontalSectionComponentPreview() {
    HorizontalSectionComponent(
        uiData = UiData(
            title = "title",
            id = 0,
            type = SectionType.horizontal,
            1,
            productList = (0..10).map {
                UiProduct(
                    id = it.toLong(),
                    name = "name",
                    image = "image",
                    originalPrice = "3000원",
                    discountPrice = null,
                    percent = null,
                    isWish = false
                )
            }.toList()
        ),
        onWishChange = {}
    )

}