/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import UIKit
import MultiPlatformLibrary
import MultiPlatformLibraryUnits

class GifsListUnitsFactory: GifsListViewModelUnitsFactory {
    func createGifTile(id: Int64, gifUrl: String) -> UnitItem {
        return UITableViewCellUnit<NewsTableViewCell>(
            data: NewsTableViewCell.CellModel(
                id: id,
                title: gifUrl,
                description: gifUrl
            ),
            configurator: nil
        )
    }
}
