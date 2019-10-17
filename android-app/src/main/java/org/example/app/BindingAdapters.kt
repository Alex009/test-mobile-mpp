/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.app

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("gifUrl")
fun ImageView.bindGif(gifUrl: String?) {
    if (gifUrl == null) {
        this.setImageDrawable(null)
        return
    }

    Glide.with(this).load(gifUrl).into(this)
}
