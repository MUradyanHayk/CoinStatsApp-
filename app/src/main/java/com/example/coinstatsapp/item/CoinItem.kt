package com.example.coinstatsapp.item

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setMargins
import com.bumptech.glide.Glide
import com.example.coinstatsapp.R
import com.example.coinstatsapp.model.CoinModel
import com.example.newprojmvvm.extensions.dp

class CoinItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private val logoImageViewSize = 50.dp
    private val favoriteImageViewSize = 20.dp
    private var logoImageView: ImageView? = null
    private var titleTextView: TextView? = null
    private var priceTextView: TextView? = null
    private var favoriteImageView: ImageView? = null
    private var titleAndPriceContainer: LinearLayout? = null

    init {
        val params = LayoutParams(LayoutParams.MATCH_PARENT, logoImageViewSize)
        params.setMargins(12.dp)
        this.layoutParams = params
        createLogoImageView()
//        createTitleTextView()
//        createPriceTextView()
        createTitleAndPriceContainer()
        createFavoriteImageView()
    }

    private fun createLogoImageView() {
        logoImageView = ImageView(context)
        val params = LayoutParams(logoImageViewSize, logoImageViewSize)
        logoImageView?.layoutParams = params
        addView(logoImageView)
    }


    private fun createTitleTextView() {
        titleTextView = TextView(context)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        titleTextView?.layoutParams = params
        titleAndPriceContainer?.addView(titleTextView)
    }

    private fun createPriceTextView() {
        priceTextView = TextView(context)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        priceTextView?.layoutParams = params
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
        val params = LayoutParams(favoriteImageViewSize, favoriteImageViewSize)
        params.marginEnd = 16.dp
        params.gravity = Gravity.CENTER or Gravity.END
        favoriteImageView?.layoutParams = params
        addView(favoriteImageView)
    }

    fun configure(model: CoinModel) {
        titleTextView?.text = model.name
        priceTextView?.text = model.price
        logoImageView?.let {
            Glide.with(context).load(model.imgURL).into(it)
        }
        if (model.isFavorite) {
            favoriteImageView?.setImageResource(R.drawable.ic_favorite_fill)
        } else {
            favoriteImageView?.setImageResource(R.drawable.ic_favorite_border)
        }
    }
}