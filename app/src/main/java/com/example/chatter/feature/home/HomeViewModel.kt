package com.example.chatter.feature.home

import androidx.lifecycle.ViewModel
import com.example.chatter.model.Channel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel(){

    private val firebaseDatabase = Firebase.database
    private val stateChannel = MutableStateFlow<List<Channel>>(emptyList())
    val stateChannelUI = stateChannel.asStateFlow()

    private val stateOfLoading = MutableStateFlow(false)
    val stateOfLoadingUI = stateOfLoading.asStateFlow()

    init {
        getChannels()
    }

    private fun getChannels(){
        stateOfLoading.value = true

        firebaseDatabase.getReference("Channels").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val listChannels = mutableListOf<Channel>()

                snapshot.children.forEach { channelFireBase ->
                    val channel = Channel(channelFireBase.key!! , channelFireBase.value.toString())
                    listChannels.add(channel)
                }
                stateChannel.value = listChannels
                stateOfLoading.value = false
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun addChannel(nameOfChat: String){
        stateOfLoading.value = true

        val key = firebaseDatabase.getReference("Channels").push().key

        firebaseDatabase.getReference("Channels").child(key!!).setValue(nameOfChat).addOnSuccessListener {
            getChannels()
        }
    }
}