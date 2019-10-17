/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package ru.alex009.library.feature.gifs.di

import ru.alex009.library.feature.gifs.model.GifsSource
import ru.alex009.library.feature.gifs.presentation.GifsListViewModel

class GifsFactory(
    private val gifsSource: GifsSource,
    private val gifsListStrings: GifsListViewModel.Strings,
    private val gifsUnitsFactory: GifsListViewModel.UnitsFactory
) {
    fun createGifsListViewModel(): GifsListViewModel {
        return GifsListViewModel(
            gifsSource = gifsSource,
            strings = gifsListStrings,
            unitsFactory = gifsUnitsFactory
        )
    }
}
