/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package ru.alex009.library.feature.gifs.model

import dev.icerock.moko.core.Parcelable
import dev.icerock.moko.core.Parcelize

@Parcelize
data class Gif(
    val id: Long,
    val previewUrl: String,
    val sourceUrl: String
) : Parcelable