@file:OptIn(ExperimentalMaterialApi::class)

package com.kurly.feature.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.kurly.pretask.core.domain.data.SectionType
import com.kurly.pretask.core.domain.data.UiData
import com.kurly.pretask.designsystem.component.Divider
import com.kurly.pretask.designsystem.component.ErrorMessage
import com.kurly.pretask.designsystem.component.GridSectionComponent
import com.kurly.pretask.designsystem.component.HorizontalSectionComponent
import com.kurly.pretask.designsystem.component.LoadingNextPageItem
import com.kurly.pretask.designsystem.component.VerticalSectionComponent
import com.kurly.pretask.designsystem.theme.KurlyPreTaskTheme
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

    Box(modifier = Modifier.pullRefresh(state)) {
        LazyColumn {
            items(
                pagingItems.itemCount,
                key = pagingItems.itemKey { it.uuid },
                contentType = pagingItems.itemContentType { item -> item.type.toString() }
            ) { index ->
                val item = pagingItems[index] ?: return@items

                when (item.type) {
                    SectionType.grid -> {
                        GridSectionComponent(
                            uiData = item,
                            onWishChange = {
                                onEvent.invoke(MainEvent.UpdateWish(it))
                            }
                        )
                    }

                    SectionType.horizontal -> {
                        HorizontalSectionComponent(
                            uiData = item,
                            onWishChange = {
                                onEvent.invoke(MainEvent.UpdateWish(it))
                            }
                        )
                    }

                    SectionType.vertical -> {
                        VerticalSectionComponent(
                            uiData = item,
                            onWishChange = {
                                onEvent.invoke(MainEvent.UpdateWish(it))
                            }
                        )
                    }

                    SectionType.empty -> {
                        Spacer(modifier = Modifier)
                    }
                }


                if (index != pagingItems.itemCount - 1) {
                    Divider(color = Color.Gray)
                }
            }

            if (errorState) {
                val error = pagingItems.loadState.refresh as LoadState.Error
                item {
                    ErrorMessage(
                        modifier = Modifier.fillParentMaxSize(),
                        message = error.error.localizedMessage
                            ?: stringResource(id = R.string.unknown_error),
                        onClickRetry = { pagingItems.retry() })
                }
            } else if (appendLoading) {
                item { LoadingNextPageItem(modifier = Modifier) }
            } else if (appendLoadingError) {
                val error = pagingItems.loadState.append as LoadState.Error
                item {
                    ErrorMessage(
                        modifier = Modifier,
                        message = error.error.localizedMessage!!,
                        onClickRetry = { pagingItems.retry() })
                }
            }

            item { Spacer(modifier = Modifier.padding(16.dp)) }
        }


        PullRefreshIndicator(
            refreshing = refreshing,
            state = state,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KurlyPreTaskTheme {
        MainScreen()
    }
}