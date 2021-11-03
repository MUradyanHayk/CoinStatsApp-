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
    private var favoriteImageView: ImageView? = null

    init {
        val params = LayoutParams(LayoutParams.MATCH_PARENT, logoImageViewSize)
        params.setMargins(12.dp)
        this.layoutParams = params
        createLogoImageView()
        createTitleTextView()
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
        params.marginStart = 16.dp + logoImageViewSize
        params.gravity = Gravity.CENTER_VERTICAL
        titleTextView?.layoutParams = params
        addView(titleTextView)
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
        logoImageView?.let {
            Glide.with(context).load(model.imgURL).into(it)
        }
    }
}