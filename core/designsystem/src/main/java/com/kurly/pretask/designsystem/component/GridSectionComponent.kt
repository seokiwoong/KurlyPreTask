package com.kurly.pretask.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kurly.pretask.core.domain.data.SectionType
import com.kurly.pretask.core.domain.data.UiData
import com.kurly.pretask.core.domain.data.UiProduct


@Composable
fun GridSectionComponent(
    uiData: UiData,
    onWishChange: (Boolean) -> Unit
) {
    Column {
        SectionTitle(title = uiData.title)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            uiData.productList?.let { productList ->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(
                        horizontal = 8.dp,
                        vertical = 8.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items(items = productList,
                        key = { it.id }) { product ->
                        GridSectionItem(
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
}


@Preview
@Composable
fun GridSectionComponentPreview() {
    GridSectionComponent(
        uiData = UiData(
            title = "title",
            id = 0,
            type = SectionType.grid,
            1,
            productList = (0..<6).map {
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