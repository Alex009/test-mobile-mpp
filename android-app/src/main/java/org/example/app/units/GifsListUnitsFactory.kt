/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.app.units

import dev.icerock.moko.units.UnitItem
import org.example.app.TileNews
import ru.alex009.library.feature.gifs.presentation.GifsListViewModel

class GifsListUnitsFactory : GifsListViewModel.UnitsFactory {
    override fun createGifTile(id: Long, gifUrl: String): UnitItem {
        return TileNews().apply {
            itemId = id
            this.title = gifUrl
        }
    }
}
