package com.example.cryptoapp.data.mapper

import com.example.cryptoapp.data.database.CoinDbModel
import com.example.cryptoapp.data.network.CoinDto
import com.example.cryptoapp.data.network.CoinInfoJsonContainerDto
import com.example.cryptoapp.data.network.CoinNamesListDto
import com.example.cryptoapp.domain.CoinInfoEntity
import com.google.gson.Gson
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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
        imageUrl = BASE_IMAGE_URL + coinDto.imageUrl,
    )

    // DbModel -> Entity
    fun mapDbModelToEntity(coinDbModel: CoinDbModel) = CoinInfoEntity(
        fromSymbol = coinDbModel.fromSymbol,
        toSymbol = coinDbModel.toSymbol,
        price = coinDbModel.price,
        lastUpdate = convertTimestampToTime(coinDbModel.lastUpdate),
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


    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    companion object {
        const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }

}