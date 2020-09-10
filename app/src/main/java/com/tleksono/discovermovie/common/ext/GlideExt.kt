package com.tleksono.discovermovie.common.ext

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette

fun ImageView.bindImage(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun ImageView.bindImagePalette(url: String?, paletteView: View) {
    url?.let {
        Glide.with(this)
            .load(it)
            .listener(
                GlidePalette.with(it)
                    .use(BitmapPalette.Profile.VIBRANT)
                    .intoBackground(paletteView)
                    .crossfade(true)
            )
            .into(this)
    }
}

