package com.example.chatter.feature.supabase

import android.content.Context
import android.net.Uri
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import java.util.UUID

class SupabaseStorage {

    private val client = createSupabaseClient(
        supabaseUrl = "Put your URL for your project",
        supabaseKey = "Put your key from Supabase website",
    ){
        install(Storage)
    }

    suspend fun uploadImage(uri: Uri , context: Context): String? {
        try{
            val extension = uri.path?.substringAfterLast(".") ?: "jpg"

            val imageName = "${UUID.randomUUID()}.${extension}"

            val inputStream = context.contentResolver.openInputStream(uri) ?: return null

            client.storage.from(BUCKET_NAME).upload(imageName , inputStream.readBytes())

            val publicUrl = client.storage.from(BUCKET_NAME).publicUrl(imageName)

            return publicUrl
        } catch (e: Exception){
            return null
        }
    }

    companion object{
        const val BUCKET_NAME = "chatter-images"
    }
}