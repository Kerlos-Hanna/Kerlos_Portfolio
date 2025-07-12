package com.example.chatter.feature.chat

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.compose.runtime.MutableState
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.messaging.FirebaseMessaging
import com.zegocloud.uikit.service.defines.ZegoUIKitUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(): ViewModel() {

    private val messagesState = MutableStateFlow<List<Message>>(emptyList())
    val messageUI = messagesState.asStateFlow()
    private val firebaseDatabase = Firebase.database

    fun listenForMessages(channelID: String){
        firebaseDatabase.getReference("Messages").child(channelID).orderByChild("createdAt")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val messagesList = mutableListOf<Message>()

                    snapshot.children.forEach { data ->
                        val message = data.getValue(Message::class.java)
                        message?.let {
                            messagesList.add(message)
                        }
                    }

                    messagesState.value = messagesList
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

     fun subscribeForNotification(channelID: String){
        FirebaseMessaging.getInstance().subscribeToTopic(channelID)
    }

    fun sendMessage(channelID: String , messageText: String , imageUrlMessage: String?){
        val message = Message(
            id = firebaseDatabase.reference.push().key?: UUID.randomUUID().toString(),
            senderId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
            messageText = messageText,
            senderName = FirebaseAuth.getInstance().currentUser?.displayName?: "",
            imageUrlMessage = imageUrlMessage
        )

        firebaseDatabase.getReference("Messages").child(channelID).push().setValue(message)
    }

    fun isCurrentUserSendMessage(anotherUserID: String):Boolean{
        return FirebaseAuth.getInstance().currentUser?.uid == anotherUserID
    }

    fun createImageUri(context: Context , cameraImageUri: MutableState<Uri?>): Uri{

        val timeStamp = SimpleDateFormat("dd_MM_yyyy_HH_mm_ss" , Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(
            storageDir,
            "$timeStamp.jpg"
        )

        cameraImageUri.value = Uri.fromFile(imageFile)

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            imageFile
        )
    }

    fun getAllUsers(onResult: (List<ZegoUIKitUser>) -> Unit) {
        val listOfUsers = mutableListOf<ZegoUIKitUser>()

        FirebaseDatabase.getInstance().getReference("Users")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { child ->
                        if(FirebaseAuth.getInstance().currentUser?.uid != child.key){
                            val userID = child.key ?: return@forEach
                            val name = child.child("name").getValue(String::class.java) ?: "No Name"

                            val user = ZegoUIKitUser(userID, name)
                            listOfUsers.add(user)
                        }
                    }
                    onResult.invoke(listOfUsers)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}