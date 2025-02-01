package com.example.cryptoapp.data.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.database.CoinDbModel
import com.example.cryptoapp.data.database.CoinInfoDao
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.domain.CoinInfoEntity
import com.example.cryptoapp.domain.CoinRepository

// реализация интерфейса
class CoinRepositoryImpl(
    private val application: Application,
) : CoinRepository {

    private val coinInfoDao: CoinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val mapper = CoinMapper()

    // методы получения

    override fun getCoinInfoList(): LiveData<List<CoinInfoEntity>> {
        val liveDataListCoinDbModel: LiveData<List<CoinDbModel>> = coinInfoDao.getPriceList()
        return Transformations.map(liveDataListCoinDbModel)
        { listCoinDbModel ->
            listCoinDbModel.map { dbModel ->
                mapper.mapDbModelToEntity(dbModel)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfoEntity> {
        val liveDataDbModel: LiveData<CoinDbModel> = coinInfoDao.getPriceInfoAboutCoin(fromSymbol)
        return Transformations.map(liveDataDbModel) { dbModel ->
            mapper.mapDbModelToEntity(dbModel)
        }
    }

    // методы загрузки данных

}