package com.chathil.adoptme.model

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcelable
import androidx.compose.ui.platform.ContextAmbient
import com.chathil.adoptme.R
import com.squareup.moshi.*
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Pet(
    val id: Int,
    val name: String,
    val type: Type,
    val desc: String,
    var isLiked: Boolean,
    val isMale: Boolean,
    val size: Size,
    val location: String,
    val imageName: String,
) : Parcelable {
    enum class Type {
        CAT, DOG, CHAMELEON
    }

    enum class Size {
        S, M, L
    }

    class PetTypeAdapter {
        @FromJson
        fun fromJson(type: String): Type {
            return when (type) {
                "Cat" -> Type.CAT
                "Dog" -> Type.DOG
                "Chameleon" -> Type.CHAMELEON
                else -> throw JsonDataException("unknown suit: $type");
            }
        }
    }

    class PetSizeAdapter {
        @FromJson
        fun fromJson(size: String): Size {
            return when (size) {
                "S" -> Size.S
                "M" -> Size.M
                "L" -> Size.L
                else -> throw JsonDataException("unknown suit: $size");
            }
        }
    }

    companion object
}

fun Pet.Companion.allPets(applicationContext: Context): List<Pet>? {
    val jsonString =
        applicationContext.assets.open("pets.json").bufferedReader().use { it.readText() }
    val moshi: Moshi = Moshi.Builder().add(Pet.PetSizeAdapter()).add(Pet.PetTypeAdapter()).build()
    val type =
        Types.newParameterizedType(List::class.java, Pet::class.java)
    val jsonAdapter: JsonAdapter<List<Pet>> = moshi.adapter(type)
    return jsonAdapter.fromJson(jsonString)
}

val Pet.Companion.fake: Pet
    get() = Pet(
        1,
        "Fake 1",
        Pet.Type.CAT,
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam",
        false,
        true,
        Pet.Size.M,
        "Bireuen, Aceh",
        "cat-peep"
    )

val Pet.Companion.fakes: List<Pet>
    get() = listOf(Pet.fake, Pet.fake.copy(id = 2))

fun Pet.image(context: Context): Int = context.resources.getIdentifier(
    imageName.replace('-', '_'),
    "drawable",
    context.packageName
)

fun Pet.icon(context: Context): Int = when (type) {
    Pet.Type.CAT -> R.drawable.icn_cat
    Pet.Type.CHAMELEON -> R.drawable.icn_otter
    Pet.Type.DOG -> R.drawable.icn_dog
}