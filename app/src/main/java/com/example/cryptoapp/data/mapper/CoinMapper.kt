package com.example.cryptoapp.data.mapper

import com.example.cryptoapp.data.database.CoinDbModel
import com.example.cryptoapp.data.network.CoinDto
import com.example.cryptoapp.data.network.CoinInfoJsonContainerDto
import com.example.cryptoapp.data.network.CoinNamesListDto
import com.example.cryptoapp.domain.CoinInfoEntity
import com.google.gson.Gson

class CoinMapper {

    // Dto -> DbModel
    fun mapDtoToDbModel(coinDto: CoinDto) = CoinDbModel(
        fromSymbol = coinDto.fromSymbol,
        toSymbol = coinDto.toSymbol,
        price = coinDto.price,
        lastUpdate = coinDto.lastUpdate,
        highDay = coinDto.highDay,
        lowDay = coinDto.lowDay,
        lastMarket = coinDto.lastMarket,
        imageUrl = coinDto.imageUrl,
    )

    // DbModel -> Entity
    fun mapDbModelToEntity(coinDbModel: CoinDbModel) = CoinInfoEntity(
        fromSymbol = coinDbModel.fromSymbol,
        toSymbol = coinDbModel.toSymbol,
        price = coinDbModel.price,
        lastUpdate = coinDbModel.lastUpdate,
        highDay = coinDbModel.highDay,
        lowDay = coinDbModel.lowDay,
        lastMarket = coinDbModel.lastMarket,
        imageUrl = coinDbModel.imageUrl,
    )

    // json to List Coin Dto
    // подробно как работает описывал в obsidian (см. раздел Kotlin Serialization)
    fun mapJsonToListCoinDto(jsonContainer: CoinInfoJsonContainerDto): List<CoinDto> {
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

    // coinNamesListDto to String
    // нужен для того, чтобы перевести имена валют в строку, для дальнеших запросов в АПИ
    fun mapCoinNamesListToString(coinNamesListDto: CoinNamesListDto): String {
        return coinNamesListDto.names
            ?.map { it.coinName?.name }
            ?.joinToString(",") ?: ""
        // всю коллекцию строк соединяем в одну строку через запятую
    }


}