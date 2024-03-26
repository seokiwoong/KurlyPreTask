package com.kurly.pretask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurly.pretask.network.KurlyNetworkDataSource
import com.kurly.pretask.network.RetrofitKurlyNetworkApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

}