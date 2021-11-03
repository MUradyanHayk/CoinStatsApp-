package com.example.coinstatsapp.data

import io.realm.RealmObject

open class CoinData : RealmObject() {
    var id: String = ""
    var name: String = ""
    var imgURL: String = ""
    var price: String = ""
    var currency: String = ""
    var isFavorite = false

    fun configureData(coinData: CoinData) {
        this.name = coinData.name
        this.imgURL = coinData.imgURL
        this.id = coinData.id
        this.price = coinData.price
        this.currency = coinData.currency
    }
}