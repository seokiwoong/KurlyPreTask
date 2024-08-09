@file:OptIn(ExperimentalMaterialApi::class)

package com.kt.naviagent.feature.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.kt.naviagent.core.domain.data.SectionType
import com.kt.naviagent.core.domain.data.UiData
import com.kt.naviagent.designsystem.component.Divider
import com.kt.naviagent.designsystem.component.ErrorMessage
import com.kt.naviagent.designsystem.component.GridSectionComponent
import com.kt.naviagent.designsystem.component.HorizontalSectionComponent
import com.kt.naviagent.designsystem.component.LoadingNextPageItem
import com.kt.naviagent.designsystem.component.SectionTitle
import com.kt.naviagent.designsystem.component.VerticalSectionComponent
import com.kt.naviagent.designsystem.theme.KtNaviAgentTheme
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val mainScreenViewModel: MainScreenViewModel = hiltViewModel()
    val pagingItems = mainScreenViewModel.pagingDataFlow.collectAsLazyPagingItems()

    MainContent(
        pagingItems = pagingItems,
        onEvent = { event ->
            mainScreenViewModel.onEvent(event)
        }
    )
}

@Composable
fun MainContent(
    pagingItems: LazyPagingItems<UiData>,
    onEvent: (MainEvent) -> Unit,
) {

    val scope = rememberCoroutineScope()

    val refreshing by remember {
        derivedStateOf {
            pagingItems.loadState.refresh is LoadState.Loading
        }
    }

    val state = rememberPullRefreshState(refreshing, {
        scope.launch {
            pagingItems.refresh()
        }
    })

    val errorState by remember {
        derivedStateOf {
            pagingItems.loadState.refresh is LoadState.Error
        }
    }

    val appendLoading by remember {
        derivedStateOf {
            pagingItems.loadState.append is LoadState.Loading
        }
    }

    val appendLoadingError by remember {
        derivedStateOf {
            pagingItems.loadState.append is LoadState.Error
        }
    }

    val drawDividerIndex: (Int) -> Boolean by remember {
        derivedStateOf {
            {
                it != 0 && it != pagingItems.itemCount - 1
            }
        }
    }

    Box(modifier = Modifier.pullRefresh(state)) {
        LazyColumn {
            item(contentType = "topSpace") { Spacer(modifier = Modifier.padding(8.dp)) }
            items(
                pagingItems.itemCount,
                key = pagingItems.itemKey { it.uuid },
                contentType = pagingItems.itemContentType { item -> item.type.toString() }
            ) { index ->
                val item = pagingItems[index] ?: return@items
                ListItemByUiData(item, onEvent, drawDividerIndex(index))
            }
            updateLoaderState(
                errorState = errorState,
                pagingItems = pagingItems,
                appendLoading = appendLoading,
                appendLoadingError = appendLoadingError
            )
            item(contentType = "bottomSpace") { Spacer(modifier = Modifier.padding(16.dp)) }
        }


        PullRefreshIndicator(
            refreshing = refreshing,
            state = state,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun ListItemByUiData(
    item: UiData,
    onEvent: (MainEvent) -> Unit,
    drawDividerIndex: Boolean
) {
    when (item.type) {
        SectionType.Grid -> {
            GridSectionComponent(
                uiData = item,
                onWishChange = {
                    onEvent.invoke(MainEvent.UpdateWish(it))
                }
            )
        }

        SectionType.Horizontal -> {
            HorizontalSectionComponent(
                uiData = item,
                onWishChange = {
                    onEvent.invoke(MainEvent.UpdateWish(it))
                }
            )
        }

        SectionType.Vertical -> {
            VerticalSectionComponent(
                uiData = item,
                onWishChange = {
                    onEvent.invoke(MainEvent.UpdateWish(it))
                }
            )
        }

        SectionType.Empty -> {
            Spacer(modifier = Modifier)
        }

        SectionType.Header -> {
            if (drawDividerIndex) {
                Divider(color = Color.Gray)
            }
            SectionTitle(title = item.title)
        }
    }
}

private fun LazyListScope.updateLoaderState(
    errorState: Boolean,
    pagingItems: LazyPagingItems<UiData>,
    appendLoading: Boolean,
    appendLoadingError: Boolean
) {
    if (errorState) {
        val error = pagingItems.loadState.refresh as LoadState.Error
        item(contentType = "error") {
            ErrorMessage(
                modifier = Modifier.fillParentMaxSize(),
                message = error.error.localizedMessage
                    ?: stringResource(id = R.string.unknown_error),
                onClickRetry = { pagingItems.retry() })
        }
    } else if (appendLoading) {
        item(contentType = "appendLoading"
        ) { LoadingNextPageItem(modifier = Modifier) }
    } else if (appendLoadingError) {
        val error = pagingItems.loadState.append as LoadState.Error
        item(contentType = "appendLoadingError"
        ) {
            ErrorMessage(
                modifier = Modifier,
                message = error.error.localizedMessage!!,
                onClickRetry = { pagingItems.retry() })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KtNaviAgentTheme {
        MainScreen()
    }
}