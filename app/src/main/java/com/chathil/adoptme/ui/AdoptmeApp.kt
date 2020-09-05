/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chathil.adoptme.ui

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import com.chathil.adoptme.ui.account.Account
import com.chathil.adoptme.ui.detail.PetDetail
import com.chathil.adoptme.ui.home.HomeScreen
import com.chathil.adoptme.ui.theme.AdoptmeTheme
import com.example.jetsnack.ui.utils.Navigator
import com.example.jetsnack.ui.utils.ProvideDisplayInsets


@Composable
fun AdoptmeApp(backDispatcher: OnBackPressedDispatcher, appState: AdoptmeAppState) {
    val navigator: Navigator<Destination> = rememberSavedInstanceState(
        saver = Navigator.saver(backDispatcher)
    ) {
        Navigator(Destination.Home, backDispatcher)
    }
    val actions = remember(navigator) { Actions(navigator) }
    ProvideDisplayInsets {
        AdoptmeTheme {
            Crossfade(navigator.current) { destination ->
                when (destination) {
                    Destination.Home -> {
                        HomeScreen(actions.selectPet, actions.openAccount, appState)
                    }
                    is Destination.PetDetail -> PetDetail(
                        petIndex = destination.petIndex,
                        upPress = actions.upPress,
                        appState = appState
                    )
                    is Destination.Account -> Account(upPress = actions.upPress)
                }
            }
        }
    }
}
