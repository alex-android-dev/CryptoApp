package com.example.cryptoapp.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository,
) {
    operator fun invoke(fromSymbol: String): LiveData<CoinInfoEntity> = repository.getCoinInfo(fromSymbol)
}