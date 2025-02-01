package com.example.cryptoapp.data.mapper

import com.example.cryptoapp.data.database.CoinDbModel
import com.example.cryptoapp.data.network.CoinDto
import com.example.cryptoapp.data.network.CoinInfoJsonContainerDto
import com.example.cryptoapp.data.network.CoinNameDto
import com.google.gson.Gson

class CoinMapper {

    // Dto -> DbModel
    fun dtoToDbModel(coinDto: CoinDto) = CoinDbModel(
        fromSymbol = coinDto.fromSymbol,
        toSymbol = coinDto.toSymbol,
        price = coinDto.price,
        lastUpdate = coinDto.lastUpdate,
        highDay = coinDto.highDay,
        lowDay = coinDto.lowDay,
        lastMarket = coinDto.lastMarket,
        imageUrl = coinDto.imageUrl,
    )

    // json to List Coin Dto
    fun jsonToListCoinDto(jsonContainer: CoinInfoJsonContainerDto): List<CoinDto> {
        val result = mutableListOf<CoinDto>()
        val jsonObject = jsonContainer.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }


}