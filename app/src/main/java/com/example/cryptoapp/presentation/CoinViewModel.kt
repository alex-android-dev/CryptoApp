package com.example.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.data.Repository.CoinRepositoryImpl
import com.example.cryptoapp.domain.GetCoinListUseCase
import com.example.cryptoapp.domain.GetCoinUseCase
import com.example.cryptoapp.domain.LoadDataUseCase
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinRepositoryImpl(application)
    private val getCoinListUseCase = GetCoinListUseCase(repository)
    private val getCoinUseCase = GetCoinUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    val coinInfoList = getCoinListUseCase()

    fun getDetailInfo(fSym: String) = getCoinUseCase(fSym)


    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }
}