package com.kurly.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.kurly.pretask.core.domain.data.UiData
import com.kurly.pretask.designsystem.component.VerticalSectionItem
import com.kurly.pretask.designsystem.theme.KurlyPreTaskTheme

@Composable
fun MainScreen() {
    val mainScreenViewModel : MainScreenViewModel = hiltViewModel()
    val data = mainScreenViewModel.sectionInfoList.collectAsLazyPagingItems()

    MainContent(
        data
    )
}


@Composable
fun MainContent(data: LazyPagingItems<UiData>) {
    LazyColumn(
        userScrollEnabled = true,
        modifier = Modifier
            .background(Color.White)
    ) {
        items(
            data.itemCount,
            key = data.itemKey { it.id },
            contentType = data.itemContentType { item -> item.type }
        ){index ->
            val item = data[index] ?: return@items
            VerticalSectionItem(
                name = item.title,
                originalPrice = "1000원",
                discountPrice = null,
                discountRate = null,
                isSoldOut = false,
                isWish = false
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KurlyPreTaskTheme {
        MainScreen()
    }
}