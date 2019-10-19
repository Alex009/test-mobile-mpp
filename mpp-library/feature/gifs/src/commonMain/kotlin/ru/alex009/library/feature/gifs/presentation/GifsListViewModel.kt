/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package ru.alex009.library.feature.gifs.presentation

import dev.icerock.moko.mvvm.State
import dev.icerock.moko.mvvm.asState
import dev.icerock.moko.mvvm.livedata.*
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.units.UnitItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ru.alex009.library.feature.gifs.model.Gif
import ru.alex009.library.feature.gifs.model.GifsSource

class GifsListViewModel(
    private val gifsSource: GifsSource,
    private val strings: Strings,
    private val unitsFactory: UnitsFactory
) : ViewModel() {

    val searchQuery: MutableLiveData<String> = MutableLiveData("ice")

    private val _state: MutableLiveData<State<List<Gif>, Throwable>> =
        MutableLiveData(initialValue = State.Loading())

    val state: LiveData<State<List<UnitItem>, StringDesc>> = _state
        .dataTransform {
            map { news ->
                news.map { item ->
                    unitsFactory.createGifTile(
                        id = item.id,
                        gifUrl = item.previewUrl
                    )
                }
            }
        }
        .errorTransform {
            map { it.message?.desc() ?: strings.unknownError.desc() }
        }

    init {
        loadList()
        setupQueryListener()
    }

    fun onRetryPressed() {
        loadList()
    }

    fun onRefresh() {
        loadList()
    }

    private fun setupQueryListener() {
        viewModelScope.launch {
            searchQuery
                .asFlow()
                .debounce(1000)
                .distinctUntilChanged()
                .collect { loadList() }
        }
    }

    private fun loadList() {
        val query = searchQuery.value

        viewModelScope.launch {
            try {
                _state.value = State.Loading()

                val items = gifsSource.getGifsList(query)

                _state.value = items.asState()
            } catch (error: Throwable) {
                _state.value = State.Error(error)
            }
        }
    }

    interface UnitsFactory {
        fun createGifTile(
            id: Long,
            gifUrl: String
        ): UnitItem
    }

    interface Strings {
        val unknownError: StringResource
    }
}
