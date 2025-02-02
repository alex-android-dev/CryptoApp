package com.example.cryptoapp.domain

import androidx.lifecycle.LiveData

class GetCoinListUseCase(
    private val repository: CoinRepository,
) {
   operator fun invoke() : LiveData<List<CoinInfoEntity>> = repository.getCoinInfoList()
}