package com.chathil.adoptme.ui.home


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Colors
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.chathil.adoptme.R
import com.chathil.adoptme.model.Pet
import com.chathil.adoptme.model.fake
import com.chathil.adoptme.model.fakes
import com.chathil.adoptme.model.image
import com.chathil.adoptme.ui.AdoptmeAppState
import com.chathil.adoptme.ui.components.*
import com.chathil.adoptme.ui.theme.AdoptmeTheme
import com.chathil.adoptme.ui.theme.AlphaNearTransparent
import com.chathil.adoptme.ui.theme.padding
import com.example.jetsnack.ui.utils.SysUiController
import com.example.jetsnack.ui.utils.statusBarsPadding

private const val PetPhotoWidth = 198
private const val SmallPetPhotoSize = 112
private const val SmallPetCardHeight = SmallPetPhotoSize - 28
private const val PetPhotoHeight = 272
private const val InfoCardWidth = PetPhotoWidth - 70
private const val InfoCardHeight = InfoCardWidth - 32
private const val InfoCardOffset = 46
private const val PetCardHeight = PetPhotoHeight + InfoCardOffset
private const val IconSize = 24

@Composable
fun HomeScreen(
    onPetSelected: (Int) -> Unit,
    onAccountClicked: () -> Unit,
    appState: AdoptmeAppState
) {
    SysUiController.current.setStatusBarColor(
        AdoptmeTheme.colors.uiBackground.copy(
            AlphaNearTransparent
        )
    )
    BackdropFrontLayer(staticChildren = { modifier ->
        Column(modifier = modifier.background(AdoptmeTheme.colors.uiBackground)) {
            Spacer(
                modifier = Modifier
                    .statusBarsPadding().background(AdoptmeTheme.colors.uiBackground)
            )
            AccountSection(
                name = "Chathil",
                Modifier.padding(padding)
            ) { onAccountClicked() }
            Image(
                asset = imageResource(id = R.drawable.animal_shelter),
                modifier = Modifier.fillMaxWidth().height(216.dp)
            )
        }
    }) { modifier, state ->
        val columnScroll = rememberScrollState()
        val rowScroll = rememberScrollState()
        Body(
            modifier = modifier,
            columnScroll = columnScroll,
            isColumnScrollEnabled = state == FullScreenState.Expanded,
            rowScroll = rowScroll,
            appState = appState,
            onPetSelected = onPetSelected
        )
    }
}

@Composable
private fun Body(
    modifier: Modifier = Modifier,
    columnScroll: ScrollState,
    isColumnScrollEnabled: Boolean = true,
    rowScroll: ScrollState,
    appState: AdoptmeAppState,
    onPetSelected: (Int) -> Unit = {}
) {
    AdoptmeSurface(
        modifier = modifier.fillMaxSize(),
        color = AdoptmeTheme.colors.uiBackground,
        shape = RectangleShape,
        elevation = 8.dp
    ) {
        ScrollableColumn(
            scrollState = columnScroll,
            isScrollEnabled = isColumnScrollEnabled
        ) {
            Spacer(
                modifier = Modifier
                    .statusBarsPadding()
                    .preferredHeight(padding)
            )
            ScrollableRow(scrollState = rowScroll) {
                appState.pets.forEachIndexed { index, pet ->
                    FeaturedPetCard(
                        modifier = Modifier.padding(
                            end = 16.dp,
                            start = if (index == 0) 16.dp else 0.dp
                        ),
                        pet = pet,
                        onLikeClick = { a -> appState.like(a) },
                        onPetClick = { a -> onPetSelected(appState.pets.indexOf(a)) }
                    )
                }
            }
            Text(
                "Popular",
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.fillMaxWidth().wrapContentWidth().padding(vertical = 16.dp)
            )
            appState.pets.filter { it.isLiked }.forEach {
                PetCard(
                    pet = it,
                    onPetClick = { a -> onPetSelected(appState.pets.indexOf(a)) },
                    onLikeClick = { a -> appState.like(a) })
            }
            Spacer(modifier = Modifier.height(36.dp))
        }
    }
}

@Composable
private fun AccountSection(
    name: String,
    modifier: Modifier = Modifier,
    onAccountClicked: () -> Unit
) {
    Row(
        modifier = modifier.wrapContentHeight(Alignment.CenterVertically).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            name,
            style = MaterialTheme.typography.h4,
            color = AdoptmeTheme.colors.textPrimary
        )
        CircleImage(modifier = Modifier.size(46.dp), imageResource(id = R.drawable.me))
    }
}

