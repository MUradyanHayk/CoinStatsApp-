package com.example.coinstatsapp.model

import io.realm.RealmObject

open class CoinModel : RealmObject() {
    var title: String = ""
    var imgURL: String = ""
}