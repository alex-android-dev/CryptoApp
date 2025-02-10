package com.example.cryptoapp.data.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.database.CoinDbModel
import com.example.cryptoapp.data.database.CoinInfoDao
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.worker.RefreshDataWorker
import com.example.cryptoapp.domain.CoinInfoEntity
import com.example.cryptoapp.domain.CoinRepository
import kotlinx.coroutines.delay

// реализация интерфейса
class CoinRepositoryImpl(
    private val application: Application,
) : CoinRepository {

    private val coinInfoDao: CoinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val mapper = CoinMapper()

    // методы получения
    override fun getCoinInfoList(): LiveData<List<CoinInfoEntity>> {
        val liveDataCoinDbModelList: LiveData<List<CoinDbModel>> = coinInfoDao.getPriceList()

        return liveDataCoinDbModelList.map { listCoinDbModel ->
            listCoinDbModel.map { dbModel ->
                mapper.mapDbModelToEntity(dbModel)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfoEntity> {
        val liveDataDbModel: LiveData<CoinDbModel> = coinInfoDao.getPriceInfoAboutCoin(fromSymbol)
        return liveDataDbModel.map { dbModel ->
            mapper.mapDbModelToEntity(dbModel)
        }
    }

    // метод загрузки данных
    override suspend fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            RefreshDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
        )
    }
}