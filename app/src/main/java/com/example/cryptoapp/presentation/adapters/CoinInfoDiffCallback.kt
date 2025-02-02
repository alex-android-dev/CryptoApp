package com.example.cryptoapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.cryptoapp.domain.CoinInfoEntity

// потому что будет всегда использоваться один и тот же объект
object CoinInfoDiffCallback : DiffUtil.ItemCallback<CoinInfoEntity>() {

    // объекты сравниваем объекты - один и тот же объект
    override fun areItemsTheSame(oldItem: CoinInfoEntity, newItem: CoinInfoEntity) =
        oldItem.fromSymbol == newItem.fromSymbol

    // сравниваем поля объекта - не изменилось ли его содержимое
    // если изменилось, то перерисовываем
    override fun areContentsTheSame(oldItem: CoinInfoEntity, newItem: CoinInfoEntity) =
        oldItem == newItem

}