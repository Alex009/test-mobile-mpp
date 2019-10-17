/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package ru.alex009.library.feature.gifs.model

interface GifsSource {
    suspend fun getGifsList(query: String): List<Gif>
}
