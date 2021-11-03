package com.example.coinstatsapp.item

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.coinstatsapp.R
import com.example.coinstatsapp.data.CoinData
import com.example.newprojmvvm.extensions.dp

class CoinItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private val logoImageViewSize = 50.dp
    private val itemHeight = 76.dp
    private val favoriteImageViewSize = 21.dp
    private var logoImageView: ImageView? = null
    private var titleTextView: TextView? = null
    private var priceTextView: TextView? = null
    private var favoriteImageView: ImageView? = null
    private var titleAndPriceContainer: LinearLayout? = null

    init {
        val params = LayoutParams(LayoutParams.MATCH_PARENT, itemHeight)
        params.marginStart = 12.dp
        this.layoutParams = params
        this.setBackgroundResource(R.drawable.rect_click_background)
        createLogoImageView()
        createTitleAndPriceContainer()
        createFavoriteImageView()
    }

    private fun createLogoImageView() {
        logoImageView = ImageView(context)
        val params = LayoutParams(logoImageViewSize, logoImageViewSize)
        params.gravity = Gravity.CENTER_VERTICAL
        logoImageView?.layoutParams = params
        addView(logoImageView)
    }


    private fun createTitleTextView() {
        titleTextView = TextView(context)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        titleTextView?.layoutParams = params
        titleTextView?.setTextColor(ContextCompat.getColor(context, R.color.gray))
        titleAndPriceContainer?.addView(titleTextView)
    }

    private fun createPriceTextView() {
        priceTextView = TextView(context)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        priceTextView?.layoutParams = params
        priceTextView?.setTextColor(ContextCompat.getColor(context, R.color.gray))
        titleAndPriceContainer?.addView(priceTextView)
    }

    private fun createTitleAndPriceContainer() {
        titleAndPriceContainer = LinearLayout(context)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.marginStart = 16.dp + logoImageViewSize
        titleAndPriceContainer?.orientation = LinearLayout.VERTICAL
        params.gravity = Gravity.CENTER_VERTICAL
        titleAndPriceContainer?.layoutParams = params
        addView(titleAndPriceContainer)

        createTitleTextView()
        createPriceTextView()
    }


    private fun createFavoriteImageView() {
        favoriteImageView = ImageView(context)
        favoriteImageView?.setImageResource(R.drawable.ic_favorite_border)
        changeFavoriteItemVisibility(true)
        val params = LayoutParams(favoriteImageViewSize, favoriteImageViewSize)
        params.marginEnd = 16.dp
        params.gravity = Gravity.CENTER or Gravity.END
        favoriteImageView?.layoutParams = params
        addView(favoriteImageView)
    }

    fun configure(data: CoinData, isFromFavoriteScreen:Boolean) {
        titleTextView?.text = data.name
        priceTextView?.text = data.price
        changeFavoriteItemVisibility(!isFromFavoriteScreen)
        logoImageView?.let {
            Glide.with(context).load(data.imgURL).into(it)
        }
        if (data.isFavorite) {
            favoriteImageView?.setImageResource(R.drawable.ic_favorite_fill)
        } else {
            favoriteImageView?.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    fun changeFavoriteItemVisibility(isVisible: Boolean) {
        favoriteImageView?.visibility = if(isVisible) VISIBLE else GONE
    }
}