package com.example.coinstatsapp.model

import io.realm.RealmObject

open class CoinModel : RealmObject() {
    var id: String = ""
    var name: String = ""
    var imgURL: String = ""
    var isFavorite = false

    fun initObject(coinModel: CoinModel) {
        this.name = coinModel.name
        this.imgURL = coinModel.imgURL
    }
}