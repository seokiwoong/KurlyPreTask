package com.kurly.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kurly.pretask.core.data.repository.KurlyPreferencesDataSource
import com.kurly.pretask.core.domain.GetMainDataUseCase
import com.kurly.pretask.core.domain.data.UiData
import com.kurly.pretask.core.domain.data.UiProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    getMainDataUseCase: GetMainDataUseCase,
    private val kurlyPreferencesDataSource: KurlyPreferencesDataSource
) : ViewModel() {

    private val wishProductIdListFlow: Flow<List<Long>> =
        kurlyPreferencesDataSource.userWishProductPreferenceData
            .map { it.wishProductInfoList }
            .distinctUntilChanged()


    val pagingDataFlow: Flow<PagingData<UiData>> =
        getMainDataUseCase.execute()
            .cachedIn(viewModelScope)
            .combine(wishProductIdListFlow) { pagingData: PagingData<UiData>,
                                              wishList: List<Long> ->
                pagingData.map {
                    it.copy(
                        productList = it.productList.map {
                            it.copy(isWish = wishList.any { id -> id == it.id })
                        }
                    )
                }
            }

    private suspend fun updateWish(product: UiProduct) {
        kurlyPreferencesDataSource.setWishProduct(
            productId = product.id,
            wish = product.isWish
        )
    }

    fun onEvent(event: MainEvent) {
        viewModelScope.launch {
            when (event) {
                is MainEvent.UpdateWish -> {
                    updateWish(event.product)
                }
            }
        }
    }
}

sealed class MainEvent {
    data class UpdateWish(
        val product: UiProduct
    ) : MainEvent()
}
