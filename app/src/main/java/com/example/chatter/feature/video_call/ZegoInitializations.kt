package com.example.chatter.feature.video_call

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton

@Composable
fun CallButton(isVideoCall: Boolean , onClick: (ZegoSendCallInvitationButton) -> Unit) {
    AndroidView(
        factory = { context ->
            val button = ZegoSendCallInvitationButton(context)
            button.setIsVideoCall(isVideoCall)
            button.resourceID = "zego_data"

            button
        },
        modifier = Modifier.size(50.dp)
    ){ zegoCallButton ->
        zegoCallButton.setOnClickListener { _ ->
            onClick(zegoCallButton)
        }
    }
}

// Bring these constant from ZEGOCloud website for your project
class ZEGOConstants{
    companion object{
        const val appID = 0L
        const val appSign = ""
    }
}