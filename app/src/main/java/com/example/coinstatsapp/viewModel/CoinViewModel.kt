package com.example.coinstatsapp.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.coinstatsapp.manager.NetManager
import com.example.coinstatsapp.data.CoinData
import io.realm.Realm
import io.realm.RealmResults

class CoinViewModel : ViewModel() {
    private val pagingLimitCountDefaultValue = 5
    private var pagingSkipCountDefaultValue = 0
    private var pagingLimitCount: Int = pagingLimitCountDefaultValue
    private val currency = "EUR"

    var hasInternetConnection: MutableLiveData<Boolean> = MutableLiveData()
    var isAllListEmpty: MutableLiveData<Boolean> = MutableLiveData()
    var isFavoriteListEmpty: MutableLiveData<Boolean> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var realmDB: Realm? = null
    var requestQueue: RequestQueue? = null

    fun initModel(context: Context, realmDB: Realm?) {
        this.realmDB = realmDB
        requestQueue = Volley.newRequestQueue(context)
    }

    fun getFavoriteList(): RealmResults<CoinData>? {
        return realmDB?.where(CoinData::class.java)?.equalTo("isFavorite", true)?.findAll()
    }

    fun getAllItemList(): RealmResults<CoinData>? {
        return realmDB?.where(CoinData::class.java)?.findAll()
    }

    fun configureData(context: Context) {
        if (!NetManager.hasInternetConnection(context)) {
            hasInternetConnection.value = false
            return
        }
        clearAllData()
        parseJson(context)
    }

    fun setAllListEmpty(isListEmpty: Boolean) {
        this.isAllListEmpty.value = isListEmpty
    }


    fun setFavoriteListEmpty(isListEmpty: Boolean) {
        this.isFavoriteListEmpty.value = isListEmpty
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
        isLoading.value = true

        val url = NetManager.getBaseUrlWithGivenParameters(pagingSkipCountDefaultValue.toString(), pagingLimitCount.toString(), currency)
        pagingSkipCountDefaultValue += pagingLimitCount
        val request = JsonObjectRequest(Request.Method.GET, url, null, {
            //onResponse
            val jsonObject = it
            val coin = CoinData()
            val coins = jsonObject.getJSONArray("coins")
            for (i in 0 until coins.length()) {
                coin.id = coins.getJSONObject(i).getString("id")
                coin.price = coins.getJSONObject(i).getString("price")
                coin.currency = currency
                coin.name = coins.getJSONObject(i).getString("name")
                coin.imgURL = coins.getJSONObject(i).getString("icon")
                createCoinObject(coin)
            }
            isLoading.value = false
            setAllListEmpty(coins.length() == 0)
        }, {
            //onError
            it.printStackTrace()
            isLoading.value = false
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
}