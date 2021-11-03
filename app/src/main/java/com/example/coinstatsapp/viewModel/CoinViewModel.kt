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
import com.example.coinstatsapp.data.CoinData
import io.realm.Realm

class CoinViewModel : ViewModel() {
    private val pagingLimitCountDefaultValue = 5
    private var pagingLimitCountMaxValue = pagingLimitCountDefaultValue
    var hasInternetConnection: MutableLiveData<Boolean> = MutableLiveData()
    var realmDB: Realm? = null
    var requestQueue: RequestQueue? = null
    var pagingLimitCount: MutableLiveData<Int> = MutableLiveData()

    fun initModel(context: Context) {
        Realm.init(context)
        realmDB = Realm.getDefaultInstance()
        requestQueue = Volley.newRequestQueue(context)
    }

    fun configureData(context: Context) {
        clearAllData()
        pagingLimitCount.value = pagingLimitCountDefaultValue
//        parseJson(context)
    }

    fun changePagingLimitCount() {
        val oldPagingLimitCount: Int = pagingLimitCount.value ?: pagingLimitCountDefaultValue
        if (oldPagingLimitCount + 5 < pagingLimitCountMaxValue) {
            pagingLimitCount.value = oldPagingLimitCount + 5
        } else {
            pagingLimitCount.value = pagingLimitCountMaxValue
        }
    }
    private fun clearAllData() {
        realmDB?.beginTransaction()
        realmDB?.where(CoinData::class.java)?.findAll()?.deleteAllFromRealm()
        realmDB?.commitTransaction()
    }

    fun parseJson(context: Context) {
        if (!NetManager.hasInternetConnection(context)) {
            hasInternetConnection.value = false
            return
        }
        hasInternetConnection.value = true

        val url = NetManager.getBaseUrlWithGivenParameters("0", pagingLimitCount.value.toString(), "EUR")

        val request = JsonObjectRequest(Request.Method.GET, url, null, {
            //onResponse
            val jsonObject = it
            val coin = CoinData()
            val coins = jsonObject.getJSONArray("coins")
            pagingLimitCountMaxValue = coins.length()
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

    private fun createCoinObject(coin: CoinData) {
        realmDB?.beginTransaction()
        val coinObject = realmDB?.createObject(CoinData::class.java)
        coinObject?.configureData(coin)
        realmDB?.commitTransaction()
    }

    fun changeFavorite(id: String) {
        realmDB?.beginTransaction()
        val coins = realmDB?.where(CoinData::class.java)?.equalTo("id", id)?.findAll()
        if (coins?.first() != null) {
            coins.first()!!.isFavorite = !coins.first()!!.isFavorite
        }

        realmDB?.commitTransaction()
    }

    fun closeDB() {
        realmDB?.close()
    }
}