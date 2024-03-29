package com.kurly.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kurly.pretask.core.data.repository.KurlyPreferencesDataSource
import com.kurly.pretask.core.domain.GetMainDataUseCase
import com.kurly.pretask.core.domain.GetSectionInfoParams
import com.kurly.pretask.core.domain.data.UiData
import com.kurly.pretask.core.domain.data.UiProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    getMainDataUseCase: GetMainDataUseCase,
    private val kurlyPreferencesDataSource: KurlyPreferencesDataSource
) : ViewModel() {

    val sectionInfoList: Flow<PagingData<UiData>> =
        getMainDataUseCase.execute(GetSectionInfoParams())
            .cachedIn(viewModelScope)

    fun updateWish(product: UiProduct) {
        viewModelScope.launch {
            kurlyPreferencesDataSource.setWishProduct(
                productId = product.id,
                wish = product.isWish
            )
        }
    }
}


sealed interface MainUiState {
    data object Loading : MainUiState
    data class MainUiData(
        val data: List<UiData>
    )
}