@Composable
private fun FeaturedPetCard(
    pet: Pet,
    modifier: Modifier = Modifier,
    onPetClick: (Pet) -> Unit,
    onLikeClick: (Pet) -> Unit
) {
    Stack(
        modifier = modifier.preferredWidth(PetPhotoWidth.dp).padding(bottom = InfoCardOffset.dp)
            .clickable(onClick = { onPetClick(pet) })
    ) {
        AdoptmeSurface(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier.preferredWidth(PetPhotoWidth.dp).height(PetPhotoHeight.dp)
        ) {
            Image(
                asset = imageResource(id = pet.image(ContextAmbient.current)),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        AdoptmeSurface(
            modifier = Modifier.preferredWidth(InfoCardWidth.dp).preferredHeight(InfoCardHeight.dp)
                .padding(bottom = 8.dp)
                .gravity(Alignment.BottomStart).offset(y = InfoCardOffset.dp), elevation = 4.dp,
            color = AdoptmeTheme.colors.uiBackground
        ) {
            PetInfo(pet = pet, onLikeClick = onLikeClick)
        }
        AdoptmeSurface(
            modifier = Modifier.preferredSize(IconSize.dp).gravity(Alignment.BottomStart)
                .offset(x = (InfoCardWidth - (IconSize / 2)).dp, y = IconSize.dp),
            elevation = 6.dp,
            color = AdoptmeTheme.colors.primary
        ) {
            IconButton(onClick = {}) {
                Icon(asset = Icons.Rounded.ArrowForward, tint = AdoptmeTheme.colors.uiBackground)
            }
        }
    }
}

@Composable
fun PetInfo(pet: Pet, modifier: Modifier = Modifier, onLikeClick: (Pet) -> Unit) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = padding, vertical = 8.dp)
    ) {
        Text(
            pet.name,
            style = MaterialTheme.typography.h6,
            color = AdoptmeTheme.colors.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            pet.location,
            style = MaterialTheme.typography.caption,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        IconButton(
            onClick = { onLikeClick(pet) },
            modifier = Modifier.padding(top = 8.dp).offset(x = (-12).dp)
        ) {
            Icon(
                asset = if (pet.isLiked) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                tint = AdoptmeTheme.colors.btnLike,
                modifier = Modifier.padding(start = 0.dp)
            )
        }
    }
}

@Composable
fun PetCard(
    pet: Pet,
    modifier: Modifier = Modifier,
    onPetClick: (Pet) -> Unit,
    onLikeClick: (Pet) -> Unit
) {
    Stack(
        modifier = modifier.padding(end = padding.minus(2.dp), top = padding)
            .padding(horizontal = padding).clickable(
                onClick = { onPetClick(pet) }
            )
    ) {
        AdoptmeSurface(
            color = MaterialTheme.colors.secondary,
            modifier = Modifier.size(SmallPetPhotoSize.dp),
            elevation = 4.dp
        ) {
            Image(
                asset = imageResource(id = pet.image(ContextAmbient.current)),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        AdoptmeSurface(
            color = AdoptmeTheme.colors.uiBackground,
            modifier = Modifier.fillMaxWidth().height(SmallPetCardHeight.dp).offset(y = padding),
            elevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(
                    start = (SmallPetPhotoSize.dp + padding),
                    end = IconSize.dp
                )
            ) {
                Text(
                    pet.name,
                    style = MaterialTheme.typography.subtitle1,
                    color = AdoptmeTheme.colors.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    pet.desc,
                    style = MaterialTheme.typography.caption,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        AdoptmeSurface(
            modifier = Modifier.preferredSize(IconSize.dp).gravity(Alignment.BottomEnd)
                .offset(
                    x = (IconSize / 2).dp, y = (PetPhotoHeight + IconSize - PetCardHeight).dp.minus(
                        padding
                    )
                ),
            elevation = 4.dp,
            color = AdoptmeTheme.colors.primary
        ) {
            IconButton(onClick = {}) {
                Icon(asset = Icons.Rounded.ArrowForward, tint = AdoptmeTheme.colors.uiBackground)
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    AdoptmeTheme {
        AdoptmeSurface(modifier = Modifier.fillMaxSize()) {
            HomeScreen({}, {}, AdoptmeAppState(Pet.fakes))
        }
    }
}

@Preview
@Composable
fun FeaturedPetCardLightPreview() {
    AdoptmeTheme {
        AdoptmeSurface(modifier = Modifier.height(PetCardHeight.dp)) {
            FeaturedPetCard(Pet.fake, onPetClick = {}, onLikeClick = {})
        }
    }
}

@Preview
@Composable
fun FeaturedPetCardDarkPreview() {
    AdoptmeTheme(darkTheme = true) {
        AdoptmeSurface(modifier = Modifier.height(PetCardHeight.dp)) {
            FeaturedPetCard(Pet.fake, onPetClick = {}, onLikeClick = {})
        }
    }
}

@Preview
@Composable
fun PetCardLightPreview() {
    AdoptmeTheme {
        AdoptmeSurface {
            PetCard(pet = Pet.fake, onPetClick = {}, onLikeClick = {})
        }
    }
}

@Preview
@Composable
fun PetCardDarkPreview() {
    AdoptmeTheme(darkTheme = true) {
        AdoptmeSurface {
            PetCard(pet = Pet.fake, onPetClick = {}, onLikeClick = {})
        }
    }
}