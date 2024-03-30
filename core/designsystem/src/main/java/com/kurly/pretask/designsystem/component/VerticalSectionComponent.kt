package com.kurly.pretask.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kurly.pretask.core.domain.data.SectionType
import com.kurly.pretask.core.domain.data.UiData
import com.kurly.pretask.core.domain.data.UiProduct


@Stable
@Composable
fun VerticalSectionComponent(
    uiData: UiData,
    onWishChange: (UiProduct) -> Unit
) {

    Column {
        SectionTitle(title = uiData.title)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = 8.dp,
                        vertical = 8.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                uiData.productList.forEach { product ->
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
        }
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
                    type = SectionType.horizontal,
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