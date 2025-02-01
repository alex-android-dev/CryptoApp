package com.example.cryptoapp.data.network

import androidx.room.Entity

@Entity(tableName = "full_price_list")
data class CoinDto(
    val fromSymbol: String,
    val toSymbol: String?,
    val price: String?,
    val lastUpdate: String?,
    val highDay: String?,
    val lowDay: String?,
    val lastMarket: String?,
    val imageUrl: String,
)