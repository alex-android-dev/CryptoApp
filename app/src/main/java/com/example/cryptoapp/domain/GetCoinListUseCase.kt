package com.example.cryptoapp.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetCoinListUseCase @Inject constructor(
    private val repository: CoinRepository,
) {
   operator fun invoke() : LiveData<List<CoinInfoEntity>> = repository.getCoinInfoList()
}