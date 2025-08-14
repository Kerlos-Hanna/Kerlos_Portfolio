package com.example.e_commerce.navigation

import android.net.Uri
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import com.example.e_commerce.ui.model.AddressData
import com.example.e_commerce.ui.model.UIProduct
import kotlinx.serialization.json.Json


object CustomNavType {
    val ProductType = object : NavType<UIProduct>(
        isNullableAllowed = false
    ) {
        override fun put(bundle: SavedState, key: String, value: UIProduct) {
            bundle.putString(key , Json.encodeToString(value))
        }

        override fun get(bundle: SavedState, key: String): UIProduct? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): UIProduct {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: UIProduct): String {
            return Uri.encode(Json.encodeToString(value))
        }
    }

    val addressData = object : NavType<AddressData>(
        isNullableAllowed = false
    ) {
        override fun put(bundle: SavedState, key: String, value: AddressData) {
            bundle.putString(key , Json.encodeToString(value))
        }

        override fun get(bundle: SavedState, key: String): AddressData? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): AddressData {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: AddressData): String {
            return Uri.encode(Json.encodeToString(value))
        }
    }
}