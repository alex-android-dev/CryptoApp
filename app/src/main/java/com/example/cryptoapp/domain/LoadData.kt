package com.example.cryptoapp.domain

class LoadData(
    private val repository: CoinRepository,
) {

    suspend operator fun invoke() = repository.loadData()

}