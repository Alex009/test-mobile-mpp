/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.app.view

import android.content.Intent
import android.view.Menu
import androidx.lifecycle.ViewModelProvider
import dev.icerock.moko.mvvm.MvvmActivity
import dev.icerock.moko.mvvm.createViewModelFactory
import org.example.app.AppComponent
import org.example.app.BR
import org.example.app.R
import org.example.app.databinding.ActivityGifsListBinding
import ru.alex009.library.feature.gifs.presentation.GifsListViewModel

// MvvmActivity for simplify creation of MVVM screen with https://github.com/icerockdev/moko-mvvm
class GifsListActivity : MvvmActivity<ActivityGifsListBinding, GifsListViewModel>() {
    override val layoutId: Int = R.layout.activity_gifs_list
    override val viewModelClass: Class<GifsListViewModel> = GifsListViewModel::class.java
    override val viewModelVariableId: Int = BR.viewModel

    // createViewModelFactory is extension from https://github.com/icerockdev/moko-mvvm
    // ViewModel not recreating at configuration changes
    override fun viewModelFactory(): ViewModelProvider.Factory = createViewModelFactory {
        AppComponent.factory.gifsFactory.createGifsListViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        val configItem = menu.add(R.string.config)
        configItem.setOnMenuItemClickListener { _ ->
            Intent(this, ConfigActivity::class.java).also { startActivity(it) }
            true
        }

        return true
    }
}
