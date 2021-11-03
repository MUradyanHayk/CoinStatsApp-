package com.example.coinstatsapp.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.coinstatsapp.constants.Constants
import com.example.coinstatsapp.manager.NetManager
import com.example.coinstatsapp.model.CoinModel
import io.realm.Realm

class CoinViewModel : ViewModel() {
    var hasInternetConnection:  MutableLiveData<Boolean> = MutableLiveData()
    var realmDB: Realm? = null
    var requestQueue: RequestQueue? = null

    fun initModel(context: Context) {
        Realm.init(context)
        realmDB = Realm.getDefaultInstance()
        requestQueue = Volley.newRequestQueue(context)
    }

    fun configureData(context: Context) {
        clearAllData()
        parseJson(context)
    }

    private fun clearAllData() {
        realmDB?.beginTransaction()
        realmDB?.where(CoinModel::class.java)?.findAll()?.deleteAllFromRealm()
        realmDB?.commitTransaction()
    }

    fun parseJson(context: Context) {
        if (!NetManager.hasInternetConnection(context)) {
            hasInternetConnection.value = false
            return
        }
        hasInternetConnection.value = true

        val request = JsonObjectRequest(Request.Method.GET, Constants.BASE_URL, null, {
            //onResponse
            val jsonObject = it
            val coin = CoinModel()
            val coins = jsonObject.getJSONArray("coins")
            for (i in 0 until coins.length()) {
                coin.id = coins.getJSONObject(i).getString("id")
                coin.price = coins.getJSONObject(i).getString("price")
                coin.name = coins.getJSONObject(i).getString("name")
                coin.imgURL = coins.getJSONObject(i).getString("icon")
                createCoinObject(coin)
            }

        }, {
            //onError
            it.printStackTrace()
        })

        requestQueue?.add(request)
    }

    private fun createCoinObject(coin: CoinModel) {
        realmDB?.beginTransaction()
        val coinObject = realmDB?.createObject(CoinModel::class.java)
        coinObject?.initObject(coin)
        realmDB?.commitTransaction()
    }

    fun changeFavorite(id: String) {
        realmDB?.beginTransaction()
        val coins = realmDB?.where(CoinModel::class.java)?.equalTo("id", id)?.findAll()
        if (coins?.first() != null) {
            coins.first()!!.isFavorite = !coins.first()!!.isFavorite
        }

        realmDB?.commitTransaction()
    }

    fun closeDB() {
        realmDB?.close()
    }
}