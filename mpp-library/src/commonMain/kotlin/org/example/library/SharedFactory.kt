/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.library

import com.github.aakira.napier.Antilog
import com.github.aakira.napier.Napier
import com.russhwolf.settings.Settings
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import org.example.library.domain.di.DomainFactory
import org.example.library.feature.config.di.ConfigFactory
import org.example.library.feature.config.model.ConfigStore
import org.example.library.feature.config.presentation.ConfigViewModel
import ru.alex009.library.feature.gifs.di.GifsFactory
import ru.alex009.library.feature.gifs.model.Gif
import ru.alex009.library.feature.gifs.model.GifsSource
import ru.alex009.library.feature.gifs.presentation.GifsListViewModel

class SharedFactory(
    settings: Settings,
    antilog: Antilog,
    baseUrl: String,
    gifsUnitsFactory: GifsListViewModel.UnitsFactory
) {
    private val domainFactory = DomainFactory(
        settings = settings,
        baseUrl = baseUrl
    )

    val gifsFactory = GifsFactory(
        gifsSource = object : GifsSource {
            override suspend fun getGifsList(query: String): List<Gif> {
                return domainFactory.gifRepository.getGifList(query).map { item ->
                    Gif(
                        id = item.id.toLong(),
                        previewUrl = item.previewUrl,
                        sourceUrl = item.gifUrl
                    )
                }
            }
        },
        gifsListStrings = object : GifsListViewModel.Strings {
            override val unknownError: StringResource = MR.strings.unknown_error
        },
        gifsUnitsFactory = gifsUnitsFactory
    )

    val configFactory = ConfigFactory(
        configStore = object : ConfigStore {
            override var apiToken: String?
                get() = domainFactory.configRepository.apiToken
                set(value) {
                    domainFactory.configRepository.apiToken = value
                }
            override var language: String?
                get() = domainFactory.configRepository.language
                set(value) {
                    domainFactory.configRepository.language = value
                }
        },
        validations = object : ConfigViewModel.Validations {
            override fun validateToken(value: String): StringDesc? {
                return if (value.isBlank()) {
                    MR.strings.invalid_token.desc()
                } else {
                    null
                }
            }

            override fun validateLanguage(value: String): StringDesc? {
                val validValues = listOf("ru", "en")
                return if (validValues.contains(value)) {
                    null
                } else {
                    StringDesc.ResourceFormatted(
                        MR.strings.invalid_language_s,
                        validValues.joinToString(", ")
                    )
                }
            }
        },
        defaultToken = "ed155d0a445e4b4fbd878fe1f3bc1b7f",
        defaultLanguage = "en"
    )

    init {
        Napier.base(antilog)
    }
}