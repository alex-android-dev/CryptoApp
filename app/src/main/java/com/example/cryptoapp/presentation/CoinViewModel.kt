package com.example.cryptoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.domain.GetCoinListUseCase
import com.example.cryptoapp.domain.GetCoinUseCase
import com.example.cryptoapp.domain.LoadDataUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    private val getCoinListUseCase: GetCoinListUseCase,
    private val getCoinUseCase: GetCoinUseCase,
    private val loadDataUseCase: LoadDataUseCase,
) : ViewModel() {

    val coinInfoList = getCoinListUseCase()

    fun getDetailInfo(fSym: String) = getCoinUseCase(fSym)


    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }
}