package com.example.chatter

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import kotlin.random.Random

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FirebaseMessageService: FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.let {
            showNotification(it.title , it.body)
        }
    }

    private fun showNotification(title: String?, message: String?){

        val channel = NotificationChannel("message" , "Messages" , NotificationManager.IMPORTANCE_HIGH)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        val notificationID = Random.nextInt(1000)
        val notification = NotificationCompat.Builder(this , "message")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()

        notificationManager.notify(notificationID , notification)
    }

    fun postNotificationToUsers(channelID: String , channelName: String , senderName: String , messageContent: String , context: Context){

        if(channelName != "Ronaldo"){
            val fcmUrl = "https://fcm.googleapis.com/v1/projects/chatter-34e16/messages:send"

            val jsonBody = JSONObject().apply {
                put("message" , JSONObject().apply {
                    put("topic" , channelID)
                    put("notification" , JSONObject().apply {
                        put("title" , "New message from $channelName")
                        put("body" , "$senderName: $messageContent")
                    })
                })
            }

            val requestBody = jsonBody.toString()

            val request = object: StringRequest(
                Method.POST,
                fcmUrl,
                Response.Listener{

                },
                Response.ErrorListener {

                }
            ){
                override fun getBody(): ByteArray {
                    return requestBody.toByteArray()
                }

                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String , String>()

                    headers["Authorization"] = "Bearer ${getAccessToken(context)}"
                    headers["Content-Type"] = "application/json"

                    return headers
                }
            }

            Volley.newRequestQueue(context).add(request)
        }
    }

    private fun getAccessToken(context: Context): String{
        val inputStream = context.resources.openRawResource(R.raw.chatter)
        val googleCredentials = GoogleCredentials.fromStream(inputStream)
            .createScoped(listOf("https://www.googleapis.com/auth/firebase.messaging"))

        return googleCredentials.refreshAccessToken().tokenValue
    }
}